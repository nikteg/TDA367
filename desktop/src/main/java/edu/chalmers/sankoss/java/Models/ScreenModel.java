package edu.chalmers.sankoss.java.Models;

import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.Settings;
import edu.chalmers.sankoss.java.client.SankossClient;

import java.io.IOException;
import java.util.Map;

/**
 * Abstraction of the game model.
 * The model will handle the client.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public abstract class ScreenModel {
    // Client to be shared by all instances - Controller
    // wont have to send the client between different models
    protected static SankossClient client = new SankossClient(Settings.HOSTNAME, Settings.PORT);
    protected static int numberOfShips;

    public ScreenModel() {

    }

    public ScreenModel(String host) {
        client = new SankossClient(host);
    }

    public ScreenModel(SankossClient client) {
        setClient(client);
    }

    public void setNumberOfShips(int numberOfShips) {
        this.numberOfShips = numberOfShips;
    }

    public int getNumberOfShips() {
        return this.numberOfShips;
    }

    public void setClient(SankossClient client) {
        this.client = client;
    }

    public SankossClient getClient() {
        return client;
    }

    /**
     * Method for connecting client.
     * Meant to be run one time only.
     */
    public void connectClient() {
        try {
            client.connect();

        } catch (IOException exc) {
            System.out.println("ERROR: Could not connect to " + client.getHost());
            exc.getStackTrace();
        }
    }

    // Abstract methods below!

    /**
     * Method to be used in Lobby.
     * Will return a Room in the Map, matching a specific room name.
     * @param roomName Name of the room to match.
     * @param rooms Map of rooms to check in.
     * @return Room matching the given name.
     */
    public abstract Room getRoomByName(String roomName, Map<Long, Room> rooms);

    /**
     * Method to be used in Lobby.
     * Sets the map of Rooms to given map.
     * @param roomMap Map to set to.
     */
    public abstract void setRoomMap(Map<Long, Room> roomMap);

}
