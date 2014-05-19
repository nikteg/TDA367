package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.CorePlayer;

/**
 * @author Niklas Tegnander
 */
public class Connected {
    private CorePlayer player;

    public Connected() {
    }

    public Connected(CorePlayer player) {
        this.player = player;
    }

    public CorePlayer getPlayer() {
        return player;
    }

    public void setPlayer(CorePlayer player) {
        this.player = player;
    }
}

