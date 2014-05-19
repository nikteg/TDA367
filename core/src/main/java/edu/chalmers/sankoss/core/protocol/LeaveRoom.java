package edu.chalmers.sankoss.core.protocol;

/**
 * Sent when a player leaves a room
 * @author Fredrik Thune
 */
public class LeaveRoom {
    private Long roomID;

    public LeaveRoom() {
    }

    public LeaveRoom(Long roomID) {
        this.roomID = roomID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }
}
