package edu.chalmers.sankoss.core;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.chalmers.sankoss.core.BasePlayer.Nationality;

/**
 * jUnitTest class for the class BasePlayer
 * 
 * @author Daniel Eineving
 * @date 2014-05-04
 */
public class PlayerTest {

	@Test
	public void testHashCode() {
		BasePlayer player1 = new BasePlayer((long) 314158979);
		BasePlayer player2 = new BasePlayer((long) 314158979);
		BasePlayer player3 = new BasePlayer((long) 133713372);
		BasePlayer player4 = new BasePlayer();

		assertTrue(player1.hashCode() == player2.hashCode());
		assertTrue(player2.hashCode() == player1.hashCode());

		assertFalse(player1.hashCode() == player3.hashCode());
		assertFalse(player1.hashCode() == player4.hashCode());
		assertFalse(player4.hashCode() == player3.hashCode());
	}

	@Test
	public void testBasePlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testBasePlayerLong() {
		BasePlayer player1 = new BasePlayer((long) 314158979);
		BasePlayer player2 = new BasePlayer((long) 133713372);
		BasePlayer player3 = new BasePlayer();

		assertTrue(player1.getID() == 314158979);
		assertTrue(player2.getID() == 133713372);
		assertTrue(player3.getID() == null);
	}

	@Test
	public void testBasePlayerLongString() {
		BasePlayer player1 = new BasePlayer((long) 314158979, "Daniel");
		BasePlayer player2 = new BasePlayer((long) 133713372, "Mikael");
		BasePlayer player3 = new BasePlayer(null, "Fredrik");

		assertTrue(player1.getName().equals("Daniel"));
		assertTrue(player2.getName().equals("Mikael"));
		assertTrue(player3.getName().equals("Fredrik"));
	}

	@Test
	public void testSetNationality() {
		BasePlayer player1 = new BasePlayer((long) 314158979, "Daniel");
		BasePlayer player2 = new BasePlayer((long) 133713372, "Mikael");

		player1.setNationality(Nationality.JAPAN);
		player2.setNationality(Nationality.GERMANY);

		assertTrue(player1.getNationality().equals(Nationality.JAPAN));
		assertTrue(player2.getNationality().equals(Nationality.GERMANY));
	}

	@Test
	public void testGetNationality() {

		/*
		 * Circular tests, we need setNationality to work to know if this works,
		 * but we need getNationalitu to work if we are going to test
		 * setNationality
		 */
		BasePlayer player1 = new BasePlayer((long) 314158979, "Daniel");
		BasePlayer player2 = new BasePlayer((long) 133713372, "Mikael");

		player1.setNationality(Nationality.JAPAN);
		player2.setNationality(Nationality.GERMANY);

		assertTrue(player1.getNationality().equals(Nationality.JAPAN));
		assertTrue(player2.getNationality().equals(Nationality.GERMANY));
	}

	@Test
	public void testGetID() {
		BasePlayer player1 = new BasePlayer((long) 314158979, "Daniel");
		BasePlayer player2 = new BasePlayer((long) 133713372);

		assertTrue(player1.getID().equals((long) 314158979));
		assertTrue(player2.getID().equals((long) 133713372));
	}

	@Test
	public void testSetID() {
		BasePlayer player1 = new BasePlayer((long) 314158979, "Daniel");
		BasePlayer player2 = new BasePlayer((long) 133713372);
		BasePlayer player3 = new BasePlayer();

		player1.setID((long) 12345678);
		player2.setID((long) 44444444);
		player3.setID((long) 98765432);

		assertTrue(player1.getID().equals((long) 12345678));
		assertTrue(player2.getID().equals((long) 44444444));
		assertTrue(player3.getID().equals((long) 98765432));
	}

	@Test
	public void testGetName() {
		BasePlayer player1 = new BasePlayer((long) 314158979, "Daniel");
		BasePlayer player2 = new BasePlayer((long) 133713372, "Mikael");
		BasePlayer player3 = new BasePlayer(null, "Fredrik");

		assertTrue(player1.getName().equals("Daniel"));
		assertTrue(player2.getName().equals("Mikael"));
		assertTrue(player3.getName().equals("Fredrik"));

		player3.setName("Niklas");

		assertTrue(player3.getName().equals("Niklas"));
	}

	@Test
	public void testSetName() {
		BasePlayer player1 = new BasePlayer((long) 314158979, "Daniel");
		BasePlayer player2 = new BasePlayer((long) 133713372);
		BasePlayer player3 = new BasePlayer();

		player1.setName("Eineving");
		player2.setName("Laxen");
		player3.setName("Bips");

		assertTrue(player1.getName().equals("Eineving"));
		assertTrue(player2.getName().equals("Laxen"));
		assertTrue(player3.getName().equals("Bips"));
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
