package edu.chalmers.sankoss.java.models;

import java.awt.List;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Fleet;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.SankossGame;

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
	}

	public boolean isOpponentReady() {
		return opponentReady;
	}

	public void addShip(Ship ship) {
		fleet.add(ship);
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
	}
}
