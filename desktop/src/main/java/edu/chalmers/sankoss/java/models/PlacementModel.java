package edu.chalmers.sankoss.java.models;

/**
 * @author Daniel Eineving
 */
public class PlacementModel extends AbstractModel {
	private boolean opponentReady;

	
	public void setOppenentReady(boolean ready){
		this.opponentReady = ready;
	}
	
	public boolean isOpponentReady(){
		return opponentReady;
	}
	
	
}
