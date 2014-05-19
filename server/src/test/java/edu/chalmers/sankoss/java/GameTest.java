package edu.chalmers.sankoss.java;

import edu.chalmers.sankoss.core.exceptions.IllegalShipCoordinatesException;
import org.junit.Test;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Ship;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Fredrik Thune
 */
public class GameTest {

    @Test
    public void testUsedCoordinates() {
        Player player1 = new Player(new Long(1), "player1");
        int size = player1.getUsedCoordinates().size();
        Player player2 = new Player(new Long(2), "player2");

        List<Player> list = new ArrayList<Player>();
        list.add(player1);
        list.add(player2);

        Game game = new Game(new Long(3), list);
        game.setAttacker(player1);

        Coordinate cor = new Coordinate(5,3);
        try {
            game.fire(player2, cor);
        } catch (UsedCoordinateException e) {
            fail("Should not throw exception");
        }
        assertFalse(player1.getUsedCoordinates().size() == size);
        assertTrue(player1.getUsedCoordinates().contains(cor));

    }

    @Test
    public void testAttacker() {
        Player player1 = new Player(new Long(1), "player1");
        Player player2 = new Player(new Long(2), "player2");

        List<Player> list = new ArrayList<Player>();
        list.add(player1);
        list.add(player2);

        Game game = new Game(new Long(3), list);

        assertFalse(game.getAttacker() == player1);
        assertFalse(game.getAttacker() == player2);

        game.setAttacker(player1);

        assertTrue(game.getAttacker() == player1);
        assertFalse(game.getAttacker() == player2);

        game.changeAttacker();

        assertFalse(game.getAttacker() == player1);
        assertTrue(game.getAttacker() == player2);

    }
    @Test
    public void testHit() {
        Player player1 = new Player(new Long(1), "player1");
        Player player2 = new Player(new Long(2), "player2");

        List<Ship> fleet = new ArrayList<Ship>();
        try {
            fleet.add(new Ship(new Coordinate(1,1), new Coordinate(1,3)));
            fleet.add(new Ship(new Coordinate(2,1), new Coordinate(2,4)));
        }
        catch (IllegalShipCoordinatesException ignore) {
            fail("Should not throw exception");
        }
        player1.setFleet(fleet);

        List<Player> list = new ArrayList<Player>();
        list.add(player1);
        list.add(player2);

        Game game = new Game(new Long(3), list);

        game.setAttacker(player2);

        try {
            assertFalse(game.fire(player1, new Coordinate(1,1)) == null);
            assertTrue(game.fire(player1, new Coordinate(5,5)) == null);
        } catch (UsedCoordinateException e) {
            fail("Should not throw exception");
        }

        try{
            game.fire(player1, new Coordinate(1,1));
            fail("Exception should be thrown before this");
        } catch (UsedCoordinateException ignore) {
        }

        try {
            game.fire(player1, new Coordinate(1,2));
            Ship ship = game.fire(player1, new Coordinate(1,3));
            assertTrue(ship.isDestroyed());
        } catch (UsedCoordinateException e) {
            fail("Should not throw exception");
        }

    }

    @Test
    public void testWinning() {
        Player player1 = new Player(new Long(1), "player1");
        Player player2 = new Player(new Long(2), "player2");

        List<Ship> fleet = new ArrayList<Ship>();
        try {
            fleet.add(new Ship(new Coordinate(1,1), new Coordinate(1,3)));
            fleet.add(new Ship(new Coordinate(2,1), new Coordinate(2,4)));
        }
        catch (IllegalShipCoordinatesException ignore) {
            fail("Should not throw exception");
        }
        player1.setFleet(fleet);

        List<Player> list = new ArrayList<Player>();
        list.add(player1);
        list.add(player2);

        Game game = new Game(new Long(3), list);

        game.setAttacker(player2);

        try {
            game.fire(player1, new Coordinate(1,1));
            game.fire(player1, new Coordinate(1,2));
            game.fire(player1, new Coordinate(1,3));

            assertFalse(player1.fleetIsDestoyed());

            game.fire(player1, new Coordinate(2,1));
            game.fire(player1, new Coordinate(2,2));
            game.fire(player1, new Coordinate(2,3));
            game.fire(player1, new Coordinate(2,4));

            assertTrue(player1.fleetIsDestoyed());
        } catch (UsedCoordinateException e) {
            e.printStackTrace();
        }


    }

}
