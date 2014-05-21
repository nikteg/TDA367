package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.model.Room;

import java.util.Map;

/**
 * 
 * @author Niklas Tegnander
 * 
 */
public class FetchedRooms {
    private Map<Long, Room> rooms;

    public FetchedRooms() {

    }

    public FetchedRooms(Map<Long, Room> rooms) {
        this.rooms = rooms;
    }

    public Map<Long, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<Long, Room> rooms) {
        this.rooms = rooms;
    }
}
