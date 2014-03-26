package edu.chalmers.sankoss.java.gamelogic;

import java.util.LinkedList;

/**
 * Class representing a ship
 * @author Daniel Eineving
 * @modified Niklas Tegnander
 */

public class Ship {
	private int size;
	private Coordinate start, end;
	private int hits=0;
	//TODO Shall the ship keep track WHERE it is hit?
	
	/**
	 * Creates a Ship
	 * @param size - Grid length of the Ship
	 * @param start - Start Coordinate
	 * @param end - End Coordinate
	 */
	public Ship(int size, Coordinate start, Coordinate end){
		this.size=size;
		
		// TODO No real need to swap start with end? Should be able to rotate ship 180 degrees?
		//Makes sure start is either above or to the left of the end coordinate
		if(start.getX()+start.getY() > end.getX() + end.getY()){
			this.start=end;
			this.end=start;
		}
		else{
			this.start=start;
			this.end=end;
		}

	}
	
	/**
	 * Gets all the boats coordinates as a LinkedList
	 * @return The coordinates of the boat.
	 */
	public LinkedList<Coordinate> getCoordinates(){
		LinkedList<Coordinate> coordinates = new LinkedList<Coordinate>();
		
		coordinates.add(start);
		if(start.getX()==end.getX()){
			for(int i=1;i<(size-1);i++){
				coordinates.add(new Coordinate(start.getX(), start.getY()+i));
			}
		}
		else{
			for(int i=1;i<(size-1);i++){
				coordinates.add(new Coordinate(start.getX()+i, start.getY()));
			}
		}
		coordinates.add(end);
		
		return coordinates;
	}
	
	public boolean isShip(Coordinate target){
		//TODO split into different parts?
		
		//TODO How do we count our coordinates?
		return (start.getX()==end.getX() && target.getX()==start.getX() && start.getY() <= target.getY() && target.getY() <=end.getY()) 
				|| (start.getY()==end.getY() && target.getY()==start.getY() && start.getX() <= target.getX() && target.getX() <=end.getX());
	}
	
	public void shipHit(){
		//TODO Shall we save a coordinate?
		hits++;
	}
}
