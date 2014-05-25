package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.core.CorePlayer;

/**
 * Created by nikteg on 30/04/14.
 */
public class PlayerChangedName {
    private CorePlayer player;

    public PlayerChangedName() {

    }

    public PlayerChangedName(CorePlayer player) {
        this.player = player;
    }

    public CorePlayer getPlayer() {
        return player;
    }
}