package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.model.CorePlayer;

/**
 * Will be sent to the host of the left room
 * @author Fredrik Thune
 */
public class LeftRoom {
    private CorePlayer player;

    public LeftRoom() {
    }

    public LeftRoom(CorePlayer player) {
        this.player = player;
    }

    public CorePlayer getPlayer() {
        return player;
    }

    public void setPlayer(CorePlayer player) {
        this.player = player;
    }
}
