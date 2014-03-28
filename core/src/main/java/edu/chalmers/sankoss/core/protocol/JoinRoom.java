package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Niklas Tegnander
 * 
 */
public class JoinRoom {
    private Long id;
    private Player player;
    
    public JoinRoom() {
        
    }
    
    public JoinRoom(Long id) {
        this.id = id;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
