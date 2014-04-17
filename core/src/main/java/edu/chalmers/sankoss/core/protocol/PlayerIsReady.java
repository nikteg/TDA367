package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.Player;

/**
 * 
 * @author Fredrik Thune
 * 
 */
public class PlayerIsReady {
    private Player player;

	public PlayerIsReady() {

	}

    public PlayerIsReady(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
