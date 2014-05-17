package edu.chalmers.sankoss.java.models;

import com.badlogic.gdx.Gdx;
import edu.chalmers.sankoss.core.BasePlayer;

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
    private List<BasePlayer> players = new ArrayList<BasePlayer>();

    public boolean isHosting() {
        return hosting;
    }

    public void setHosting(boolean hosting) {
        this.hosting = hosting;

        setChanged();
        notifyObservers("hosting");

        Gdx.app.debug("WaitingModel", "Hosting set to " + hosting);
    }

    public void addPlayer(BasePlayer player) {
        players.add(player);

        setChanged();
        notifyObservers("player");
    }

    public void removePlayer(BasePlayer player) {
        players.remove(player);

        setChanged();
        notifyObservers("player");
    }

    public List<BasePlayer> getPlayers() {
        return players;
    }
}
