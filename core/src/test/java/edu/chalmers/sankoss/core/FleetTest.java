package edu.chalmers.sankoss.core;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.Fleet;
import edu.chalmers.sankoss.core.core.Ship;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Fredrik Thune
 */
public class FleetTest {
    @Test
    public void testNumberOfShips() {
        Fleet fleet = new Fleet();


        for(int i=0; i<fleet.getMaxNumberOfShips(); i++) {
            assertTrue(fleet.add(new Ship()));
        }
        //Should not be able to add new ship if fleet is full
        int temp = fleet.getLength();
        assertFalse(fleet.add(new Ship()));
        assertTrue(temp == fleet.getLength());

    }

    @Test
    public void testRemoveShips() {
        Fleet fleet = new Fleet();

        Ship ship1 = new Ship(new Coordinate(1,1), new Coordinate(1,4));
        fleet.add(ship1);
        Ship ship2 = new Ship(new Coordinate(3,2), new Coordinate(6,2));
        fleet.add(ship2);

        int temp = fleet.getLength();
        assertTrue(fleet.remove(ship1));
        assertFalse(temp == fleet.getLength());

    }
}
