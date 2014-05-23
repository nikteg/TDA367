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
        return ((ships.size() + 1 <= numberOfShips) && ships.add(ship));
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



}
