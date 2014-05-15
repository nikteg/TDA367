package edu.chalmers.sankoss.java.models;

import org.junit.Test;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class PlacementTest {
    @Test
    public void testSwitchReadyBtnState() throws Exception {
        Placement.ReadyBtnState readyBtnState = Placement.ReadyBtnState.READY;

        readyBtnState = readyBtnState.getNext();

        assert(readyBtnState == Placement.ReadyBtnState.WAITING);
    }
}
