package edu.chalmers.sankoss.core;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.core.exceptions.IllegalShipCoordinatesException;

/**
 * @author Daniel Eineving
 * @date 2014-04-09
 */
public class CoordinateTest {

	@Test
	public void testHashCode() {
		Coordinate cor1 = new Coordinate(2,3);
		Coordinate cor2 = new Coordinate(2,3);
		Coordinate cor3 = new Coordinate(3,2);

		assertTrue(cor1.hashCode()==cor2.hashCode());
		assertFalse(cor1.hashCode()==cor3.hashCode());
	}

	@Test
	public void testCoordinateIntInt() {
		Coordinate cor = new Coordinate(3,6);

		assertTrue(cor.getX()==3);
		assertTrue(cor.getY()==6);
	}

	@Test
	public void testGetX() {
		Coordinate cor = new Coordinate(3,6);
		assertTrue(cor.getX()==3);
	}

	@Test
	public void testGetY() {
		Coordinate cor = new Coordinate(3,6);
		assertTrue(cor.getY()==6);
	}

	@Test
	public void testEqualsObject() {
		Coordinate cor1 = new Coordinate(2,3);
		Coordinate cor2 = new Coordinate(2,3);
		Coordinate cor3 = new Coordinate(3,2);
		
		assertTrue(cor1.equals(cor2));
		assertTrue(cor2.equals(cor1));
		assertTrue(cor1.equals(cor1));
		
		assertFalse(cor1.equals(cor3));
		assertFalse(cor3.equals(cor1));
		
		try {
			assertFalse(cor1.equals(new Ship(new Coordinate (2,2), new Coordinate(2,4))));
		} catch (IllegalShipCoordinatesException ignored) {
			
		}
	}

}
