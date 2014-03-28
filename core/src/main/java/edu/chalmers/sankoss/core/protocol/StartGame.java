package edu.chalmers.sankoss.core.protocol;

/**
 * Created by nikteg on 28/03/14.
 */
public class StartGame {
    Long id;

    public StartGame() {
    }

    public StartGame(Long id) {
        this.id = id;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }
}
