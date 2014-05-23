package edu.chalmers.sankoss.desktop;

import static org.junit.Assert.*;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.desktop.mvc.game.GameModel;
import edu.chalmers.sankoss.desktop.mvc.game.Shot;

import org.junit.Test;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 5/19/14
 */
public class GameModelTest {

    @Test
    public void testSetShootingAllowed() throws Exception {

        GameModel testModel = new GameModel();

        testModel.setShootingAllowed(true);

        assertTrue(testModel.isShootingAllowed());

    }

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
    public void testGetYourShots() throws Exception {

        GameModel testModel = new GameModel();

        Coordinate coordinate = new Coordinate(2, 2);
        testModel.addShot(new Shot(coordinate, Shot.State.HIT));

        assertTrue(testModel.getShots().contains(coordinate));
    }

    @Test
    public void testGetState() throws Exception {

        GameModel testModel = new GameModel();

        testModel.setState(GameModel.State.LOST);

        assertTrue(testModel.getState().equals(GameModel.State.LOST));
    }

    @Test
    public void testSetState() throws Exception {

        GameModel testModel = new GameModel();

        testModel.setState(GameModel.State.WON);

        assertTrue(testModel.getState().equals(GameModel.State.WON));
    }

    @Test
    public void testSetOpponent() throws Exception {

        GameModel testModel = new GameModel();

        CorePlayer opponent = new CorePlayer(32l);
        testModel.setOpponent(opponent);

        assertTrue(testModel.getOpponent().equals(opponent));
    }

    @Test
    public void testGetOpponent() throws Exception {

        GameModel testModel = new GameModel();

        CorePlayer opponent = new CorePlayer(32l);
        testModel.setOpponent(opponent);

        assertTrue(testModel.getOpponent().equals(opponent));
    }

    @Test
    public void testToggleFlag() throws Exception {

        GameModel testModel = new GameModel();

        Coordinate coordinate = new Coordinate(1,3);
        testModel.toggleFlag(coordinate);

        assertTrue (testModel.getFlags().contains(coordinate));
    }

    @Test
    public void testGetFlags() throws Exception {

        GameModel testModel = new GameModel();

        Coordinate coordinate = new Coordinate(8,2);
        testModel.toggleFlag(coordinate);

        assertTrue (testModel.getFlags().contains(coordinate));
    }

}
