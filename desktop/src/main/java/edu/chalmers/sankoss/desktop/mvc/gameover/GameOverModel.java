package edu.chalmers.sankoss.desktop.mvc.gameover;

import edu.chalmers.sankoss.desktop.mvc.AbstractModel;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class GameOverModel extends AbstractModel {
    private State state = State.WON;

    public enum State {
        WON, LOST;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        fireChange("state", state);
    }

    public GameOverModel() {
        super();
    }

    @Override
    public void reset() {
        fireChange("reset");
    }
}
