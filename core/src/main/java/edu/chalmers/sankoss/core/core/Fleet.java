package edu.chalmers.sankoss.core.core;

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
    private int numberOfShips = 5;

    public Fleet() {
    }

    public Fleet(int size) {
        this.numberOfShips = size;
    }

    public int getLength() {
        return ships.size();
    }

    public boolean add(Ship ship) {
        return (((ships.size() + 1) <= numberOfShips) && validate(ship) && ships.add(ship));
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
