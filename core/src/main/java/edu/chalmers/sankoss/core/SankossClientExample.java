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
public class SankossClientExample {
    private Client client;
    private Player player;
    private List<Player> opponents = new ArrayList<Player>();
    private Long gameID;
    private Long roomID;

    public static void main(String[] args) {
        if (args.length > 0) {
            new SankossClientExample(args[0]);
        } else {
            new SankossClientExample("localhost");
        }
    }

    public SankossClientExample(String host) {
        client = new Client();
        new Thread(client).start();

        Network.register(client);

        client.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection connection) {
                System.out.println("Connected to server: " + connection.getRemoteAddressTCP());
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Connected) {
                    Connected msg = (Connected) object;

                    // Create new local player with remote ID
                    player = new Player(msg.getPlayerID());

                    // Fetch remote rooms
                    client.sendTCP(new FetchRooms());

                    return;
                }

                if (object instanceof FetchedRooms) {
                    if (player == null) return;

                    FetchedRooms msg = (FetchedRooms) object;

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
                if (object instanceof CreatedRoom) {
                    CreatedRoom msg = (CreatedRoom) object;

                    roomID = msg.getRoomID();

                    int choice;
                    do {
                        choice = JOptionPane.showOptionDialog(null, "Start game?", "Start game",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    } while (choice != 0);

                    client.sendTCP(new StartGame(roomID));

                }

                if (object instanceof JoinedRoom) {
                    if (player == null) return;
                    JoinedRoom msg = (JoinedRoom) object;
                    if (msg.getPlayer().equals(player))
                        return;

                    System.out.println(String.format("Player #%d joined...", msg.getPlayer().getID()));
                }

                if (object instanceof StartedGame) {
                    if (player == null) return;

                    StartedGame msg = (StartedGame) object;

                    if (msg.getGameID() == null) {
                        int choice;
                        do {
                            choice = JOptionPane.showOptionDialog(null, "Start game?", "Start game",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        } while (choice != 0);

                        client.sendTCP(new StartGame(roomID));

                        return;
                    }

                    gameID = msg.getGameID();

                    System.out.println(String.format("Placing ships! #%d", msg.getGameID()));

                    int choice;
                    do {
                    	choice = JOptionPane.showOptionDialog(null, "Are you ready?", "Ready", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                       	
                    } while(choice != 0);

                    List<Ship> fleet = new ArrayList<Ship>();
                    fleet.add(new Ship(new Coordinate(1,1), new Coordinate(1,3)));
                    fleet.add(new Ship(new Coordinate(2,1), new Coordinate(2,4)));

                   	client.sendTCP(new PlayerReady(msg.getGameID(), fleet));

                    return;
                }
                
                if (object instanceof GameReady) {
                    if (player == null) return;

                	System.out.println("Game started!");
                	return;
                }

                if (object instanceof PlayerIsReady) {
                    if (player == null) return;

                    PlayerIsReady msg = (PlayerIsReady) object;

                    opponents.add(msg.getPlayer());

                    System.out.println(String.format("#%d is ready!", msg.getPlayer().getID()));

                    return;
                }
                
                if (object instanceof Turn) {
                    Coordinate coordinate = null;
                    String input = null;
                    Player opponent = opponents.get(0); // Only if 2 players

                    while (coordinate == null || input == null) {
                        input = (String) JOptionPane.showInputDialog(null, "Skjut:", "Skjuter - #" + player.getID(), JOptionPane.QUESTION_MESSAGE, null, null, "1,1");

                        try {
                            String coord = input.trim();
                            String[] coords = coord.split(",");
                            coordinate = new Coordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid coordinates. Turn is over, sucks to be you!");
                            coordinate = null;
                        } catch (NullPointerException e) {
                            System.out.println("Invalid format on input. Turn is over, sucks to be you!");
                            coordinate = null;
                        }
                    }

                    client.sendTCP(new Fire(gameID, opponent, coordinate));

                    return;
                }

                if (object instanceof FireResult) {
                    FireResult msg = (FireResult) object;

                    int x =  msg.getCoordinate().getX();
                    int y =  msg.getCoordinate().getY();

                    if (msg.getTarget().equals(player)) {
                        System.out.println("You are being fired at:" + x + "," + y + " " + (msg.isHit() ? "HIT" : "MISS"));
                    } else {
                        System.out.println("#" + msg.getTarget().getID() +
                                " are being fired at:" + x + "," + y + " " + (msg.isHit() ? "HIT" : "MISS"));

                    }
                    return;
                }
                if (object instanceof DestroyedShip) {
                    DestroyedShip msg = (DestroyedShip) object;
                    if (msg.getPlayer().equals(player)) {
                        System.out.println(String.format("Your ship %s got destroyed!", msg.getShip()));
                    } else {
                        System.out.println(String.format("Player #%d got their %s ship destroyed!!!", msg.getPlayer().getID(), msg.getShip()));
                    }
                    return;
                }
                
            }

            public void disconnected(Connection connection) {
                System.out.println("Disconnected from remote server...");
                System.exit(0);
            }
        }));

        try {
            client.connect(5000, host, Network.PORT);
        } catch (IOException e) {
            System.out.println("Could not connect to remote server...");
            System.exit(0);
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
