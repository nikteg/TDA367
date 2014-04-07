/**
 * 
 */
package edu.chalmers.sankoss.core;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;
import edu.chalmers.sankoss.core.Ship.ROTATION;

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
		Ship ship= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		assertTrue(ship.getStart().equals(new Coordinate(2,2)));
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#getEnd()}.
	 */
	@Test
	public void testGetEnd() {
		Ship ship= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		assertTrue(ship.getEnd().equals(new Coordinate(2,4)));
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#getSize()}.
	 */
	@Test
	public void testGetSize() {
		Ship ship= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		assertTrue(ship.getSize()==3);
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
		Ship ship= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		
		for(int i=0;i<3;i++){
			assertTrue(ship.getCoordinates().contains(new Coordinate(2,i+2)));
		}
		assertFalse(ship.getCoordinates().contains(new Coordinate(3,3)));
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#getRotation()}.
	 */
	@Test
	public void testGetRotation() {
		Ship shipWest= new Ship(new Coordinate(2,2), new Coordinate(4,2));
		Ship shipEast= new Ship(new Coordinate(4,2), new Coordinate(2,2));
		Ship shipNorth= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		Ship shipSouth= new Ship(new Coordinate(2,4), new Coordinate(2,2));
		
		assertTrue(shipWest.getRotation()== ROTATION.WEST);
		assertTrue(shipEast.getRotation()== ROTATION.EAST);
		assertTrue(shipNorth.getRotation()== ROTATION.NORTH);
		assertTrue(shipSouth.getRotation()== ROTATION.SOUTH);
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#shipHit()}.
	 */
	@Test
	public void testShipHit() {
		Ship ship= new Ship(new Coordinate(2,2), new Coordinate(3,2));
		ship.shipHit();
		ship.shipHit();
		assertTrue(ship.isDestroyed());
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#isDestroyed()}.
	 */
	@Test
	public void testIsDestroyed() {
		Ship ship= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		
		assertFalse(ship.isDestroyed());
		ship.shipHit();
		assertFalse(ship.isDestroyed());
		ship.shipHit();
		assertFalse(ship.isDestroyed());
		ship.shipHit();
		
		assertTrue(ship.isDestroyed());
	}

	/**
	 * Test method for {@link edu.chalmers.sankoss.core.Ship#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Ship ship1= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		Ship ship2= new Ship(new Coordinate(2,2), new Coordinate(2,4));
		Ship ship3= new Ship(new Coordinate(2,2), new Coordinate(2,5));
		
		assertTrue(ship1.equals(ship2));
		assertTrue(ship2.equals(ship1));
		assertTrue(ship1.equals(ship1));
		
		assertFalse(ship1.equals(ship3));
		
		assertFalse(ship1.equals("Ship"));	
	}

}
