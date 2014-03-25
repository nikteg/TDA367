package edu.chalmers.sankoss.core;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import edu.chalmers.sankoss.core.protocol.*;

/*
 * TEMPORARY
 */
public class SankossClient {
    private Client client;

    public static void main(String[] args) {
        new SankossClient();
    }

    public SankossClient() {
        client = new Client();
        client.start();

        Network.register(client);

        client.addListener(new ThreadedListener(new Listener() {
            public void connected(Connection connection) {
                System.out.println("Connected to server: " + connection.getRemoteAddressTCP());
            }

            public void received(Connection connection, Object object) {
                if (object instanceof FetchRooms) {
                    FetchRooms msg = (FetchRooms) object;
                    Room[] rooms = msg.getRooms();

                    System.out.println(String.format("Fetched %d rooms", rooms.length));

                    for (Room room : rooms) {
                        System.out.println(String.format("Room: #%d %s", room.getId(), room.getName()));
                    }
                    
                    int choice = JOptionPane.showOptionDialog(null, "Create or join room?", "Create or join",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Join", "Create" },
                            "Create");

                    switch (choice) {
                    case 1:
                        String input = (String) JOptionPane.showInputDialog(null, "Room name:", "Room name", JOptionPane.QUESTION_MESSAGE, null, null, "Test room");
                        String name = input.trim();
                        
                        client.sendTCP(new CreateRoom(name, ""));
                        
                        break;
                    case 0:
                        if (rooms.length == 0) {
                            System.out.println("No rooms to join!");
                            
                            return;
                        }
                        
                        String[] possibleValues = new String[rooms.length];
                        
                        for (int i = 0; i < rooms.length; i++) {
                            possibleValues[i] = rooms[i].getName();
                        }
                        
                        int choice2 = JOptionPane.showOptionDialog(null, "Select room", "Select room", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, possibleValues, possibleValues[0]);
                        
                        client.sendTCP(new JoinRoom(rooms[choice2].getId()));
                        
                        break;
                    }
                    
                    return;
                }
                
                if (object instanceof CreateGame) {
                    CreateGame msg = (CreateGame) object;
                    
                    System.out.println(String.format("Inside game '#%d'...", msg.getGame().getId()));
                }
            }

            public void disconnected(Connection connection) {
                System.out.println("Disconnected from remote server...");
                System.exit(0);
            }
        }));

        try {
            client.connect(5000, "sodapop.se", Network.port);
        } catch (IOException e) {
            System.out.println("Could not connect to remote server...");
        }

        client.sendTCP(new FetchRooms());

        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
