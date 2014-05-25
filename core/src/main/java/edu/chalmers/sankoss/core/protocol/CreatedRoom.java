package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.core.Room;

/**
 *
 * @author Fredrik Thune
 *
 */
public class CreatedRoom {
    private Room room;

    public CreatedRoom() {
    }

    public CreatedRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
