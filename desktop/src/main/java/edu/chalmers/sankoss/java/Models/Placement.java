package edu.chalmers.sankoss.java.Models;

import edu.chalmers.sankoss.core.protocol.Fleet;
import edu.chalmers.sankoss.core.protocol.Ship;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Daniel Eineving
 * @date 2014-03-29 
 */
public class Placement extends ScreenModel{
	
	private Ship[] ship;
	
	/**
	 * The fleet of all the placed ships
	 * @return the fleet of placed ships
	 */
	public Fleet getFleet(){
		return new Fleet(ship);
	}
	
	/**
	 * Creates five ships with the sizes 5, 4, 3, 3 and 2. 
	 */
	//TODO Move, rename or keep?
	public void setBoats(){
		ship = new Ship[5];
		
		//Standard boats sizes in battleship
		ship[0]= new Ship(5);
		ship[1]= new Ship(4);
		ship[2]= new Ship(3);
		ship[3]= new Ship(3);
		ship[4]= new Ship(2);
	}
	
	
	
}
