package edu.chalmers.sankoss.core.protocol;

/**
 *
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 */
public class StartGame {
    private Long roomID;

    public StartGame() {
    }

    public StartGame(Long roomID) {
        this.roomID = roomID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }
}
