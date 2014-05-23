package edu.chalmers.sankoss.desktop.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.chalmers.sankoss.core.Network;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Fleet;
import edu.chalmers.sankoss.core.core.Room;
import edu.chalmers.sankoss.core.protocol.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niklas Tegnander
 * @modified Fredrik Thune, Daniel Einevi
 */
public class SankossClient {
	private static SankossClient instance;
	
    private Client client;
    private SankossClientPlayer player;

    private List<CorePlayer> opponents = new ArrayList<CorePlayer>();
    private Long gameID;
    private Room room;
    private boolean ready = false;
    private boolean hosting = false;

    private List<ISankossClientListener> listeners = new ArrayList<ISankossClientListener>();

    private SankossClient() {
        initialize();
    }
    public static SankossClient getInstance(){
    	if(instance == null){
    		instance = new SankossClient();
    	}
    	return instance;
    }

    public void reset() {
        opponents.clear();
        ready = false;
        hosting = false;

    }

    public void setReady(boolean ready) {
       this.ready = ready;
    }

    public boolean getReady() {
        return ready;
    }

    public void initialize() {
        client = new Client();
        new Thread(client).start();

        Network.register(client);

        client.addListener(new Listener.ThreadedListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof Connected) {
                    Connected msg = (Connected) object;

                    player = new SankossClientPlayer(msg.getPlayer());

                    for (ISankossClientListener listener : listeners) {
                        listener.connected();
                    }

                    return;
                }

                if (player == null) return;

                if (object instanceof FetchedRooms) {
                    FetchedRooms msg = (FetchedRooms) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.fetchedRooms(msg.getRooms());
                    }

                    return;
                }

                if (object instanceof CreatedRoom) {
                    CreatedRoom msg = (CreatedRoom) object;

                    if (msg.getRoom() == null)
                        throw new IllegalArgumentException("Invalid name or password format.");


                    room = msg.getRoom();
                    hosting = true;

                    for (ISankossClientListener listener : listeners) {
                        listener.createdRoom(room);
                    }

                    return;
                }

                if (object instanceof JoinedRoom) {
                    JoinedRoom msg = (JoinedRoom) object;

                    room = msg.getRoom();

                    for (ISankossClientListener listener : listeners) {
                        listener.joinedRoom(msg.getPlayer());
                    }

                    return;
                }

                if (object instanceof StartedGame) {
                    StartedGame msg = (StartedGame) object;
                    gameID = msg.getGameID();

                    hosting = false;

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
                        listener.fireResult(msg.getTarget(), msg.getCoordinate(), msg.isHit());
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

                if (object instanceof PlayerChangedNat) {
                    PlayerChangedNat msg = (PlayerChangedNat) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.playerChangedName(msg.getPlayer());
                    }

                    return;
                }

                if (object instanceof RemovedRoom) {
                    RemovedRoom msg = (RemovedRoom) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.removedRoom();
                    }

                    return;
                }

                if (object instanceof LeftGame) {
                    LeftGame msg = (LeftGame) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.leftGame(msg.getPlayer());
                    }

                    return;
                }

                if (object instanceof LeftRoom) {
                    LeftRoom msg = (LeftRoom) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.leftRoom(msg.getPlayer());
                    }

                    return;
                }

                if (object instanceof ErrorMsg) {
                    ErrorMsg msg = (ErrorMsg) object;

                    for (ISankossClientListener listener : listeners) {
                        listener.errorMsg(msg.getErrorObject(), msg.getErrorMessage());
                    }

                    return;
                }

                if(object instanceof Winner) {

                    for(ISankossClientListener listener : listeners) {
                        listener.winner();
                    }

                    return;
                }

                if(object instanceof Looser) {

                    for(ISankossClientListener listener : listeners) {
                        listener.looser();
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

    public boolean isHosting() {
        return room != null && ((room.getPlayers().size() > 0) && (room.getPlayers().get(0).getID().equals(player.getID())));
    }

    public SankossClientPlayer getPlayer() {
        return player;
    }

    public List<CorePlayer> getOpponents() {
        return opponents;
    }

    public Long getGameID() {
        return gameID;
    }

    public Room getRoom() {
        return room;
    }

    public void addListener(ISankossClientListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ISankossClientListener listener) {
        listeners.remove(listener);
    }

    public void connect(String hostname) throws IOException {
        connect(hostname, Network.PORT);
    }

    public void connect(String hostname, int port) throws IOException {
        if (client == null) return;

        /**
         * 5 second timeout.
         */
        client.connect(5000, hostname, port);
    }

    public boolean isConnected() {
        return client.isConnected();
    }

    public void createRoom(String name, String password) {
        if (client == null) return;

        client.sendTCP(new CreateRoom(name, password));
    }

    public void createRoom(String name) {
        createRoom(name, "");
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

    public void playerChangeNat(CorePlayer.Nationality nationality) {
        if (client == null) return;

        client.sendTCP(new PlayerChangeNat(nationality));
    }

    public void fetchRooms() {
        if (client == null) return;

        client.sendTCP(new FetchRooms());
    }

    public void fire(Coordinate coordinate) {
        if (client == null || getOpponents() == null) return;

        System.out.println("Opponent: " + getOpponents());
        System.out.println("Coordinates: " + coordinate.getX() + ", " + coordinate.getY());

        client.sendTCP(new Fire(gameID, getOpponents().get(0), coordinate));
    }

    public void joinRoom(Long roomID) {
        if (client == null) return;

        client.sendTCP(new JoinRoom(roomID));
    }

    public void playerReady(Fleet fleet) {
        if (client == null) return;

        client.sendTCP(new PlayerReady(gameID, fleet.getShips()));
    }

    public void startGame() {
        if (client == null) return;

        client.sendTCP(new StartGame(room.getID()));
    }

    public void leaveGame() {
        if (client == null) return;

        client.sendTCP(new LeaveGame(gameID));

        /**
         * Nullifies game when game has been left
         */
        gameID = null;
    }

    public void leaveRoom() {
        if (client == null) return;

        client.sendTCP(new LeaveRoom(room.getID()));

        /**
         * Nullifies room when room has been left
         */
        room = null;
    }

    @Override
    public String toString() {
        return player.toString();
    }

}
