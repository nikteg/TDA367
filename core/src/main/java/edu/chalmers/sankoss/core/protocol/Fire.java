package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Fredrik Thune
 * 
 */
public class Fire {
    private Long gameID;
    private Player target;
    private Coordinate coordinate;

    public Fire() {
    }

    public Fire(Long gameID, Player target, Coordinate coordinate) {
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

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
