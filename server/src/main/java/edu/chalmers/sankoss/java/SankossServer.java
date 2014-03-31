package edu.chalmers.sankoss.java;

import java.io.IOException;
import java.util.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

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
		new SankossServer();
	}

    /**
     * Start server
     * @throws IOException
     */
	public SankossServer() throws IOException {
		server = new Server() {
			protected Connection newConnection() {
				return new PlayerConnection();
			}
		};

        // Register server for serialization
		Network.register(server);

		server.addListener(new Listener() {

            // Got connection from client
			public void connected(Connection connection) {
                System.out.println(String.format("%s connected as #%d", connection.getRemoteAddressTCP(), connection.getID()));
            }

            // Got package from client
			public void received(Connection c, Object object) {
                PlayerConnection connection = (PlayerConnection) c;
                Player player = connection.getPlayer();

                // Player connected
                if (object instanceof Connect) {
                    connection.setPlayer(new Player(connection.getID()));
                    System.out.println(String.format("#%d connected", connection.getID()));

                    players.put(connection.getPlayer(), connection);

                    connection.sendTCP(new Connect(connection.getID()));

                    return;
                }

                // Room creation
				if (object instanceof CreateRoom) {
                    if (player == null) return;

					CreateRoom msg = (CreateRoom) object;

                    Room room;
                    try {
                        room = RoomFactory.createRoom(msg.getName(), msg.getPassword(), player);
                    } catch (RoomNotFoundException e) {
                        System.out.println("Room could not be created...");
                        connection.sendTCP(new CreatedRoom());

                        return;
                    }

                    System.out.println(String.format("#%d created room #%d (%s with password '%s')", player.getID(), room.getID(), room.getName(), msg.getPassword()));

                    connection.sendTCP(new CreatedRoom(room.getID()));

					return;
				}

                // Room joining
				if (object instanceof JoinRoom) {
                    if (player == null) return;

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

                // When the host wants to start the game
                if (object instanceof StartGame) {
                    StartGame msg = (StartGame) object;

                    Room room;
                    try {
                        room = RoomFactory.getRoom(msg.getRoomID());
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new StartedGame());

                        return;
                    }

                    // If the host pressed start game
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

                // Fetching rooms to player
				if (object instanceof FetchRooms) {
                    if (player == null) return;

					System.out.println(String.format("#%d fetching rooms", player.getID()));

					FetchRooms fetchRooms = new FetchRooms(RoomFactory.getRooms());
					connection.sendTCP(fetchRooms);

					return;
				}

                // Player inside a game has placed his fleet
				if (object instanceof PlayerReady) {
                    if (player == null) return;

					PlayerReady msg = (PlayerReady) object;

                    player.setReady(true);

                    Game game;
                    try {
                        game = GameFactory.getGame(msg.getGameID());
                    } catch (GameNotFoundException e) {
                        return;
                    }

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
                        System.out.println(String.format("Everyone is ready... Start game #%d", game.getID()));
                        for (Player gamePlayer : game.getPlayers()) {
                            players.get(gamePlayer).sendTCP(new GameReady());
                        }

                        // Send turn to random player
                        int starter = new Random().nextInt(game.getPlayers().size() - 1);
                        game.setAttacker(game.getPlayers().get(starter));
                        players.get(game.getAttacker()).sendTCP(new Turn());

                    }

                    player.setFleet(msg.getFleet());

                    return;
				}

                // Player fire at another player
				if (object instanceof Fire) {
                    if (player == null) return;

					Fire msg = (Fire) object;

                    // A player cannot shoot at itself
                    if (msg.getTarget().equals(player)) return;

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
                        System.out.println(String.format("#%s Error: %s", player.getID(), e.getMessage()));
                        players.get(player).sendTCP(new Turn());
                        return;
                    }

                    FireResult fireResult = new FireResult(msg, (targetShip != null));

                    for (Player gamePlayer : game.getPlayers()) {
                        players.get(gamePlayer).sendTCP(fireResult);

                        if (targetShip !=null && targetShip.isDestroyed()) {
                            players.get(gamePlayer).sendTCP(new DestroyedShip(msg.getTarget(), targetShip));
                        }
                    }

					game.changeAttacker();
                    players.get(game.getAttacker()).sendTCP(new Turn());

					return;
				}

			}

			public void disconnected(Connection c) {
				PlayerConnection connection = (PlayerConnection) c;
				Player player = connection.getPlayer();

                players.remove(player);

                System.out.println(String.format("#%d disconnected", connection.getID()));
            }
		});

		server.bind(Network.PORT);
		server.start();
		System.out.println("Server started on port: " + Network.PORT);

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
