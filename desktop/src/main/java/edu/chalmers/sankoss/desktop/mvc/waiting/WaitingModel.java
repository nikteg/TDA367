package edu.chalmers.sankoss.desktop.mvc.waiting;

import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.desktop.mvc.AbstractModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Necessary model for WaitingScreen.
 *
 * @author Mikael Malmqvist
 * @date 5/5/14
 */
public class WaitingModel extends AbstractModel {
    private boolean hosting = false;
    private List<CorePlayer> players = new ArrayList<CorePlayer>();

    public boolean isHosting() {
        return hosting;
    }

    public void setHosting(boolean hosting) {
        this.hosting = hosting;
        fireChange("hosting", hosting);

        //Gdx.app.debug("WaitingModel", "Hosting set to " + hosting);
    }

    public boolean addPlayer(CorePlayer player) {
        if (players.add(player)) {
            //Gdx.app.debug("WaitingModel", "Added player #" + player.getID());

            fireChange("player_joined", player);

            return true;
        }

        return false;
    }

    public boolean removePlayer(CorePlayer player) {
        for (CorePlayer removingPlayer : players) {
            if (removingPlayer.getID().equals(player.getID())) {
                players.remove(removingPlayer);

                //Gdx.app.debug("WaitingModel", "Removed player #" + player.getID());

                fireChange("player_left", player);
                return true;
            }
        }

       return false;
    }

    public List<CorePlayer> getPlayers() {
        return players;
    }

    public void resetPlayers() {
        players.clear();

        fireChange("reset_Players", null);
    }

    @Override
    public void reset() {
        super.reset();
        System.out.println("**************************************_____");
        setHosting(false);
        resetPlayers();
    }
}
