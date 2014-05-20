package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.CorePlayer;

/**
 * Created by mikmal.
 */
public class PlayerChangeNat {
    private CorePlayer.Nationality nationality;

    public PlayerChangeNat() {

    }

    public PlayerChangeNat(CorePlayer.Nationality nationality) {
        this.nationality = nationality;
    }

    public CorePlayer.Nationality getNationality() {
        return nationality;
    }
}