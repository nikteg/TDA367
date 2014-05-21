/**
 * 
 */
package edu.chalmers.sankoss.core;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.core.core.Ship.Rotation;
import edu.chalmers.sankoss.core.exceptions.IllegalShipCoordinatesException;

/**
 * @author Daniel Eineving
 * @date 2014-04-07
 */
public class ShipTest {
	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		try {
			Ship ship1 = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));
			Ship ship2 = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));
			Ship ship3 = new Ship(new Coordinate(2, 2), new Coordinate(2, 5));

			assertTrue(ship1.hashCode() == ship2.hashCode()
					&& ship2.hashCode() != ship3.hashCode());
		} catch (IllegalShipCoordinatesException e) {
		}
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#Ship()}.
	 */
	@Test
	public void testShip() {
		Ship ship = new Ship();
		assertTrue(ship.getSize() == 0);
		assertTrue(ship.getCoordinates().size() == 0);
		assertTrue(ship.getFront() == null);
		assertTrue(ship.getRear() == null);
	}

	/**
	 * Test method for
	 * {@link edu.chalmers.sankoss.core.core.Ship#Ship(edu.chalmers.sankoss.core.core.Coordinate, edu.chalmers.sankoss.core.core.Coordinate)}
	 * .
	 */
	@Test
	public void testShipCoordinateCoordinate() {
		Ship ship = null;
		try {
			ship = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}

		assertTrue(ship.getFront().equals(new Coordinate(2, 2)));
		assertTrue(ship.getRear().equals(new Coordinate(2, 4)));

		try {
			Ship illegalShip = new Ship(new Coordinate(1, 1), new Coordinate(3,
					3));
			fail("Should not execute this row");
		} catch (IllegalShipCoordinatesException e) {
			assertTrue(e instanceof IllegalShipCoordinatesException);
		}
	}

	/**
	 * Test method for
	 * {@link edu.chalmers.sankoss.core.core.Ship#setCoordinates(edu.chalmers.sankoss.core.core.Coordinate, edu.chalmers.sankoss.core.core.Coordinate)}
	 * .
	 */
	@Test
	public void testSetCoordinates() {
		Ship ship = null;
		try {
			ship = new Ship(new Coordinate(4, 3), new Coordinate(4, 1));
			ship.setCoordinates(new Coordinate(2, 2), new Coordinate(2, 4));

			assertTrue(ship.getFront().equals(new Coordinate(2, 2)));
			assertTrue(ship.getRear().equals(new Coordinate(2, 4)));
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
		
		try{
			ship.setCoordinates(new Coordinate (2, 2),new Coordinate(4,4));
			fail("Exception should be thrown before this");
		}
		catch(IllegalShipCoordinatesException ignore){
			//Nothing
		}

	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#getFront()}.
	 */
	@Test
	public void testGetStart() {

		try {
			Ship ship = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));
			assertTrue(ship.getFront().equals(new Coordinate(2, 2)));
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#getRear()}.
	 */
	@Test
	public void testGetEnd() {
		try {
			Ship ship = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));
			assertTrue(ship.getRear().equals(new Coordinate(2, 4)));
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#getSize()}.
	 */
	@Test
	public void testGetSize() {
		try {
			Ship ship = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));
			assertTrue(ship.getSize() == 3);
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#getCoordinates()}.
	 */
	@Test
	public void testGetCoordinates() {

		try {
			Ship ship = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));

			LinkedList<Coordinate> list = new LinkedList<Coordinate>();
			list.add(new Coordinate(2, 2));
			list.add(new Coordinate(2, 3));
			list.add(new Coordinate(2, 4));

			assertTrue(ship.getCoordinates().equals(list));
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

	/**
	 * Test method for
	 * {@link edu.chalmers.sankoss.core.core.Ship#isShip(edu.chalmers.sankoss.core.core.Coordinate)}
	 * .
	 */
	@Test
	public void testIsShip() {
		try {
			Ship ship = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));

			for (int i = 0; i < 3; i++) {
				assertTrue(ship.isShip(new Coordinate(2, i + 2)));
			}
			assertFalse(ship.getCoordinates().contains(new Coordinate(3, 3)));
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#getRotation()}.
	 */
	@Test
	public void testGetRotation() {
		try {
			Ship shipWest = new Ship(new Coordinate(2, 2), new Coordinate(4, 2));
			Ship shipEast = new Ship(new Coordinate(4, 2), new Coordinate(2, 2));
			Ship shipNorth = new Ship(new Coordinate(2, 2),
					new Coordinate(2, 4));
			Ship shipSouth = new Ship(new Coordinate(2, 4),
					new Coordinate(2, 2));

			assertTrue(shipWest.getRotation() == Rotation.WEST);
			assertTrue(shipEast.getRotation() == Rotation.EAST);
			assertTrue(shipNorth.getRotation() == Rotation.NORTH);
			assertTrue(shipSouth.getRotation() == Rotation.SOUTH);
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#shipHit()}.
	 */
	@Test
	public void testShipHit() {
		try {
			Ship ship = new Ship(new Coordinate(2, 2), new Coordinate(3, 2));
			ship.shipHit();
			ship.shipHit();
			assertTrue(ship.isDestroyed());
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.core.Ship#isDestroyed()}.
	 */
	@Test
	public void testIsDestroyed() {
		try {
			Ship ship = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));

			assertFalse(ship.isDestroyed());
			ship.shipHit();
			assertFalse(ship.isDestroyed());
			ship.shipHit();
			assertFalse(ship.isDestroyed());
			ship.shipHit();

			assertTrue(ship.isDestroyed());
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

	/**
	 * Test method for
	 * {@link edu.chalmers.sankoss.core.core.Ship#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {

		try {
			Ship ship1 = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));
			Ship ship2 = new Ship(new Coordinate(2, 2), new Coordinate(2, 4));
			Ship ship3 = new Ship(new Coordinate(2, 2), new Coordinate(2, 5));

			assertTrue(ship1.equals(ship2));
			assertTrue(ship2.equals(ship1));
			assertTrue(ship1.equals(ship1));

			assertFalse(ship1.equals(ship3));

			assertFalse(ship1.equals("Ship"));
		} catch (IllegalShipCoordinatesException e) {
			fail("Should not throw exception");
		}
	}

}
