package edu.chalmers.sankoss.java.Models;

import edu.chalmers.sankoss.core.protocol.Fleet;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Daniel Eineving
 * @date 2014-03-30 
 */
public class Placement extends ScreenModel{
	
	private Fleet fleet;
	
	public Placement(Fleet fleet){
		this.fleet=fleet;
	}
	
	/**
	 * The fleet of all the placed ships
	 * @return the fleet of placed ships
	 */
	public Fleet getFleet(){
		return fleet;
	}
	
	
	
	
	
	
	
	
	
	
	
}
