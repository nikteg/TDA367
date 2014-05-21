package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.model.CorePlayer;

/**
 * 
 * @author Fredrik Thune
 * 
 */
public class PlayerIsReady {
    private CorePlayer player;

	public PlayerIsReady() {

	}

    public PlayerIsReady(CorePlayer player) {
        this.player = player;
    }

    public CorePlayer getPlayer() {
        return player;
    }

    public void setPlayer(CorePlayer player) {
        this.player = player;
    }
}
