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
        Shot testShot = new Shot(new Coordinate(5, 5), Shot.State.HIT);
        testModel.addShot(testShot);

        assertTrue(testModel.getShots().contains(testShot));
    }


    @Test
    public void testToggleFlag() throws Exception {

        GameModel testModel = new GameModel();

        Coordinate coordinate = new Coordinate(1,3);
        Coordinate coordinate2 = new Coordinate(5,7);
        testModel.toggleFlag(coordinate); // Should add flag
        testModel.toggleFlag(coordinate2); // Should add flag
        testModel.toggleFlag(coordinate2); // Should remove flag


        assertTrue (testModel.getFlags().contains(coordinate) && !testModel.getFlags().contains(coordinate2));
    }

    @Test
    public void testReset() throws Exception {

        GameModel testModel = new GameModel();


        testModel.setShootingAllowed(false);
        testModel.setState(GameModel.State.PLAYING);
        testModel.getShots().clear();
        testModel.getShips().clear();
        testModel.getOpponentShots().clear();
        testModel.getFlags().clear();
        testModel.setOpponent(null);

        assertTrue(testModel.getState().equals(GameModel.State.PLAYING)
                && !testModel.isShootingAllowed()
                && testModel.getShots().isEmpty()
                && testModel.getFlags().isEmpty()
                && testModel.getShips().isEmpty()
                && testModel.getOpponentShots().isEmpty()
                && testModel.getOpponent() == null);
    }


}
