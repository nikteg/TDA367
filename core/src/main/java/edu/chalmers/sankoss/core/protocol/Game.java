package edu.chalmers.sankoss.core.protocol;

public class Game {
    private int id;
    private Player[] players = new Player[2];
    private Player attacker;
    
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

	public Player getAttacker() {
		return attacker;
	}

	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}
	
	public void changeAttacker() {
		attacker = (attacker == players[0]) ? players[1] : players[0];
	}
    
}
