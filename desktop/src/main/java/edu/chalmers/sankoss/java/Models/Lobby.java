package edu.chalmers.sankoss.java.Models;

import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.client.SankossClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class Lobby extends ScreenModel {
    private Map<Long, Room> roomMap = new HashMap<Long, Room>();
    private SankossClient client;
    private String[] names = new String[0];

    public Lobby() {
        this.roomMap = new HashMap<Long, Room>();
    }

    /**
     * Constructor to set Map of rooms.
     * @param roomMap map with rooms on network.
     */
    public Lobby(Map<Long, Room> roomMap) {
        this.roomMap = roomMap;
    }

    public void setClient(SankossClient client) {
        this.client = client;

    }

    public void setRoomMap(Map<Long, Room> roomMap) {
        this.roomMap = roomMap;
    }

    public Map<Long, Room> getRoomMap() {
        return this.roomMap;
    }

    /**
     * Method for getting key array from roomMap
     */
    public Object[] getKeys() {
        Set<Long> keys = roomMap.keySet();
        return keys.toArray();
    }

    /**
     * Method for getting Rooms from roomMap
     */
    public Room[] getRooms() {
        Object[] keys = getKeys();
        Room[] entries = new Room[keys.length];

        for(int i = 0; i < entries.length; i++) {
            entries[i] = roomMap.get(keys[i]);
        }

        return entries;
    }

    /**
     * Method for getting names from Rooms
     */
    public String[] getRoomNames(Room[] rooms) {
        String[] names = new String[rooms.length];

        for(int i = 0; i < names.length; i++) {
            names[i] = rooms[i].getName();
        }

        return names;
    }

    public void setNames(Room[] rooms) {
        this.names = new String[rooms.length];

        for(int i = 0; i < rooms.length; i++) {
            names[i] = rooms[i].getName();
        }
    }

    public String[] getNames() {
        return this.names;
    }

}
