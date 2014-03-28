package edu.chalmers.sankoss.core.protocol;

import java.util.Map;

/**
 * 
 * @author Niklas Tegnander
 * 
 */
public class FetchRooms {
    private Map<Long, Room> rooms;

    public FetchRooms() {
        
    }
    
    public FetchRooms(Map<Long, Room> rooms) {
        this.rooms = rooms;
    }

    public Map<Long, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<Long, Room> rooms) {
        this.rooms = rooms;
    }
}
