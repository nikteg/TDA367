package edu.chalmers.sankoss.desktop.mvc.game;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Ship;
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
    private List<Ship> ships = new ArrayList<Ship>();
    private List<Shot> shots = new ArrayList<Shot>();
    private List<Shot> opponentShots = new ArrayList<Shot>();
    private List<Coordinate> flags = new ArrayList<Coordinate>();
    private CorePlayer opponent;

    public GameModel() {

    }

    public void addOpponentShot(Shot shot) {
        opponentShots.add(shot);

        setChanged();
        notifyObservers("opponent_shot");
    }

    public List<Shot> getOpponentShots() {
        return opponentShots;
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

    public void addShip(Ship ship) {
        ships.add(ship);

        setChanged();
        notifyObservers("ship");
    }

    /**
     * Method for adding shots to model's shotList.
     * @param shot
     */
    public void addShot(Shot shot) {
        shots.add(shot);

        setChanged();
        notifyObservers("shot");
    }

    public List<Shot> getShots() {
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
