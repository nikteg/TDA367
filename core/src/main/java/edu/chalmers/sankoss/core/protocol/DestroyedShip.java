package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Ship;

/**
 *
 * @author Fredrik Thune
 *
 */
public class DestroyedShip {
    Player player;
    Ship ship;

    public DestroyedShip() {
    }

    public DestroyedShip(Player player, Ship ship) {
        this.player = player;
        this.ship = ship;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
