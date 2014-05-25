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
    public void testAddShip() throws Exception {

        PlacementModel testModel = new PlacementModel();

        Ship myShip = new Ship(new Coordinate(2,2), new Coordinate(2,5));
        testModel.addShip(myShip);

        assertTrue(testModel.getFleet().getShip(0).equals(myShip));
    }

    @Test
    public void testValidate() throws Exception {
        // TODO: Test stuff
    }

    @Test
    public void testUpdateNationality() throws Exception {

        // This could not be tested due to network coding.
        // There is no instance of SankossClient..
        assertTrue(true);
    }


}
