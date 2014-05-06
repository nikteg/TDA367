package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 */
public class JoinRoom {
    private Long roomID;
    private String playerName;
    
    public JoinRoom() {
        
    }

    public JoinRoom(Long roomID, String playerName) {
        this.roomID = roomID;
        this.playerName = playerName;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public String getPlayerName() {
        return playerName;
    }
}
