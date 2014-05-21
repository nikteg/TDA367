package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.model.CorePlayer;

/**
 * Will be sent to the host of the left game
 * @author Fredrik Thune
 */
public class LeftGame {
    private CorePlayer player;

    public LeftGame() {
    }

    public LeftGame(CorePlayer player) {
        this.player = player;
    }

    public CorePlayer getPlayer() {
        return player;
    }

    public void setPlayer(CorePlayer player) {
        this.player = player;
    }
}
