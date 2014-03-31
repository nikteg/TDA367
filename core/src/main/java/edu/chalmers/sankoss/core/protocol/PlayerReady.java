package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Niklas Tegnander
 * 
 */
public class PlayerReady {
    private Long gameID;
    private List<Ship> fleet = new ArrayList<Ship>();
	
	public PlayerReady() {
		
	}

    public PlayerReady(Long gameID, List<Ship> fleet) {
        this.gameID = gameID;
        this.fleet = fleet;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long id) {
        this.gameID = id;
    }

    public List<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(List<Ship> fleet) {
        this.fleet = fleet;
    }
}
