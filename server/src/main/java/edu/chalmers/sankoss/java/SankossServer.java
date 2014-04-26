package edu.chalmers.sankoss.java;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import edu.chalmers.sankoss.core.*;
import edu.chalmers.sankoss.core.protocol.*;
import edu.chalmers.sankoss.java.web.WebServer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    /**
     * Logger
     */
    private final static Logger LOGGER = Logger.getLogger(SankossServer.class.getName());

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Constructor
     * @param args
     * @throws IOException if the server couldn't start and bind to an address
     */
    public static void main(String[] args) throws IOException {
        SankossServer sankossServer = new SankossServer();
        //sankossServer.startHTTPServer(8080);

        new Thread(new WebServer(sankossServer)).start();
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
                    connection.setPlayer(new Player((long) connection.getID()));

                    LOGGER.log(Level.INFO, String.format("%s connected as #%d", connection.getRemoteAddressTCP(), connection.getID()));

                    players.put(connection.getPlayer(), connection);

                    connection.sendTCP(new Connected((long) connection.getID()));
                    pcs.firePropertyChange("playerConnected", null, null);

                    return;
                }

                /**
                 * Nothing below this line will be executed if no player has been created for the client.
                 * This makes sure that not everyone can manipulate the server with messages.
                 */
                if (player == null) return;

                /**
                 * Tell the RoomFactory to create a new room with given details from the message.
                 * Add the clients player instance to the room and send the room ID to the client.
                 */
                if (object instanceof CreateRoom) {
                    CreateRoom msg = (CreateRoom) object;

                    /**
                     * A client cannot sit in more than one room at a time
                     */
                    for (Room room : RoomFactory.getRooms().values()) {
                        if (room.getPlayers().contains(player)) {
                            connection.sendTCP(new CreatedRoom());

                            return;
                        }
                    }

                    Room room;
                    try {
                        room = RoomFactory.createRoom(msg.getName(), msg.getPassword());
                        room.addPlayer(player);
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new CreatedRoom());

                        return;
                    }

                    LOGGER.log(Level.INFO, String.format("#%d created room #%d (%s with password '%s')", player.getID(), room.getID(), room.getName(), msg.getPassword()));

                    connection.sendTCP(new CreatedRoom(room.getID()));
                    pcs.firePropertyChange("roomCreated", null, null);

                    return;
                }

                /**
                 * Let client join room with given ID in the message.
                 * Also tell the players already in the room that a new player has joined.
                 */
                if (object instanceof JoinRoom) {
                    JoinRoom msg = (JoinRoom) object;

                    Room room;
                    try {
                        room = RoomFactory.getRoom(msg.getRoomID());
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new JoinRoom());

                        return;
                    }

                    if (room.getPlayers().contains(player)) {
                        connection.sendTCP(new JoinRoom());
                        return;
                    }

                    room.addPlayer(player);

                    LOGGER.log(Level.INFO, String.format("#%d joined room #%d (%s)", player.getID(), room.getID(), room.getName()));

                    JoinedRoom joinedRoom = new JoinedRoom(player);

                    for (Player roomPlayer : room.getPlayers()) {
                        players.get(roomPlayer).sendTCP(joinedRoom);
                    }

                    pcs.firePropertyChange("roomJoined", null, null);

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

                    if (room.getPlayers().size() <= 1) {
                        connection.sendTCP(new StartedGame());

                        return;
                    }

                    /**
                     * Security check.
                     * Make sure that the message was sent from the creator.
                     */
                    if (room.getPlayers().get(0).equals(player)) {
                        Game game = GameFactory.createGame(room.getPlayers());

                        LOGGER.log(Level.INFO, String.format("#%d started game #%d", player.getID(), game.getID()));

                        for (Player gamePlayer : game.getPlayers()) {
                            players.get(gamePlayer).sendTCP(new StartedGame(game.getID(), game.getPlayers()));
                        }

                        pcs.firePropertyChange("gameStarted", null, null);

                        try {
                            RoomFactory.removeRoom(room);
                        } catch (RoomNotFoundException e) {
                            e.printStackTrace();
                        }

                        pcs.firePropertyChange("roomRemoved", null, null);

                        return;
                    }

                    return;
                }

                /**
                 * Received when the client wants to get a list of rooms.
                 */
                if (object instanceof FetchRooms) {
                    LOGGER.log(Level.INFO, String.format("#%d fetching rooms", player.getID()));

                    FetchedRooms fetchedRooms = new FetchedRooms(RoomFactory.getRooms());
                    connection.sendTCP(fetchedRooms);

                    return;
                }

                /**
                 * The player has places their ships.
                 * Will start the game when all the players in the game has placed their ships.
                 */
                if (object instanceof PlayerReady) {
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

                        LOGGER.log(Level.INFO, String.format("Everyone is ready... Start game #%d", game.getID()));

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
                        LOGGER.log(Level.INFO, String.format("#%d %s", player.getID(), e.getMessage()));
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

                /**
                 * Add an AI player to a room with given room id.
                 * Starts a new thread.
                 */
                if (object instanceof CreateAI) {
                    CreateAI msg = (CreateAI) object;

                    Room room;
                    try {
                        room = RoomFactory.getRoom(msg.getRoomID());
                    } catch (RoomNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }

                    LOGGER.log(Level.INFO, String.format("Adding AI player to room #%d", room.getID()));

                    new Thread(new SankossAI(room.getID())).start();
                }

                /**
                 * Remove room with given room id.
                 */
                if (object instanceof RemoveRoom) {
                    RemoveRoom msg = (RemoveRoom) object;

                    /**
                     * Make sure that the client is inside the given room
                     */
                    Room room;
                    try {
                        room = RoomFactory.getRoom(msg.getRoomID());
                    } catch (RoomNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (!room.getPlayers().contains(player)) return;

                    /**
                     * If so, remove the room and notify the client
                     */
                    try {
                        RoomFactory.removeRoom(room);
                    } catch (RoomNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }

                    connection.sendTCP(new RemovedRoom());

                    pcs.firePropertyChange("roomRemoved", null, null);

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
                        room.getPlayers().remove(player);
                        for (Player opponent : room.getPlayers()) {
                            players.get(opponent).sendTCP(new Disconnect(player));
                        }
                        try {
                            RoomFactory.removeRoom(room);
                        } catch (RoomNotFoundException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    }

                }

                for (Game game : GameFactory.getGames().values()) {
                    if (game.getPlayers().contains(player)) {
                        game.getPlayers().remove(player);
                        for (Player opponent : game.getPlayers()) {
                            players.get(opponent).sendTCP(new Disconnect(player));
                        }
                        try {
                            GameFactory.removeGame(game);
                        } catch (GameNotFoundException e) {
                            System.out.println(e.getMessage());
                        }

                        break;
                    }

                }

                players.remove(player);

                pcs.firePropertyChange("playerDisconnected", null, null);
                pcs.firePropertyChange("roomRemoved", null, null);
                pcs.firePropertyChange("gameRemoved", null, null);

                LOGGER.log(Level.INFO, String.format("#%d disconnected", connection.getID()));
            }
        });

        server.bind(Network.PORT);
        server.start();
        LOGGER.log(Level.INFO, "Server started on port: " + Network.PORT);

    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    public Map<Player, PlayerConnection> getPlayers() {
        return players;
    }

    public Map<Long, Room> getRooms() {
        return RoomFactory.getRooms();
    }

    public Map<Long, Game> getGames() {
        return GameFactory.getGames();
    }

    /**
     * Holds the player connection
     */
    public class PlayerConnection extends Connection {
        private Player player;

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }
}
