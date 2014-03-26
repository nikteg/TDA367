package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Niklas Tegnander
 * 
 */
public class FetchRooms {
    private Room[] rooms;

    public FetchRooms() {
        
    }
    
    public FetchRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }
}
