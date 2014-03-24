package edu.chalmers.sankoss.core.protocol;

public class JoinRoom {
    private int id;
    private Player player;
    
    public JoinRoom() {
        
    }
    
    public JoinRoom(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
