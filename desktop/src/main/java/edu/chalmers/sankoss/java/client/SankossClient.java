package edu.chalmers.sankoss.java.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.chalmers.sankoss.core.*;
import edu.chalmers.sankoss.core.protocol.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 */
public class SankossClient {
    private Client client;
    private SankossClientPlayer player;

    private List<BasePlayer> opponents = new ArrayList<BasePlayer>();
    private Long gameID;
    private Long roomID;
    private String host;
    private int timeout;
    private boolean ready = false;

    private List<ISankossClientListener> listeners = new ArrayList<ISankossClientListener>();

    public SankossClient(String host, int timeout) {
        this.host = host;
        this.timeout = timeout;

        initialize();
    }

    public SankossClient(String host) {
        this(host, 5000);
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean getReady() {
        return ready;
    }

    private void initialize() {
        client = new Client();
        new Thread(client).start();

        Network.register(client);

        client.addListener(new Listener.ThreadedListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Connected) {
                    Connected msg = (Connected) object;

                    player = new SankossClientPlayer(msg.getPlayerID());

                    for (ISankossClientListener listener : listeners) {
                        listener.connected(msg.getPlayerID());
                    }


                    return;
                }

                if (player == null) return;

                if (object instanceof FetchedRooms) {
                    FetchedRooms msg = (FetchedRooms) object;

                    System.out.println(msg.getRooms().toString());

                    for (ISankossClientListener listener : listeners) {
                        listener.fetchedRooms(msg.getRooms());
                    }

                    return;
                }

                if (object instanceof CreatedRoom) {
                    CreatedRoom msg = (CreatedRoom) object;

                    if (msg.getRoomID() == null)
                        throw new IllegalArgumentException("Invalid name or password format.");


                    roomID = msg.getRoomID();

                    for (ISankossClientListener listener : listeners) {
                        listener.createdRoom(roomID);
                    }

                    return;
                }

                if (object instanceof JoinedRoom) {
                    JoinedRoom msg = (JoinedRoom) object;

                    if (msg.getPlayer().equals(player.getBasePlayer()))
                        return;

                    for (ISankossClientListener listener : listeners) {
                        listener.joinedRoom(msg.getPlayer());
                    }

                    return;
                }

                if (object instanceof StartedGame) {
                    StartedGame msg = (StartedGame) object;
                    gameID = msg.getGameID();

                    for (ISankossClientListener listener : listeners) {
                        listener.startedGame(gameID);
                    }

                    return;
                }

                if (object instanceof GameReady) {
                    for (ISankossClientListener listener : listeners) {
                        listener.gameReady();
                    }

                    return;
                }

                if (object instanceof PlayerIsReady) {
                    PlayerIsReady msg = (PlayerIsReady) object;

                    opponents.add(msg.getPlayer());

                    for (ISankossClientListener listener : listeners) {
                        listener.playerIsReady(msg.getPlayer());
                    }

                    return;
                }

                if (object instanceof Turn) {
                    for (ISankossClientListener listener : listeners) {
                        listener.turn();
                    }

                    return;
                }

                if (object instanceof FireResult) {
                    FireResult msg = (FireResult) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.fireResult(msg.getGameID(), msg.getTarget(), msg.getCoordinate(), msg.isHit());
                    }

                    return;
                }

                if (object instanceof DestroyedShip) {
                    DestroyedShip msg = (DestroyedShip) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.destroyedShip(msg.getPlayer(), msg.getShip());
                    }

                    return;
                }

                if (object instanceof PlayerChangedName) {
                    PlayerChangedName msg = (PlayerChangedName) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.playerChangedName(msg.getPlayer());
                    }

                    return;
                }
            }

            public void disconnected(Connection connection) {

                for (ISankossClientListener listener : listeners) {
                    listener.disconnected();
                }
            }
        }));
    }

    public SankossClientPlayer getPlayer() {
        return player;
    }

    public List<BasePlayer> getOpponents() {
        return opponents;
    }

    public Long getGameID() {
        return gameID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void addListener(ISankossClientListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ISankossClientListener listener) {
        listeners.remove(listener);
    }

    public void connect() throws IOException {
        if (client == null) return;

        client.connect(timeout, host, Network.PORT);

        client.sendTCP(new Connect());
    }

    public void createRoom(String name, String password) {
        if (client == null) return;

        client.sendTCP(new CreateRoom(name, password));
    }

    public void removeRoom(Long roomID) {
        if (client == null) return;

        client.sendTCP(new RemoveRoom(roomID));
    }

    public void disconnect() {
        if (client == null) return;

        client.close();
    }

    public void playerChangeName(String name) {
        if (client == null) return;

        client.sendTCP(new PlayerChangeName(name));
    }

    public void fetchRooms() {
        if (client == null) return;

        client.sendTCP(new FetchRooms());
    }

    public void fire(Long gameID, BasePlayer target, Coordinate coordinate) {
        if (client == null) return;

        client.sendTCP(new Fire(gameID, target, coordinate));
    }

    public void joinRoom(Long roomID) {
        if (client == null) return;

        System.out.println("CLIENT: Trying to join #" + roomID);
        client.sendTCP(new JoinRoom(roomID));
    }

    public void playerReady(Long gameID, Fleet fleet) {
        if (client == null) return;

        client.sendTCP(new PlayerReady(gameID, fleet.getShips()));
    }

    public void startGame(Long roomID) {
        if (client == null) return;

        client.sendTCP(new StartGame(roomID));
    }

    @Override
    public String toString() {
        return player.toString();
    }

}
