package edu.chalmers.sankoss.core.protocol;

/**
 * @author Fredrik Thune
 */
public class CreateAI {
    private Long roomID;

    public CreateAI() {
    }

    public CreateAI(Long roomID) {
        this.roomID = roomID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }
}
