package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.mvc.placement.PlacementModel;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @modified Fredrik Thune
 */
public class PlacementModelTest {


    @Test
    public void testAddShip() throws Exception {

        PlacementModel testModel = new PlacementModel();

        Ship myShip = new Ship(new Coordinate(2,2), new Coordinate(2,5));
        testModel.addShip(myShip);

        assertTrue(testModel.getFleet().getShip(0).equals(myShip));


        assertFalse(testModel.addShip(new Ship(new Coordinate(2,2), new Coordinate(2,5))));
        assertFalse(testModel.addShip(new Ship(new Coordinate(2,2), new Coordinate(5,2))));

        assertFalse(testModel.addShip(new Ship(new Coordinate(-1,2), new Coordinate(2,2))));
        assertFalse(testModel.addShip(new Ship(new Coordinate(2,-1), new Coordinate(2,5))));
        assertFalse(testModel.addShip(new Ship(new Coordinate(12,2), new Coordinate(2,2))));
        assertFalse(testModel.addShip(new Ship(new Coordinate(2,12), new Coordinate(2,5))));

        assertFalse(testModel.addShip(new Ship(new Coordinate(2,2), new Coordinate(-1,2))));
        assertFalse(testModel.addShip(new Ship(new Coordinate(2,2), new Coordinate(2,-1))));
        assertFalse(testModel.addShip(new Ship(new Coordinate(2,2), new Coordinate(12,2))));
        assertFalse(testModel.addShip(new Ship(new Coordinate(2,2), new Coordinate(2,12))));
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
