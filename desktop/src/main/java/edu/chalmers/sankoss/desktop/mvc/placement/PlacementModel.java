package edu.chalmers.sankoss.desktop.mvc.placement;

import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Fleet;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.mvc.AbstractModel;

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
        fireChange("OpponentReady", ready);
	}

	public boolean isOpponentReady() {
		return opponentReady;
	}

	public void addShip(Ship ship) {
		fleet.add(ship);

        setChanged();
        notifyObservers("ship");
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
        fireChange("NationalityChanged", nationality);
	}
	public boolean isUserReady(){
		return userReady;
	}

	public void setUserReady(boolean ready){
		this.userReady = ready;

        updateNationality();
        fireChange("playerReady", ready);
	}

    /**
     * Updates the client's player instance with correct nationality based on model.
     */
	
	//TODO Remove, this should not be sent until the player is ready.
    public void updateNationality() {
    	SankossClient.getInstance().getPlayer().setNationality(nationality);
    	SankossClient.getInstance().playerChangeNat(nationality);
    }

    public boolean getUserReady() {
        return userReady;
    }
}
