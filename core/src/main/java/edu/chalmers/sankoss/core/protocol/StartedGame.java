package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.BasePlayer;

import java.util.List;

/**
 *
 * @author Fredrik Thune
 *
 */
public class StartedGame {
    private Long gameID;
    public StartedGame() {
    }

    public StartedGame(Long gameID) {
        this.gameID = gameID;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
}
