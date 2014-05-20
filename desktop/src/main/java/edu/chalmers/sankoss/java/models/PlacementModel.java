package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Fleet;
import edu.chalmers.sankoss.core.Ship;

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

        setChanged();
        notifyObservers("playerReady");
	}

    public boolean getUserReady() {
        return userReady;
    }
}
