package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.model.CorePlayer;

/**
 * Created by mikmal.
 */
public class PlayerChangedNat {
    private CorePlayer player;

    public PlayerChangedNat() {

    }

    public PlayerChangedNat(CorePlayer player) {
        this.player = player;
    }

    public CorePlayer getPlayer() {
        return player;
    }
}