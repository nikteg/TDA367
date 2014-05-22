package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.desktop.mvc.waiting.WaitingModel;
import org.junit.Test;

/**
 * Test class for WaitingModel.
 *
 * @author Mikael Malmqvist
 */
public class WaitingModelTest {

    @Test
    public void testIsHosting() throws Exception {

        WaitingModel testModel = new WaitingModel();

        testModel.setHosting(true);

        assert(testModel.isHosting());

    }

    @Test
    public void testSetHosting() throws Exception {

        WaitingModel testModel = new WaitingModel();

        testModel.setHosting(true);

        assert(testModel.isHosting());
    }

    @Test
    public void testAddPlayer() throws Exception {

        WaitingModel testModel = new WaitingModel();

        CorePlayer player = new CorePlayer(1l);
        CorePlayer player2 = new CorePlayer(2l);

        testModel.addPlayer(player);
        testModel.addPlayer(player2);


        for(CorePlayer corePlayer : testModel.getPlayers()) {
            if(corePlayer.getID().equals(player2.getID())) {
                testModel.removePlayer(corePlayer);
                return;
            }
        }

        assert (testModel.getPlayers().get(0).equals(player));

    }

    @Test
    public void testRemovePlayer() throws Exception {

        WaitingModel testModel = new WaitingModel();

        CorePlayer player = new CorePlayer(1l);
        CorePlayer player2 = new CorePlayer(2l);

        testModel.addPlayer(player);
        testModel.addPlayer(player2);


        for(CorePlayer corePlayer : testModel.getPlayers()) {
            if(corePlayer.getID().equals(player.getID())) {
                testModel.removePlayer(corePlayer);
            }
        }

        assert (!testModel.getPlayers().get(0).equals(player));
    }

    @Test
    public void testGetPlayers() throws Exception {

        WaitingModel testModel = new WaitingModel();

        assert (testModel.getPlayers() != null);
    }

}
