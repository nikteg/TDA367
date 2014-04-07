/**
 * 
 */
package edu.chalmers.sankoss.core;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

/**
 * @author Daniel Eineving
 * @date 2014-04-07
 */
public class ShipTest {
	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		Ship ship1= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		Ship ship2= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		Ship ship3= new Ship(new Coordinate(2,2), new Coordinate(2,5));

		assertTrue(ship1.hashCode() == ship2.hashCode() && ship2.hashCode()!= ship3.hashCode());
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#Ship()}.
	 */
	@Test
	public void testShip(){
		Ship ship = new Ship();
		assertTrue(ship.getSize()==0);
		assertTrue(ship.getCoordinates().size()==0);
		assertTrue(ship.getStart()==null);
		assertTrue(ship.getEnd()==null);
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#Ship(edu.chalmers.sankoss.core.Coordinate, edu.chalmers.sankoss.core.Coordinate)}.
	 */
	@Test
	public void testShipCoordinateCoordinate() {
		Ship ship= new Ship(new Coordinate(2,2), new Coordinate(2,4));

		assertTrue(ship.getStart().equals(new Coordinate(2, 2)));
		assertTrue(ship.getEnd().equals(new Coordinate(2, 4)));
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#setCoordinates(edu.chalmers.sankoss.core.Coordinate, edu.chalmers.sankoss.core.Coordinate)}.
	 */
	@Test
	public void testSetCoordinates() {
		Ship ship = new Ship(new Coordinate(4,3), new Coordinate(4,1));
				
		ship.setCoordinates(new Coordinate(2,2), new Coordinate(2,4));

		assertTrue(ship.getStart().equals(new Coordinate(2, 2)));
		assertTrue(ship.getEnd().equals(new Coordinate(2, 4)));
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#getStart()}.
	 */
	@Test
	public void testGetStart() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#getEnd()}.
	 */
	@Test
	public void testGetEnd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#getSize()}.
	 */
	@Test
	public void testGetSize() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#getCoordinates()}.
	 */
	@Test
	public void testGetCoordinates() {
		Ship ship= new Ship(new Coordinate(2,2), new Coordinate(2,4));

		LinkedList<Coordinate> list= new LinkedList<Coordinate>();
		list.add(new Coordinate(2, 2));
		list.add(new Coordinate(2, 3));
		list.add(new Coordinate(2, 4));

		assertTrue(ship.getCoordinates().equals(list));
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#isShip(edu.chalmers.sankoss.core.Coordinate)}.
	 */
	@Test
	public void testIsShip() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#getRotation()}.
	 */
	@Test
	public void testGetRotation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#shipHit()}.
	 */
	@Test
	public void testShipHit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#isDestroyed()}.
	 */
	@Test
	public void testIsDestroyed() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

}
