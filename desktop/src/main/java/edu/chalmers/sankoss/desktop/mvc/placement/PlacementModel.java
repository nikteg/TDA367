package edu.chalmers.sankoss.desktop.mvc.placement;

import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Fleet;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.mvc.AbstractModel;

import java.awt.geom.Line2D;

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

	public boolean addShip(Ship ship) {
        fireChange("ship_added", ship);
        if (!validate(ship))
            return false;
        return fleet.add(ship);
    }

    private boolean validate(Ship ship) {

        if ((ship.getFront().getX() < 1 || ship.getFront().getX() > 10)
                || (ship.getFront().getY() < 1 || ship.getFront().getY() > 10))
            return false;


        if ((ship.getRear().getX() < 1 || ship.getRear().getX() > 10)
                || (ship.getRear().getY() < 1 || ship.getRear().getY() > 10))
            return false;

        Line2D shipLine = new Line2D.Double(ship.getFront().getX(), ship.getFront().getY(),
                ship.getRear().getX(), ship.getRear().getY());

        for (Ship otherShip : getFleet().getShips()) {
            Line2D otherShipLine = new Line2D.Double(otherShip.getFront().getX(), otherShip.getFront().getY(),
                    otherShip.getRear().getX(), otherShip.getRear().getY());
            if(otherShipLine.intersectsLine(shipLine))
                return false;

        }
        return true;
    }

    public void removeShip(Ship ship) {
        fireChange("ship_removed", ship);
        fleet.remove(ship);
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
