package edu.chalmers.sankoss.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import edu.chalmers.sankoss.core.protocol.*;

/*
 * Temporary client
 */
public class SankossClient {
    private Client client;
    private Player player;
    private List<Player> opponents = new ArrayList<Player>();
    private boolean hosting = false;
    private Game game;

    public static void main(String[] args) {
        if (args.length > 0) {
            new SankossClient(args[0]);
        } else {
            new SankossClient("localhost");
        }
    }

    public SankossClient(String host) {
        client = new Client();
        client.start();

        Network.register(client);

        client.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection connection) {
                System.out.println("Connected to server: " + connection.getRemoteAddressTCP());
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Connect) {
                    Connect msg = (Connect) object;

                    System.out.println("GOT CONNECTION");

                    // Create new local player with remote ID
                    player = new Player(msg.getPlayerID());

                    // Fetch remote rooms
                    client.sendTCP(new FetchRooms());

                    return;
                }

                if (object instanceof FetchRooms) {
                    if (player == null) return;

                    FetchRooms msg = (FetchRooms) object;

                    // Get map with rooms and their ID
                    Map<Long, Room> rooms = msg.getRooms();

                    System.out.println(String.format("Fetched %d rooms", rooms.size()));

                    for (Room room : rooms.values()) {
                        System.out.println(String.format("Room: #%d %s", room.getID(), room.getName()));
                    }
                    
                    int choice = JOptionPane.showOptionDialog(null, "Create or join room?", "Create or join",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Join", "Create" },
                            "Create");

                    if (choice == 1) {
                        // Create room

                        String input = (String) JOptionPane.showInputDialog(null, "Room name:", "Room name", JOptionPane.QUESTION_MESSAGE, null, null, "Test room");
                        String name = input.trim();

                        client.sendTCP(new CreateRoom(name, ""));
                        hosting = true;
                    } else {
                        // Join room

                        if (rooms.size() == 0) {
                            System.out.println("No rooms to join!");
                            
                            return;
                        }

                        Room[] roomsArray = rooms.values().toArray(new Room[rooms.size()]);
                        String[] possibleValues = new String[roomsArray.length];
                        for (int i = 0; i < roomsArray.length; i++) {
                            possibleValues[i] = roomsArray[i].getName();
                        }
                        
                        int choice2 = JOptionPane.showOptionDialog(null, "Select room", "Select room", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, possibleValues, possibleValues[0]);
                        
                        client.sendTCP(new JoinRoom(roomsArray[choice2].getID()));
                    }
                    
                    return;
                }

                if (object instanceof JoinRoom) {
                    if (player == null) return;

                    JoinRoom msg = (JoinRoom) object;

                    if (player.getID() == msg.getPlayer().getID()) {
                        System.out.println("Waiting for host to start the game #" + msg.getID());
                    } else {
                        System.out.println(String.format("Player #%d joined...", msg.getID()));

                        // Stop if this player isn't the host
                        if (!hosting) return;

                        int choice;
                        do {
                            choice = JOptionPane.showOptionDialog(null, "Start game?", "Start game",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        } while (choice != 0);

                        client.sendTCP(new StartGame(msg.getID()));
                    }
                }

                if (object instanceof StartGame) {
                    if (player == null) return;

                    StartGame msg = (StartGame) object;

                    game = new Game(msg.getID(), opponents);

                    System.out.println(String.format("Placing ships! #%d", msg.getID()));

                    int choice;
                    do {
                    	choice = JOptionPane.showOptionDialog(null, "Are you ready?", "Ready", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                       	
                    } while(choice != 0);

                    System.out.println("CREATING FLEET");
                    List<Ship> fleet = new ArrayList<Ship>();
                    fleet.add(new Ship(2, new Coordinate(1,1), new Coordinate(1,3)));
                    fleet.add(new Ship(3, new Coordinate(2,1), new Coordinate(2,4)));

                    System.out.println("FLEET CREATED");

                   	client.sendTCP(new PlayerReady(msg.getID(), fleet));

                    System.out.println("FLEET SENT");
                   
                    return;
                }
                
                if (object instanceof GameReady) {
                    if (player == null) return;

                	System.out.println("Game started!");
                	return;
                }

                if (object instanceof Player) {
                    if (player == null) return;

                    Player msg = (Player) object;

                    if (msg.getID() == player.getID()) return;

                    opponents.add(msg);

                    System.out.println(String.format("#%d is ready!", msg.getID()));

                    return;
                }
                
                if (object instanceof Turn) {
                	String input = (String) JOptionPane.showInputDialog(null, "Skjut:", "Skjut", JOptionPane.QUESTION_MESSAGE, null, null, "1,1");
                    String coord = input.trim();
                    String[] coords = coord.split(",");

                    Player opponent = opponents.get(1);
                    Coordinate coordinate = new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));

                    client.sendTCP(new Fire(game.getID(), opponent, coordinate));

                    return;
                }
                
                
            }

            public void disconnected(Connection connection) {
                System.out.println("Disconnected from remote server...");
                System.exit(0);
            }
        }));

        try {
            client.connect(5000, host, Network.port);
        } catch (IOException e) {
            System.out.println("Could not connect to remote server...");
        }

        client.sendTCP(new Connect());

        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
