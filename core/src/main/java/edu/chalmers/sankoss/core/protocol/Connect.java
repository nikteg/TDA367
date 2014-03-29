package edu.chalmers.sankoss.core.protocol;

/**
 * Created by nikteg on 29/03/14.
 */
public class Connect {
    private int playerID;

    public Connect() {
    }

    public Connect(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
