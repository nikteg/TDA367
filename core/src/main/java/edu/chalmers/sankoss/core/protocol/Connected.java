package edu.chalmers.sankoss.core.protocol;

/**
 * @author Niklas Tegnander
 */
public class Connected {
    private Long playerID;

    public Connected() {
    }

    public Connected(Long playerID) {
        this.playerID = playerID;
    }

    public Long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Long playerID) {
        this.playerID = playerID;
    }
}

