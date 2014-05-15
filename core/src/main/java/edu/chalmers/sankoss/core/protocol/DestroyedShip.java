package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Ship;

/**
 *
 * @author Fredrik Thune
 *
 */
public class DestroyedShip {
    private BasePlayer player;
    private Ship ship;

    public DestroyedShip() {
    }

    public DestroyedShip(BasePlayer player, Ship ship) {
        this.player = player;
        this.ship = ship;
    }

    public BasePlayer getPlayer() {
        return player;
    }

    public void setPlayer(BasePlayer player) {
        this.player = player;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
