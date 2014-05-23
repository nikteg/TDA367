package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.mvc.placement.PlacementModel;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class PlacementModelTest {


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

}
