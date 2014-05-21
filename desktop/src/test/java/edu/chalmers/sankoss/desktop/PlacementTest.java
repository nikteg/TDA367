package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.model.CorePlayer;
import edu.chalmers.sankoss.core.model.Fleet;
import edu.chalmers.sankoss.core.model.Ship;

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
        Fleet fleet = new Fleet();
        Ship ship = new Ship();
        System.out.println(fleet.add(ship));

        assert(fleet.getShip(0).equals(ship));
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
