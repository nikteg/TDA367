package edu.chalmers.sankoss.core.protocol;

/**
 *
 * @author Fredrik Thune
 *
 */
public class CreatedGame {
    private Long gameID;

    public CreatedGame() {
    }

    public CreatedGame(Long gameID) {
        this.gameID = gameID;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
}
