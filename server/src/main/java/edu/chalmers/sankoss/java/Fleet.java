package edu.chalmers.sankoss.java;

import java.util.Arrays;
import java.util.LinkedList;
import edu.chalmers.sankoss.core.Ship;

/**
 * Class representing a fleet of ships
 * @author Daniel Eineving
 * @date 2014-03-29
 */

public class Fleet{

	private	Ship[] ship;

	/**
	 * Creates a new fleet
	 * @param ship
	 */
	public Fleet(Ship[] ship){
		if(ship==null || ship.length==0){
			throw new IllegalArgumentException("There are no ships");
		}
		this.ship=ship;
	}

	/**
	 * Get all the ships in the fleet
	 * @return All the ships in the fleet
	 */
	public Ship[] getShips(){
		//TODO Not safe
		return ship;
	}
	
	/**
	 * Checks if the fleet i destroyed
	 * @return true if the fleet is destroyed
	 */
	public boolean isDestroyed(){
		for(int i=0; i<ship.length;i++){
			if(!ship[i].isDestroyed()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * A list containing all the ships that have not been destroyed
	 * @return remaining ships
	 */
	public LinkedList<Ship> getRemainingShips(){
		LinkedList<Ship> temp= new LinkedList<Ship>();
		
		for(int i=0;i<ship.length;i++){
			if(!ship[i].isDestroyed()){
				temp.add(ship[i]);
			}
		}
		return temp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(ship);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fleet other = (Fleet) obj;
		if (!Arrays.equals(ship, other.ship))
			return false;
		return true;
	}

}
