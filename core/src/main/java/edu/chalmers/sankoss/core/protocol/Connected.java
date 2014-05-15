package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.BasePlayer;

/**
 * @author Niklas Tegnander
 */
public class Connected {
    private BasePlayer player;

    public Connected() {
    }

    public Connected(BasePlayer player) {
        this.player = player;
    }

    public BasePlayer getBasePlayer() {
        return player;
    }

    public void setBasePlayer(BasePlayer player) {
        this.player = player;
    }
}

