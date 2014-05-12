package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.BasePlayer;

/**
 * Created by nikteg on 30/04/14.
 */
public class PlayerChangedName {
    private BasePlayer player;

    public PlayerChangedName() {

    }

    public PlayerChangedName(BasePlayer player) {
        this.player = player;
    }

    public BasePlayer getPlayer() {
        return player;
    }
}