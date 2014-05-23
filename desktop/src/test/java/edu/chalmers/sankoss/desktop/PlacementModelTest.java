package edu.chalmers.sankoss.desktop;

import static org.junit.Assert.*;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.mvc.placement.PlacementModel;

import org.junit.Test;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class PlacementModelTest {
    @Test
    public void testSetOpponentReady() throws Exception {

        PlacementModel testModel = new PlacementModel();

        testModel.setOpponentReady(true);

        assertTrue(testModel.isOpponentReady());
    }

    @Test
    public void testIsOpponentReady() throws Exception {

        PlacementModel testModel = new PlacementModel();

        testModel.setOpponentReady(true);

        assertTrue(testModel.isOpponentReady());
    }

    @Test
    public void testAddShip() throws Exception {

        PlacementModel testModel = new PlacementModel();

        Ship myShip = new Ship(new Coordinate(2,2), new Coordinate(2,5));
        testModel.addShip(myShip);

        assertTrue(testModel.getFleet().getShip(0).equals(myShip));
    }

    @Test
    public void testGetFlagPath() throws Exception {

        PlacementModel testModel = new PlacementModel();

        CorePlayer player = new CorePlayer();
        player.setNationality(CorePlayer.Nationality.USA);

        testModel.setNationality(player.getNationality());

        assertTrue (testModel.getNationality().getPath().equals("textures/USA.png"));

    }

    @Test
    public void testSetNationality() throws Exception {
        PlacementModel testModel = new PlacementModel();

        CorePlayer player = new CorePlayer();
        player.setNationality(CorePlayer.Nationality.USA);

        testModel.setNationality(player.getNationality());

        assertTrue (testModel.getNationality().equals(CorePlayer.Nationality.USA));
    }
}
