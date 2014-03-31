package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.Player;

import java.util.List;

/**
 *
 * @author Fredrik Thune
 *
 */
public class StartedGame {
    private Long gameID;
    private List<Player> players;
    public StartedGame() {
    }

    public StartedGame(Long gameID, List<Player> players) {
        this.gameID = gameID;
        this.players = players;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
