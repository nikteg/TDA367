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
        return ((ships.size() + 1 <= numberOfShips) && validate(ship) && ships.add(ship));
    }

    public boolean remove(Ship ship) {
        for (int i=0; i< getShips().size(); i++) {
            if (getShip(i).equals(ship)) {
                ships.remove(i);
                return true;
            }

        }

        return false;
    }

    public List<Ship> getShips() {
        return new ArrayList<Ship>(ships);
    }

    public Ship getShip(int index) {
        return ships.get(index);
    }

    private boolean validate(Ship ship) {

        if ((ship.getFront().getX() < 1 || ship.getFront().getX() > 10)
                || (ship.getFront().getY() < 1 || ship.getFront().getY() > 10))
            return false;


        if ((ship.getRear().getX() < 1 || ship.getRear().getX() > 10)
                || (ship.getRear().getY() < 1 || ship.getRear().getY() > 10))
            return false;

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
