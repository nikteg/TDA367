package edu.chalmers.sankoss.java;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Network;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.core.protocol.*;
import edu.chalmers.sankoss.java.web.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;


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
     * Logger
     */
    Logger log = LoggerFactory.getLogger(SankossServer.class);

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
             * @param c client connection
             */
            public void connected(Connection c) {
                PlayerConnection connection = (PlayerConnection) c;
                connection.setPlayer(new Player((long) connection.getID()));

                log.info(String.format("%s connected as #%d", connection.getRemoteAddressTCP(), connection.getID()));

                connection.sendTCP(new Connected(connection.getPlayer().getCorePlayer()));
                pcs.firePropertyChange("playerConnected", null, null);

                return;
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
                        if (room.hasPlayerWithID(player.getID())) {
                            connection.sendTCP(new CreatedRoom());

                            return;
                        }
                    }

                    Room room;
                    try {
                        room = RoomFactory.createRoom(msg.getName(), msg.getPassword());
                        room.addPlayer(player.getCorePlayer());
                    } catch (RoomNotFoundException e) {
                        connection.sendTCP(new CreatedRoom());

                        return;
                    }

                    log.info(String.format("#%d created room #%d (%s with password '%s')", player.getID(), room.getID(), room.getName(), msg.getPassword()));

                    connection.sendTCP(new CreatedRoom(room));
                    pcs.firePropertyChange("roomCreated", null, null);


                    FetchedRooms fetchedRooms = new FetchedRooms(RoomFactory.getRooms());
                    for (Connection con : server.getConnections()) {
                        PlayerConnection playerConnection = (PlayerConnection)con;
                        if (!RoomFactory.hasPlayerWithID(playerConnection.getPlayer().getID()) &&
                                !GameFactory.hasPlayerWithID(playerConnection.getPlayer().getID())) {
                            playerConnection.sendTCP(fetchedRooms);
                        }

                    }

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

                    // A player cannot join the same room twice
                    for (CorePlayer roomPlayer : room.getPlayers()) {
                        if (roomPlayer.getID().equals(player.getID())) {
                            connection.sendTCP(new JoinRoom());
                            return;
                        }
                    }


                    //TODO SEND ROOM IS FULL MESSAGE
                    /**
                     * Only 2 player can join the same room
                     */
                    if (room.getPlayers().size() >= 2) {
                        connection.sendTCP(new JoinRoom());
                        return;
                    }

                    room.addPlayer(player.getCorePlayer());

                    log.info(String.format("#%d joined room #%d (%s)", player.getID(), room.getID(), room.getName()));

                    JoinedRoom joinedRoom = new JoinedRoom(room, player.getCorePlayer());

                    System.out.println(joinedRoom.getPlayer().getName());

                    // Should be one player in the room, but doing it this way will support more players
                    for (CorePlayer roomPlayer : room.getPlayers()) {
                        getPlayerConnectionFromID(roomPlayer.getID()).sendTCP(joinedRoom);
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
                    if (room.getPlayers().get(0).getID().equals(player.getCorePlayer().getID())) {

                        List<Player> gamePlayers = new ArrayList<Player>();

                        for (CorePlayer corePlayer : room.getPlayers()) {
                            gamePlayers.add(getPlayerConnectionFromID(corePlayer.getID()).getPlayer());
                        }

                        Game game = GameFactory.createGame(gamePlayers);

                        log.info(String.format("#%d started game #%d", player.getID(), game.getID()));

                        for (Player gamePlayer : game.getPlayers()) {
                            getPlayerConnectionFromID(gamePlayer.getID()).sendTCP(new StartedGame(game.getID()));
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
                    log.info(String.format("#%d fetching rooms", player.getID()));

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

                    PlayerIsReady playerIsReady = new PlayerIsReady(player.getCorePlayer());
                    for (Player gamePlayer : game.getPlayers()) {
                        if (!gamePlayer.equals(player)) {
                            getPlayerConnectionFromID(gamePlayer.getID()).sendTCP(playerIsReady);
                        }
                    }

                    if (allReady) {
                        for (Player gamePlayer : game.getPlayers()) {
                            getPlayerConnectionFromID(gamePlayer.getID()).sendTCP(new GameReady());
                        }

                        log.info(String.format("Everyone is ready... Start game #%d", game.getID()));

                        // Send turn to random player
                        int starter = new Random().nextInt(game.getPlayers().size() - 1);
                        game.setAttacker(game.getPlayers().get(starter));

                        getPlayerConnectionFromID(game.getAttacker().getID()).sendTCP(new Turn());
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
                    if (msg.getTarget().getID().equals(player.getCorePlayer().getID()))
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
                    Player targetPlayer = getPlayerConnectionFromID(msg.getTarget().getID()).getPlayer();
                    try {
                        targetShip = game.fire(targetPlayer, msg.getCoordinate());
                    } catch (UsedCoordinateException e) {
                        log.info(String.format("#%d %s", player.getID(), e.getMessage()));
                        getPlayerConnectionFromID(player.getID()).sendTCP(new Turn());

                        return;
                    }

                    boolean hit = (targetShip != null);
                    FireResult fireResult = new FireResult(msg, hit);

                    for (Player gamePlayer : game.getPlayers()) {
                        getPlayerConnectionFromID(gamePlayer.getID()).sendTCP(fireResult);

                        /**
                         * Send message telling everyone that a ship was destroyed.
                         */
                        if (targetShip != null && targetShip.isDestroyed()) {
                            getPlayerConnectionFromID(gamePlayer.getID()).sendTCP(new DestroyedShip(msg.getTarget(), targetShip));
                        }
                    }

                    if (targetPlayer.fleetIsDestoyed()) {
                        for (Player gamePlayer : game.getPlayers()) {
                            /**
                             * Send message telling everyone who won and who lost
                             */
                            if (gamePlayer.getID().equals(targetPlayer.getID()))
                                getPlayerConnectionFromID(gamePlayer.getID()).sendTCP(new Looser());
                            else
                                getPlayerConnectionFromID(gamePlayer.getID()).sendTCP(new Winner());

                        }
                    }


                    game.changeAttacker();

                    getPlayerConnectionFromID(game.getAttacker().getID()).sendTCP(new Turn());


                    return;
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

                    if (!room.hasPlayerWithID(player.getID())) return;

                    for (CorePlayer roomPlayer : room.getPlayers()) {
                        getPlayerConnectionFromID(roomPlayer.getID()).sendTCP(new RemovedRoom());
                    }

                    /**
                     * If so, remove the room and notify the client
                     */
                    try {
                        RoomFactory.removeRoom(room);
                    } catch (RoomNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }

                    log.info("Room #" + msg.getRoomID() + " has been removed");
                    pcs.firePropertyChange("roomRemoved", null, null);

                    for (PlayerConnection con : getPlayerConnections()) {
                        con.sendTCP(new FetchedRooms(RoomFactory.getRooms()));
                    }

                    return;
                }

                /**
                 * Removes player from game when left game and announces to game members
                 */
                if (object instanceof LeaveGame) {
                    LeaveGame msg = (LeaveGame) object;
                    Game game = null;

                    try {
                        game = GameFactory.getGame(msg.getGameID());
                    } catch (GameNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (game != null) {
                        log.info("Player #" + player.getID() + " has left game #" + game.getID());

                        game.removePlayerWithID(player.getID());

                        for (Player gamePlayer : game.getPlayers()) {
                            getPlayerConnectionFromID(gamePlayer.getID()).sendTCP(new LeftGame(gamePlayer.getCorePlayer()));
                        }

                    }

                    return;
                }

                /**
                 * Removes player from room when left room and announces to room members
                 */
                if (object instanceof LeaveRoom) {
                    LeaveRoom msg = (LeaveRoom) object;
                    Room room = null;

                    try {
                        room = RoomFactory.getRoom(msg.getRoomID());
                    } catch (RoomNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (room != null) {
                        log.info("Player #" + player.getID() + " has left room #" + room.getID());

                        room.removePlayerWithID(player.getID());

                        for (CorePlayer roomPlayer : room.getPlayers()) {
                            getPlayerConnectionFromID(roomPlayer.getID()).sendTCP(new LeftRoom(player.getCorePlayer()));
                        }
                    }

                    return;
                }

                if (object instanceof PlayerChangeName) {
                    PlayerChangeName msg = (PlayerChangeName) object;

                    log.info(String.format("%s is now known as %s", player.getName(), msg.getName()));

                    System.out.println("TRYING CHANGED NAME" + msg.getName());

                    String name = msg.getName();
                    // TODO Should be regex...
                    //if (name.matches("\\w{1,16}")) {
                    if (name.length() < 16) {
                        System.out.println("CHANGED NAME" + msg.getName());
                        player.setName(msg.getName());
                        connection.sendTCP(new PlayerChangedName(player.getCorePlayer()));

                        pcs.firePropertyChange("playerChangedName", null, null);
                    } else {
                        connection.sendTCP(new PlayerChangedName());
                    }
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
                    if (room.hasPlayerWithID(player.getID())) {

                        if (room.isPlayerWithIDHost(player.getID())) {
                            try {
                                RoomFactory.removeRoom(room);
                            } catch (RoomNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        room.removePlayerWithID(player.getID());
                        for (CorePlayer opponent : room.getPlayers()) {
                            getPlayerConnectionFromID(opponent.getID()).sendTCP(new Disconnect(player.getCorePlayer()));
                        }

                        break;
                    }

                }

                for (Game game : GameFactory.getGames().values()) {
                    if (game.hasPlayerWithID(player.getID())) {

                        if (game.isPlayerWithIDHost(player.getID())) {
                            try {
                                GameFactory.removeGame(game);
                            } catch (GameNotFoundException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        game.removePlayerWithID(player.getID());
                        for (Player opponent : game.getPlayers()) {
                            getPlayerConnectionFromID(opponent.getID()).sendTCP(new Disconnect(player.getCorePlayer()));
                        }

                        break;
                    }

                }

                pcs.firePropertyChange("playerDisconnected", null, null);
                pcs.firePropertyChange("roomRemoved", null, null);
                pcs.firePropertyChange("gameRemoved", null, null);

                log.info(String.format("#%d disconnected", connection.getID()));
            }
        });

        server.bind(Network.PORT);
        server.start();
        log.info("Server started on port: " + Network.PORT);

    }

    protected PlayerConnection getPlayerConnectionFromID(Long id) {
        for (PlayerConnection playerConnection : getPlayerConnections()) {
            if (playerConnection.getPlayer().getID().equals(id)) {
                return playerConnection;
            }
        }

        return null;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    public List<PlayerConnection> getPlayerConnections() {
        List<PlayerConnection> list = new ArrayList<PlayerConnection>();
        for (Connection con : server.getConnections()) {
            list.add((PlayerConnection)con);
        }

        return list;
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
