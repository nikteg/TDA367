package edu.chalmers.sankoss.java;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import edu.chalmers.sankoss.core.Network;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.core.protocol.*;


/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 * 
 */
public class SankossServer {
    /**
     * KryoNet server
     */
	private Server server;

    /**
     * Map to link players with their connections
     */
    private Map<Player, PlayerConnection> players = new HashMap<Player, PlayerConnection>();

	public static void main(String[] args) throws IOException {
		SankossServer sankossServer = new SankossServer();
        sankossServer.startHTTPServer(8080);
	}

    /**
     * Start server
     * @throws IOException
     */
	public SankossServer() throws IOException {

        /**
         * Create KryoNet server instance and configure it to use a PlayerConnection as connection.
         */
		server = new Server() {
			protected Connection newConnection() {
				return new PlayerConnection();
			}
		};

        /**
         * Register server for serialization (converting the objects so that they can be sent through the network).
         */
		Network.register(server);

        /**
         * Add listener.
         * This is where all the network traffic gets filtered.
         */
		server.addListener(new Listener() {

            /**
             * Executed when the server receives a new client connection.
             * @param connection client connection
             */
			public void connected(Connection connection) {
                System.out.println(String.format("%s connected as #%d", connection.getRemoteAddressTCP(), connection.getID()));
            }

            /**
             * Executed when the server receives a message.
             * The received Object must be casted to the correct object type before using it.
             * @param c client connection
             * @param object sent object
             */
			public void received(Connection c, Object object) {
                PlayerConnection connection = (PlayerConnection) c;
                Player player = connection.getPlayer();

                /**
                 * Create new player instance and map it to their connection.
                 * When the new player instance is created, its ID is sent to the client.
                 * This package should be received as soon as the client has connected!
                 */
                if (object instanceof Connect) {
                    connection.setPlayer(new Player(connection.getID()));
                    System.out.println(String.format("#%d connected", connection.getID()));

                    players.put(connection.getPlayer(), connection);

                    connection.sendTCP(new Connect(connection.getID()));

                    return;
                }

                /**
                 * Tell the RoomFactory to create a new room with given details from the message.
                 * Add the clients player instance to the room and send the room ID to the client.
                 */
				if (object instanceof CreateRoom) {
                    if (player == null)
                        return;

					CreateRoom msg = (CreateRoom) object;

                    Room room;
                    try {
                        room = RoomFactory.createRoom(msg.getName(), msg.getPassword());
                        room.addPlayer(player);
                    } catch (RoomNotFoundException e) {
                        System.out.println("Room could not be created...");
                        connection.sendTCP(new CreatedRoom());

                        return;
                    }

                    System.out.println(String.format("#%d created room #%d (%s with password '%s')", player.getID(), room.getID(), room.getName(), msg.getPassword()));

                    connection.sendTCP(new CreatedRoom(room.getID()));

					return;
				}

                /**
                 * Let client join room with given ID in the message.
                 * Also tell the players already in the room that a new player has joined.
                 */
				if (object instanceof JoinRoom) {
                    if (player == null)
                        return;

					JoinRoom msg = (JoinRoom) object;

                    Room room;
                    try {
                        room = RoomFactory.getRoom(msg.getRoomID());
                        room.addPlayer(player);
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new JoinRoom());

                        return;
                    }

                    System.out.println(String.format("#%d joined room #%d (%s)", player.getID(), room.getID(), room.getName()));

                    JoinedRoom joinedRoom = new JoinedRoom(player);

                    for (Player roomPlayer : room.getPlayers()) {
                        players.get(roomPlayer).sendTCP(joinedRoom);
                    }

					return;
				}

                /**
                 * The room creator wants to start the game.
                 * Starts game with given ID and messages the players in the room that the game has started.
                 * When this is done, the room is removed.
                 */
                if (object instanceof StartGame) {
                    StartGame msg = (StartGame) object;

                    Room room;
                    try {
                        room = RoomFactory.getRoom(msg.getRoomID());
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new StartedGame());

                        return;
                    }

                    /**
                     * Security check.
                     * Make sure that the message was sent from the creator.
                     */
                    if (room.getPlayers().get(0).equals(player)) {
                        Game game = GameFactory.createGame(room.getPlayers());

                        System.out.println(String.format("#%d started game #%d", player.getID(), game.getID()));

                        for (Player gamePlayer : game.getPlayers()) {
                            players.get(gamePlayer).sendTCP(new StartedGame(game.getID(), game.getPlayers()));
                        }

                        try {
                            RoomFactory.removeRoom(room);
                        } catch (RoomNotFoundException e) {
                            e.printStackTrace();
                        }

                        return;
                    }

                    return;
                }

                /**
                 * Received when the client wants to get a list of rooms.
                 */
				if (object instanceof FetchRooms) {
                    if (player == null)
                        return;

					System.out.println(String.format("#%d fetching rooms", player.getID()));

					FetchedRooms fetchedRooms = new FetchedRooms(RoomFactory.getRooms());
					connection.sendTCP(fetchedRooms);

					return;
				}

                /**
                 * The player has places their ships.
                 * Will start the game when all the players in the game has placed their ships.
                 */
				if (object instanceof PlayerReady) {
                    if (player == null)
                        return;

					PlayerReady msg = (PlayerReady) object;

                    player.setReady(true);

                    Game game;
                    try {
                        game = GameFactory.getGame(msg.getGameID());
                    } catch (GameNotFoundException e) {
                        return;
                    }

                    // Make sure that the game actually has players
                    if (game.getPlayers().size() == 0)
                        return;

                    boolean allReady = true;
                    for (Player gamePlayer : game.getPlayers()) {
                        if (!gamePlayer.isReady()) {
                            allReady = false;

                            break;
                        }
                    }

                    PlayerIsReady playerIsReady = new PlayerIsReady(player);
                    for (Player gamePlayer : game.getPlayers()) {
                        if (!gamePlayer.equals(player))
                            players.get(gamePlayer).sendTCP(playerIsReady);
                    }

                    if (allReady) {
                        for (Player gamePlayer : game.getPlayers()) {
                            players.get(gamePlayer).sendTCP(new GameReady());
                        }

                        System.out.println(String.format("Everyone is ready... Start game #%d", game.getID()));

                        // Send turn to random player
                        int starter = new Random().nextInt(game.getPlayers().size() - 1);
                        game.setAttacker(game.getPlayers().get(starter));
                        players.get(game.getAttacker()).sendTCP(new Turn());

                    }

                    player.setFleet(msg.getFleet());

                    return;
				}

                /**
                 * Player shoots at another player.
                 */
				if (object instanceof Fire) {
                    if (player == null) return;

					Fire msg = (Fire) object;

                    // A player can't shoot at him/herself
                    if (msg.getTarget().equals(player))
                        return;

                    Game game;
                    try {
                        game = GameFactory.getGame(msg.getGameID());
                    } catch (GameNotFoundException e) {
                        return;
                    }

                    // For safety, check if it REALLY is the players turn to fire
					if (!game.getAttacker().equals(player))
						return;


                    Ship targetShip;
                    try {
                        targetShip = game.fire(players.get(msg.getTarget()).getPlayer(), msg.getCoordinate());
                    } catch (UsedCoordinateException e) {
                        System.out.println(String.format("#%s %s", player.getID(), e.getMessage()));
                        players.get(player).sendTCP(new Turn());

                        return;
                    }

                    boolean hit = (targetShip != null);
                    FireResult fireResult = new FireResult(msg, hit);

                    for (Player gamePlayer : game.getPlayers()) {
                        players.get(gamePlayer).sendTCP(fireResult);

                        /**
                         * Send message telling everyone that a ship was destroyed.
                         */
                        if (targetShip != null && targetShip.isDestroyed()) {
                            players.get(gamePlayer).sendTCP(new DestroyedShip(msg.getTarget(), targetShip));
                        }
                    }

					game.changeAttacker();
                    players.get(game.getAttacker()).sendTCP(new Turn());

					return;
				}

			}

            /**
             * Received when the client has disconnected from the server.
             * Will also be triggered if the keep-alive messages have not been confirmed.
             * @param c
             */
			public void disconnected(Connection c) {
				PlayerConnection connection = (PlayerConnection) c;
				Player player = connection.getPlayer();

                /**
                 * Remove room that the player was connected to.
                 */
                for (Room room : RoomFactory.getRooms().values()) {
                    if (room.getPlayers().contains(player)) {
                        try {
                            RoomFactory.removeRoom(room);
                        } catch (RoomNotFoundException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    }
                }

                players.remove(player);

                System.out.println(String.format("#%d disconnected", connection.getID()));
            }
		});

		server.bind(Network.PORT);
		server.start();
		System.out.println("Server started on port: " + Network.PORT);

	}

    public void startHTTPServer(int port) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", new WebHandler());
        httpServer.setExecutor(java.util.concurrent.Executors.newCachedThreadPool()); // creates a default executor
        httpServer.start();
    }

    private class WebHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<h1>Players</h1>\n");

            for (Map.Entry pairs : players.entrySet()) {
                Player player = (Player) pairs.getKey();
                PlayerConnection playerConnection = (PlayerConnection) pairs.getValue();
                stringBuilder.append(String.format("<p>#%d %s</p>\n", player.getID(), playerConnection.getRemoteAddressTCP()));
            }

            stringBuilder.append("<h1>Rooms</h1>\n");

            for (Room room : RoomFactory.getRooms().values()) {
                stringBuilder.append(String.format("<p>#%d %s</p>\n", room.getID(), room.getName()));

                stringBuilder.append(String.format("<b>Players in room:</b>\n"));

                for (Player player : room.getPlayers()) {
                    stringBuilder.append(String.format("<p>#%d</p>\n", player.getID()));
                }
            }

            String response = stringBuilder.toString();

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    /**
     * Holds the player connection
     */
    private class PlayerConnection extends Connection {
        private Player player;

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }
}
