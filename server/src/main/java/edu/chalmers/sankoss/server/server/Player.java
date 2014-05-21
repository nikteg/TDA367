package edu.chalmers.sankoss.server.server;

import edu.chalmers.sankoss.core.model.Coordinate;
import edu.chalmers.sankoss.core.model.CorePlayer;
import edu.chalmers.sankoss.core.model.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune, Mikael Malmqvist
 * 
 */
public class Player extends CorePlayer {
    private List<Ship> fleet = new ArrayList<Ship>();
    private List<Coordinate> usedCoordinates = new ArrayList<Coordinate>();
    private boolean ready;

    public Player() {
    }

    public Player(Long id) {
        super(id);
    }
    
    public Player(Long id, String name) {
        super(id, name);
    }

    public CorePlayer getCorePlayer() {
        return new CorePlayer(getID(), getName(), getNationality());
    }

    public List<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(List<Ship> fleet) {
        this.fleet = fleet;
    }

    public boolean fleetIsDestoyed() {
        for (Ship ship : fleet) {
            if (!ship.isDestroyed())
                return false;
        }
        return true;
    }

    public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

    public List<Coordinate> getUsedCoordinates() {
        return usedCoordinates;
    }

    public void addUsedCoordiante(Coordinate coordinate) {
        this.usedCoordinates.add(coordinate);
    }

    public void setUsedCoordinates(List<Coordinate> usedCoordinates) {
        this.usedCoordinates = usedCoordinates;
    }


    @Override
    public String toString() {
        return getName();
    }

}
