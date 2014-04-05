package edu.chalmers.sankoss.core;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.chalmers.sankoss.core.protocol.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Fredrik Thune
 */
public class SankossAI implements Runnable {
    private Client client;
    private Player player;
    private List<Player> opponents = new ArrayList<Player>();
    private Long gameID;
    private Long roomID;

    public SankossAI(Long roomID) {
        this.roomID = roomID;
    }

    public void destroy() {
        client.close();
    }

    @Override
    public void run() {
        client = new Client();
        new Thread(client).start();

        Network.register(client);

        client.addListener(new Listener.ThreadedListener(new Listener() {
            public void connected(Connection connection) {
                System.out.println("AI: Connected to server: " + connection.getRemoteAddressTCP());
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Connected) {
                    Connected msg = (Connected) object;

                    // Create new local player with remote ID
                    player = new Player(msg.getPlayerID());

                    // Fetch remote rooms
                    client.sendTCP(new JoinRoom(roomID));

                    return;
                }

                if (object instanceof StartedGame) {
                    if (player == null) return;

                    StartedGame msg = (StartedGame) object;

                    gameID = msg.getGameID();

                    System.out.println(String.format("AI: Placing ships! #%d", msg.getGameID()));

                    List<Ship> fleet = new ArrayList<Ship>();

                    fleet.add(new Ship(new Coordinate(1, 5), new Coordinate(3, 5)));
                    fleet.add(new Ship(new Coordinate(3, 7), new Coordinate(4, 7)));
                    fleet.add(new Ship(new Coordinate(5, 2), new Coordinate(5, 5)));
                    fleet.add(new Ship(new Coordinate(9, 8), new Coordinate(9, 4)));
                    fleet.add(new Ship(new Coordinate(7, 2), new Coordinate(7, 3)));


                    client.sendTCP(new PlayerReady(msg.getGameID(), fleet));

                    return;
                }

                if (object instanceof GameReady) {
                    if (player == null) return;

                    System.out.println("AI: Game started!");
                    return;
                }

                if (object instanceof PlayerIsReady) {
                    if (player == null) return;

                    PlayerIsReady msg = (PlayerIsReady) object;

                    opponents.add(msg.getPlayer());

                    System.out.println(String.format("AI: #%d is ready!", msg.getPlayer().getID()));

                    return;
                }

                if (object instanceof Turn) {
                    Coordinate coordinate = null;
                    Player opponent = opponents.get(0); // Only if 2 players
                    Random r = new Random();
                    do {
                        coordinate = new Coordinate(r.nextInt(9) + 1, r.nextInt(9) + 1);
                    } while (player.getUsedCoordinates().contains(coordinate));

                    client.sendTCP(new Fire(gameID, opponent, coordinate));

                    return;
                }

            }
        }));

        try {
            client.connect(5000, "127.0.0.1", Network.PORT);
        } catch (IOException e) {
            System.out.println("AI: Could not connect to remote server...");
        }

        client.sendTCP(new Connect());
    }
}

