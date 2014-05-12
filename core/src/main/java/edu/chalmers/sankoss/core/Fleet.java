package edu.chalmers.sankoss.core;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fredrik Thune
 *
 */
public class Fleet {
    private ArrayList<Ship> ships = new ArrayList<Ship>();
    private int size = 4; // TODO will be 5

    public Fleet() {
    }

    public Fleet(int size) {
        this.size = size;
    }

    public boolean add(Ship ship) {
        return (((ships.size() + 1) <= size) && validate(ship) && ships.add(ship));
    }

    public boolean remove(Ship ship) {
        return ships.remove(ship);
    }

    public List<Ship> getShips() {
        return new ArrayList<Ship>(ships);
    }

    public Ship getShip(int index) {
        return ships.get(index);
    }

    private boolean validate(Ship ship) {

        Line2D shipLine = new Line2D.Double(ship.getFront().getX(), ship.getFront().getY(),
                                            ship.getRear().getX(), ship.getRear().getY());

        for (Ship otherShip : ships) {
            Line2D otherShipLine = new Line2D.Double(otherShip.getFront().getX(), otherShip.getFront().getY(),
                                                     otherShip.getRear().getX(), otherShip.getRear().getY());
            if(otherShipLine.intersectsLine(shipLine))
                return false;

        }
        return true;
    }
}
