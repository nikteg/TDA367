package edu.chalmers.sankoss.java.models;

import java.awt.List;

import edu.chalmers.sankoss.core.Fleet;
import edu.chalmers.sankoss.core.Ship;

/**
 * @author Daniel Eineving
 */
public class PlacementModel extends AbstractModel {
	private boolean opponentReady;
	private Fleet fleet = new Fleet();
	
	public void setOpponentReady(boolean ready){
		this.opponentReady = ready;
	}
	
	public boolean isOpponentReady(){
		return opponentReady;
	}
	
	public void addShip(Ship ship){
		fleet.add(ship);
	}
	
	public List getShips(){
		return (List) fleet.getShips();
	}
}
