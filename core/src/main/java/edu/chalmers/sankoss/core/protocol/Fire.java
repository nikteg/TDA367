package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Coordinate;

/**
 * 
 * @author Fredrik Thune
 * 
 */
public class Fire {
    private Long gameID;
    private BasePlayer target;
    private Coordinate coordinate;

    public Fire() {
    }

    public Fire(Long gameID, BasePlayer target, Coordinate coordinate) {
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

    public BasePlayer getTarget() {
        return target;
    }

    public void setTarget(BasePlayer target) {
        this.target = target;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
