package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.Player;

/**
 * Created by nikteg on 30/04/14.
 */
public class PlayerChangedName {
    private Player player;
    private String name;

    public PlayerChangedName() {

    }

    public PlayerChangedName(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return player;
    }
}
