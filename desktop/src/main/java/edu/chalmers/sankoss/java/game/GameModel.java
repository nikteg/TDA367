package edu.chalmers.sankoss.java.game;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.java.mvc.AbstractModel;

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

        setChanged();
        notifyObservers("state");
    }

    public void setOpponent(CorePlayer opponent) {
        this.opponent = opponent;

        setChanged();
        notifyObservers("opponent");
    }

    public CorePlayer getOpponent() {
        return this.opponent;
    }

    public void setShootingAllowed(boolean shootingAllowed) {
        this.shootingAllowed = shootingAllowed;

        setChanged();
        notifyObservers("shooting_allowed");
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

        setChanged();
        notifyObservers("shot");
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

        setChanged();
        notifyObservers("flag");
    }

    public List<Coordinate> getFlags() {
        return flags;
    }
}
