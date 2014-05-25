package edu.chalmers.sankoss.core.protocol;

/**
 * Sent when a player leaves a game
 * @author Fredrik Thune
 */
public class LeaveGame {
    private Long gameID;

    public LeaveGame() {
    }

    public LeaveGame(Long gameID) {
        this.gameID = gameID;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
}
