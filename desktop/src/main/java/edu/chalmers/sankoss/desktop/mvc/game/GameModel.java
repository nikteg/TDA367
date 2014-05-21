package edu.chalmers.sankoss.desktop.mvc.game;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.desktop.mvc.AbstractModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for GameScreen.
 * Handles all data for the GameScreen and listens
 * to the renderer.
 *
 */
public class GameModel extends AbstractModel {
    private boolean shootingAllowed = false;
    private State state = State.PLAYING;
    private List<Coordinate> shots = new ArrayList<Coordinate>();
    private List<Coordinate> flags = new ArrayList<Coordinate>();
    private CorePlayer opponent;

    public GameModel() {

    }

    public enum State {
        PLAYING, WON, LOST;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        fireChange("state", state);
    }

    public void setOpponent(CorePlayer opponent) {
        this.opponent = opponent;
        fireChange("opponent", opponent);
    }

    public CorePlayer getOpponent() {
        return this.opponent;
    }

    public void setShootingAllowed(boolean shootingAllowed) {
        this.shootingAllowed = shootingAllowed;
        fireChange("shooting_allowed", shootingAllowed);
    }

    public boolean isShootingAllowed() {
        return shootingAllowed;
    }

    /**
     * Method for adding shots to model's shotList.
     * @param coordinate position of shot.
     */
    public void addShot(Coordinate coordinate) {
        shots.add(coordinate);
        fireChange("shot", coordinate);
    }

    public List<Coordinate> getShots() {
        return shots;
    }

    /**
     * Toggle flag at given coordinate
     * @param coordinate position of flag.
     */
    public void toggleFlag(Coordinate coordinate) {
        if (flags.contains(coordinate)) {
            flags.remove(coordinate);
        } else {
            flags.add(coordinate);
        }
        fireChange("flag", coordinate);
    }

    public List<Coordinate> getFlags() {
        return flags;
    }
}
