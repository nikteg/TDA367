package edu.chalmers.sankoss.core.protocol;

/**
 *
 * @author Fredrik Thune
 *
 */
public class CreatedRoom {
    private Long roomID;

    public CreatedRoom() {
    }

    public CreatedRoom(Long roomID) {
        this.roomID = roomID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }
}
