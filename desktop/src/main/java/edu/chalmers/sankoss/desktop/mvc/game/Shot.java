package edu.chalmers.sankoss.desktop.mvc.game;

import edu.chalmers.sankoss.core.core.Coordinate;

/**
 * Created by nikteg on 2014-05-21.
 */
public class Shot {
    private Coordinate coordinate;
    private State state;

    public Shot(Coordinate coordinate, State state) {
        this.coordinate = coordinate;
        this.state = state;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public State getState() {
        return state;
    }

    public enum State {
        HIT, MISS
    }
}
