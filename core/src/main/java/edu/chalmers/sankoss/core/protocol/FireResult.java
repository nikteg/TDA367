package edu.chalmers.sankoss.core.protocol;

/**
 *
 * @author Fredrik Thune
 *
 */
public class FireResult extends Fire {
    private boolean isHit;

    public FireResult() {

    }
    public FireResult(Fire fire) {
        setFire(fire);
    }
    public FireResult(Fire fire, boolean isHit) {
        setFire(fire);
        this.isHit = isHit;
    }

    public void setFire(Fire fire) {
        setGameID(fire.getGameID());
        setTarget(fire.getTarget());
        setCoordinate(fire.getCoordinate());
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }
}
