package edu.chalmers.sankoss.java;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.server.server.Player;

import org.junit.Test;


/**
 * jUnitTest class for the class Player
 * 
 * @author Daniel Eineving
 * @date 2014-05-25
 */
public class PlayerTest {

	@Test
	public void testPlayerLong() {
		Player player1 = new Player((long) 314158979);
		Player player2 = new Player((long) 133713372);
		Player player3 = new Player();

		assertTrue(player1.getID() == 314158979);
		assertTrue(player2.getID() == 133713372);
		assertTrue(player3.getID() == (long) -1);
	}

	@Test
	public void testPlayerLongString() {
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372, "Mikael");
		Player player3 = new Player(null, "Fredrik");

		assertTrue(player1.getName().equals("Daniel"));
		assertTrue(player2.getName().equals("Mikael"));
		assertTrue(player3.getName().equals("Fredrik"));
	}

	@Test
	public void testSetNationality() {
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372, "Mikael");

		player1.setNationality(CorePlayer.Nationality.JAPAN);
		player2.setNationality(CorePlayer.Nationality.GERMANY);

		assertTrue(player1.getNationality().equals(CorePlayer.Nationality.JAPAN));
		assertTrue(player2.getNationality().equals(CorePlayer.Nationality.GERMANY));
	}

	@Test
	public void testGetNationality() {

		/*
		 * Circular tests, we need setNationality to work to know if this works,
		 * but we need getNationalitu to work if we are going to test
		 * setNationality
		 */
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372, "Mikael");

		player1.setNationality(CorePlayer.Nationality.JAPAN);
		player2.setNationality(CorePlayer.Nationality.GERMANY);

		assertTrue(player1.getNationality().equals(CorePlayer.Nationality.JAPAN));
		assertTrue(player2.getNationality().equals(CorePlayer.Nationality.GERMANY));
	}

	@Test
	public void testGetID() {
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372);

		assertTrue(player1.getID().equals((long) 314158979));
		assertTrue(player2.getID().equals((long) 133713372));
	}

	@Test
	public void testSetID() {
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372);
		Player player3 = new Player();

		player1.setID((long) 12345678);
		player2.setID((long) 44444444);
		player3.setID((long) 98765432);

		assertTrue(player1.getID().equals((long) 12345678));
		assertTrue(player2.getID().equals((long) 44444444));
		assertTrue(player3.getID().equals((long) 98765432));
	}

	@Test
	public void testGetName() {
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372, "Mikael");
		Player player3 = new Player(null, "Fredrik");

		assertTrue(player1.getName().equals("Daniel"));
		assertTrue(player2.getName().equals("Mikael"));
		assertTrue(player3.getName().equals("Fredrik"));

		player3.setName("Niklas");

		assertTrue(player3.getName().equals("Niklas"));
	}

	@Test
	public void testSetName() {
		Player player1 = new Player((long) 314158979, "Daniel");
		Player player2 = new Player((long) 133713372);
		Player player3 = new Player();

		player1.setName("Eineving");
		player2.setName("Laxen");
		player3.setName("Bips");

		assertTrue(player1.getName().equals("Eineving"));
		assertTrue(player2.getName().equals("Laxen"));
		assertTrue(player3.getName().equals("Bips"));
	}

	@Test
	public void testGetFleet() {
		Player player = new Player((long) 314158979, "Daniel");
		List<Ship> fleet = new LinkedList<Ship>();
		try {
			fleet.add(new Ship(new Coordinate(2, 2), new Coordinate(2, 4)));
			fleet.add(new Ship(new Coordinate(3, 2), new Coordinate(3, 3)));
			fleet.add(new Ship(new Coordinate(4, 2), new Coordinate(4, 4)));
			fleet.add(new Ship(new Coordinate(5, 2), new Coordinate(5, 5)));
		} catch (Exception ignore) {}
		
		player.setFleet(fleet);
		
		assertTrue(fleet.equals(player.getFleet()));
	}

	@Test
	public void testSetFleet() {
		//Same test as testGetFleet
		Player player = new Player((long) 314158979, "Daniel");
		List<Ship> fleet = new LinkedList<Ship>();
		try {
			fleet.add(new Ship(new Coordinate(2, 2), new Coordinate(2, 4)));
			fleet.add(new Ship(new Coordinate(3, 2), new Coordinate(3, 3)));
			fleet.add(new Ship(new Coordinate(4, 2), new Coordinate(4, 4)));
			fleet.add(new Ship(new Coordinate(5, 2), new Coordinate(5, 5)));
		} catch (Exception ignore) {}
		
		player.setFleet(fleet);
		
		assertTrue(fleet.equals(player.getFleet()));
	}

	@Test
	public void testIsReady() {
		Player player = new Player();
		player.setReady(true);
		assertTrue(player.isReady());
		
		player.setReady(false);
		assertFalse(player.isReady());
	}
}
