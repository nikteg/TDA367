package edu.chalmers.sankoss.core;

import edu.chalmers.sankoss.core.core.CorePlayer;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Test class for CorePlayer.
 * Will only test equals, due to setters
 * and getters.
 *
 * @author Mikael Malmqvist
 */
public class CorePlayerTest {

    @Test
    public void testEquals() throws Exception {
        CorePlayer testCorePlayer = new CorePlayer();

        testCorePlayer.setID(1l);
        testCorePlayer.setName("Test");

        assertTrue(testCorePlayer.equals(new CorePlayer(1l, "Test")));
    }
}
