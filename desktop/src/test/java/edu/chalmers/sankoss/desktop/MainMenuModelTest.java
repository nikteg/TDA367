package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.desktop.mvc.mainMenu.MainMenuModel;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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

}
