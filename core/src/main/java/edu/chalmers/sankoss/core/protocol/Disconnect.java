package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Fredrik Thune
 * 
 */
public class Disconnect {
	private Player player;

	public Disconnect() {

	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}