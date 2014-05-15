package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.BasePlayer;

/**
 * 
 * @author Fredrik Thune
 * 
 */
public class JoinedRoom {
    private BasePlayer player;

    public JoinedRoom() {

    }

    public JoinedRoom(BasePlayer player) {
        this.player = player;
    }

    public BasePlayer getPlayer() {
        return player;
    }

    public void setPlayer(BasePlayer player) {
        this.player = player;
    }
}
