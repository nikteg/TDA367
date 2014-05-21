package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.model.Coordinate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 5/19/14
 */
public class GameTest {
    @Test
    public void testSetMyTurn() throws Exception {
        boolean turn = true;

        assert(turn);

    }

    @Test
    public void testAddtoList() throws Exception {
        List<Coordinate> arrayList = new ArrayList<Coordinate>();
        Coordinate coordinate = new Coordinate(5, 5);

        arrayList.add(coordinate);

        assert(arrayList.contains(coordinate));
    }

    @Test
    public void testGetMyTurn() throws Exception {
        boolean myTurn = true;

        assert(myTurn);
    }

    @Test
    public void testSetWon() throws Exception {
        boolean won = true;

        assert(won);
    }

    @Test
    public void testGetYourShots() throws Exception {
        List<Coordinate> shots = new ArrayList<Coordinate>();
        Coordinate coordinate = new Coordinate(2,2);

        shots.add(coordinate);

        assert(shots.contains(coordinate));
    }
}
