package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.BasePlayer;

/**
 * 
 * @author Fredrik Thune
 * 
 */
public class PlayerIsReady {
    private BasePlayer player;

	public PlayerIsReady() {

	}

    public PlayerIsReady(BasePlayer player) {
        this.player = player;
    }

    public BasePlayer getPlayer() {
        return player;
    }

    public void setPlayer(BasePlayer player) {
        this.player = player;
    }
}
