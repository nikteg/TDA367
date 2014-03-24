package edu.chalmers.sankoss.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import edu.chalmers.sankoss.core.protocol.*;

public class SankossServer {
    private Server server;
    private HashMap<Player, PlayerConnection> playerConnections = new HashMap<Player, PlayerConnection>();
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Game> games = new ArrayList<Game>();

    public static void main(String[] args) throws IOException {
        new SankossServer();
    }

    public SankossServer() throws IOException {
        server = new Server() {
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };

        Network.register(server);

        server.addListener(new Listener() {
            public void connected(Connection c) {
                PlayerConnection connection = (PlayerConnection) c;

                if (connection.getPlayer() == null) {
                    int id = (new Random()).nextInt(); // TODO create better ID
                    connection.setPlayer(new Player(id, "Player"));
                }
            }

            public void received(Connection c, Object object) {
                PlayerConnection connection = (PlayerConnection) c;
                Player player = connection.getPlayer();

                if (object instanceof CreateRoom) {
                    CreateRoom msg = (CreateRoom) object;

                    System.out.println(String.format("%s: Created room '%s' with password '%s'",
                            connection.getRemoteAddressTCP(), msg.getName(), msg.getPassword()));

                    playerConnections.put(player, connection);

                    int id = (new Random()).nextInt(); // TODO create better ID
                    Room room = new Room(id, msg.getName(), msg.getPassword(), player);

                    // Add to room list
                    rooms.add(room);

                    connection.sendTCP(new CreateRoom());

                    return;
                }

                if (object instanceof JoinRoom) {
                    JoinRoom msg = (JoinRoom) object;

                    Room toJoinRoom = null;
                    for (Room room : rooms) {
                        if (room.getId() == msg.getId()) {
                            toJoinRoom = room;
                            rooms.remove(room);
                            break;
                        }
                    }

                    // Room does not exist, send error message to client
                    if (toJoinRoom == null)
                        return;

                    System.out.println(String.format("%s: Joining room '#%d %s'", connection.getRemoteAddressTCP(),
                            toJoinRoom.getId(), toJoinRoom.getName()));

                    int id = toJoinRoom.getId();
                    Player opponent = toJoinRoom.getPlayer();

                    playerConnections.put(player, connection);

                    Game game = new Game(id, new Player[] { player, opponent });

                    System.out.println(String.format("%s: Creating game '#%d' with players '#%d' and '#%d'",
                            connection.getRemoteAddressTCP(), game.getId(), player.getId(), opponent.getId()));

                    games.add(game);

                    CreateGame createGame = new CreateGame(game);

                    playerConnections.get(player).sendTCP(createGame);

                    // Send message to opponent
                    playerConnections.get(opponent).sendTCP(createGame);

                    return;
                }

                if (object instanceof FetchRooms) {
                    System.out.println(String.format("%s: Fetching rooms", connection.getRemoteAddressTCP()));

                    FetchRooms fetchRooms = new FetchRooms(rooms.toArray(new Room[rooms.size()]));

                    connection.sendTCP(fetchRooms);

                    return;
                }
            }

            public void disconnected(Connection c) {
                PlayerConnection connection = (PlayerConnection) c;
                Player player = connection.getPlayer();

                playerConnections.remove(player);
            }
        });

        server.bind(Network.port);
        server.start();
        System.out.println("Server started on port: " + Network.port);

    }
}
