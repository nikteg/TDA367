package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Fredrik Thune
 * 
 */
public class Miss {
    private Coordinate coordinate;
    private boolean you;

    public Miss() {

    }

    public Miss(Coordinate coordinate, boolean you) {
        this.coordinate = coordinate;
        this.you = you;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isYou() {
        return you;
    }
    public void setYou(boolean you) {
        this.you = you;
    }
}