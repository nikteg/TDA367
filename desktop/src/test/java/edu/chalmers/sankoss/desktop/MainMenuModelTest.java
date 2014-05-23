package edu.chalmers.sankoss.desktop;

import static org.junit.Assert.*;
import edu.chalmers.sankoss.desktop.mvc.mainMenu.MainMenuModel;

import org.junit.Test;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class MainMenuModelTest {

    @Test
    public void testIsConnected() {
        MainMenuModel testModel = new MainMenuModel();

        testModel.setConnected(true);

        assertTrue(testModel.isConnected());
    }

    @Test
    public void testSetConnected() {
        MainMenuModel testModel = new MainMenuModel();

        testModel.setConnected(false);

        assertTrue(!testModel.isConnected());
    }
}
