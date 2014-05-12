package edu.chalmers.sankoss.java.client;

import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Fleet;

/**
 * @author Fredrik Thune
 */
public class SankossClientPlayer extends BasePlayer{
    private Fleet fleet = new Fleet();

    public SankossClientPlayer() {
    }

    public SankossClientPlayer(Long playerID) {
        super(playerID);
        setName("Player #" + playerID);
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public BasePlayer getBasePlayer() {
        return new BasePlayer(getID(), getName(), getNationality());
    }
}
