package edu.chalmers.sankoss.java.client;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Fleet;

/**
 * @author Fredrik Thune
 */
public class SankossClientPlayer extends CorePlayer {
    private Fleet fleet = new Fleet();

    public SankossClientPlayer() {
    }

    public SankossClientPlayer(Long playerID) {
        super(playerID);
    }

    public SankossClientPlayer(CorePlayer player) {
        super(player.getID(), player.getName());
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public CorePlayer getCorePlayer() {
        return new CorePlayer(getID(), getName(), getNationality());
    }
}
