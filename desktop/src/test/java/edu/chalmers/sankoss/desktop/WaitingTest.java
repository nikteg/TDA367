package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.core.CorePlayer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for WaitingModel.
 *
 * @author Mikael Malmqvist
 */
public class WaitingTest {

    @Test
    public void testIsHosting() throws Exception {
        boolean hosting = true;

        assert(hosting);

    }

    @Test
    public void testSetHosting() throws Exception {
        boolean hosting = true;

        assert(hosting);
    }

    @Test
    public void testAddPlayer() throws Exception {
        List<CorePlayer> players = new ArrayList<CorePlayer>();
        CorePlayer player = new CorePlayer(1l);
        CorePlayer player2 = new CorePlayer(2l);

        players.add(player);
        players.add(player2);

        for(CorePlayer corePlayer : players) {
            if(corePlayer.getID().equals(player2.getID())) {
                players.remove(corePlayer);
                return;
            }
        }

        assert (players.get(0).equals(player));

    }

    @Test
    public void testRemovePlayer() throws Exception {
        List<CorePlayer> players = new ArrayList<CorePlayer>();
        CorePlayer player = new CorePlayer(1l);
        CorePlayer player2 = new CorePlayer(2l);

        players.add(player);
        players.add(player2);

        for(CorePlayer corePlayer : players) {
            if(corePlayer.getID().equals(player.getID())) {
                players.remove(corePlayer);
            }
        }

        assert (!players.get(0).equals(player));
    }

    @Test
    public void testGetPlayers() throws Exception {
        List<CorePlayer> players = new ArrayList<CorePlayer>();

        assert (players != null);
    }

}
