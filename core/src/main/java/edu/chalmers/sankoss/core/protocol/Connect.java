package edu.chalmers.sankoss.core.protocol;

/**
 * Created by nikteg on 29/03/14.
 */
public class Connect {
    private Long playerID;

    public Connect() {
    }

    public Connect(Long playerID) {
        this.playerID = playerID;
    }

    public Long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Long playerID) {
        this.playerID = playerID;
    }
}
