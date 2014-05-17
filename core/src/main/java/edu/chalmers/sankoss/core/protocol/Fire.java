package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Coordinate;

/**
 * 
 * @author Fredrik Thune
 * 
 */
public class Fire {
    private Long gameID;
    private CorePlayer target;
    private Coordinate coordinate;

    public Fire() {
    }

    public Fire(Long gameID, CorePlayer target, Coordinate coordinate) {
        this.gameID = gameID;
        this.target = target;
        this.coordinate = coordinate;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public CorePlayer getTarget() {
        return target;
    }

    public void setTarget(CorePlayer target) {
        this.target = target;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
