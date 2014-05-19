package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.CorePlayer;

/**
 * 
 * @author Fredrik Thune
 * 
 */
public class Disconnect {
	private CorePlayer player;

	public Disconnect() {

	}

    public Disconnect(CorePlayer player) {
        this.player = player;
    }

    public CorePlayer getPlayer() {
		return player;
	}

	public void setPlayer(CorePlayer player) {
		this.player = player;
	}
}
