package edu.chalmers.sankoss.java.placement;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Fleet;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.mvc.AbstractModel;

/**
 * @author Daniel Eineving
 */
public class PlacementModel extends AbstractModel {
	private boolean opponentReady;
	private Fleet fleet = new Fleet();
	private CorePlayer.Nationality nationality = CorePlayer.Nationality.USA;
	private boolean userReady = false;

	public void setOpponentReady(boolean ready) {
		this.opponentReady = ready;

        setChanged();
        notifyObservers("OpponentReady");
	}

	public boolean isOpponentReady() {
		return opponentReady;
	}

	public void addShip(Ship ship) {
		fleet.add(ship);
	}

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

	public Fleet getFleet() {
		return fleet;
	}

	public CorePlayer.Nationality getNationality() {
		return nationality;
	}

	public void setNationality(CorePlayer.Nationality nationality) {
		this.nationality = nationality;
		setChanged();
		notifyObservers("NationalityChanged");
	}
	public boolean isUserReady(){
		return userReady;
	}

	public void setUserReady(boolean ready){
		this.userReady = ready;

        updateNationality();

        setChanged();
        notifyObservers("playerReady");
	}

    /**
     * Updates the client's player instance with correct nationality based on model.
     */
    public void updateNationality() {
        SankossGame.getInstance().getClient().getPlayer().setNationality(nationality);
        SankossGame.getInstance().getClient().playerChangeNat(nationality);
    }

    public boolean getUserReady() {
        return userReady;
    }
}
