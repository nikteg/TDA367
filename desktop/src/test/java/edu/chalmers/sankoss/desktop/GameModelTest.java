package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.desktop.mvc.game.GameModel;
import edu.chalmers.sankoss.desktop.mvc.game.Shot;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 5/19/14
 */
public class GameModelTest {

    @Test
    public void testIsShootingAllowed() throws Exception {
        GameModel testModel = new GameModel();

        testModel.setShootingAllowed(true);

        assertTrue(testModel.isShootingAllowed());
    }

    @Test
    public void testAddShot() throws Exception {

        GameModel testModel = new GameModel();

        Coordinate coordinate = new Coordinate(5, 5);
        testModel.addShot(new Shot(coordinate, Shot.State.HIT));

        assertTrue(testModel.getShots().contains(coordinate));
    }


    @Test
    public void testToggleFlag() throws Exception {

        GameModel testModel = new GameModel();

        Coordinate coordinate = new Coordinate(1,3);
        testModel.toggleFlag(coordinate);

        assertTrue (testModel.getFlags().contains(coordinate));
    }


}
