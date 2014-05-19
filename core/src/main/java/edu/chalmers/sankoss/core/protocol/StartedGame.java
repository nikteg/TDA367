package edu.chalmers.sankoss.core.protocol;

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
