package edu.chalmers.sankoss.core.protocol;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class RemoveRoom {
    private Long roomID;

    public RemoveRoom() {

    }

    public RemoveRoom(Long roomID) {
        this.roomID = roomID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

}
