package edu.chalmers.sankoss.core;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.chalmers.sankoss.core.Player.Nationality;

/**
 * jUnitTest class for the class Player
 * 
 * @author Daniel Eineving
 * @date 2014-05-04
 */
public class PlayerTest {

	@Test
	public void testHashCode() {
		Player player1 = new Player((long) 314158979);
		Player player2 = new Player((long) 314158979);
		Player player3 = new Player((long) 133713372);
		Player player4 = new Player();
		
		assertTrue(player1.hashCode() == player2.hashCode());
		assertTrue(player2.hashCode() == player1.hashCode());
		
		assertFalse(player1.hashCode() == player3.hashCode());
		assertFalse(player1.hashCode() == player4.hashCode());
		assertFalse(player4.hashCode() == player3.hashCode());
	}

	@Test
	public void testPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlayerLong() {
		Player player1 = new Player((long) 314158979);
		Player player2 = new Player((long) 133713372);
		Player player3 = new Player();
		
		assertTrue(player1.getID() == 314158979);
		assertTrue(player2.getID() == 133713372);
		assertTrue(player3.getID() == null);
	}

	@Test
	public void testPlayerLongString() {
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372, "Mikael");
		Player player3 = new Player(null,"Fredrik");
		
		assertTrue(player1.getName().equals("Daniel"));
		assertTrue(player2.getName().equals("Mikael"));
		assertTrue(player3.getName().equals("Fredrik"));
	}

	@Test
	public void testSetNationality() {
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372, "Mikael");
		
		player1.setNationality(Nationality.JAPAN);
		player2.setNationality(Nationality.GERMANY);
		
		assertTrue(player1.getNationality().equals(Nationality.JAPAN));
		assertTrue(player2.getNationality().equals(Nationality.GERMANY));
	}

	@Test
	public void testGetNationality() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetID() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetID() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFleet() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFleet() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsReady() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetReady() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsedCoordinates() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUsedCoordiante() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetUsedCoordinates() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testWrite() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		fail("Not yet implemented");
	}

}
