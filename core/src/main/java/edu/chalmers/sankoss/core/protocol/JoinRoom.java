package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 */
public class JoinRoom {
    private Long roomID;
    
    public JoinRoom() {
        
    }

    public JoinRoom(Long roomID) {
        this.roomID = roomID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

}
