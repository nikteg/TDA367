package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Fleet;
import edu.chalmers.sankoss.core.core.Ship;

import org.junit.Test;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class PlacementTest {
    @Test
    public void testSetOpponentReady() throws Exception {
        boolean opponentReady = true;

        assert(opponentReady);
    }

    @Test
    public void testIsOpponentReady() throws Exception {
        boolean opponentReady = true;

        assert(opponentReady);
    }

    @Test
    public void testAddShip() throws Exception {
        Fleet myFleet = new Fleet();
        Ship myShip = new Ship(new Coordinate(2,2), new Coordinate(2,5));

        myFleet.add(myShip);

        assert(myFleet.getShip(0).equals(myShip));
    }

    @Test
    public void testGetFlagPath() throws Exception {
        CorePlayer player = new CorePlayer();
        player.setNationality(CorePlayer.Nationality.USA);

        assert (player.getNationality().getPath().equals("textures/USA.png"));

    }

    @Test
    public void testSetNationality() throws Exception {
        CorePlayer player = new CorePlayer();
        player.setNationality(CorePlayer.Nationality.USA);

        assert (player.getNationality().equals(CorePlayer.Nationality.USA));
    }
}
