package edu.chalmers.sankoss.core.protocol;

public class Game {
    private int id;
    private Player[] players = new Player[2];
    
    public Game() {
        
    }
    
    public Game(int id, Player[] players) {
        this.id = id;
        this.players = players;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Player[] getPlayers() {
        return players;
    }
    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
