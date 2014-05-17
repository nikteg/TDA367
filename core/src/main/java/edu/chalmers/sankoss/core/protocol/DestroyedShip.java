package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Ship;

/**
 *
 * @author Fredrik Thune
 *
 */
public class DestroyedShip {
    private CorePlayer player;
    private Ship ship;

    public DestroyedShip() {
    }

    public DestroyedShip(CorePlayer player, Ship ship) {
        this.player = player;
        this.ship = ship;
    }

    public CorePlayer getPlayer() {
        return player;
    }

    public void setPlayer(CorePlayer player) {
        this.player = player;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
