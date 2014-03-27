package edu.chalmers.sankoss.java.gamelogic;

import java.util.LinkedList;

/**
 * Class representing a ship
 * @author Daniel Eineving
 * @modified Niklas Tegnander
 * @date 2014-03-27
 */

public class Ship {
	private int size;
	private Coordinate start, end;
	private int hits=0;

	/**
	 * Creates a Ship
	 * @param size - Grid length of the Ship
	 * @param start - Start Coordinate
	 * @param end - End Coordinate
	 */
	public Ship(int size, Coordinate start, Coordinate end){
		this.size=size;
		this.start=start;
		this.end=end;
	}

	/**
	 * Gets all the boats coordinates as a LinkedList
	 * @return The coordinates of the boat.
	 */
	public LinkedList<Coordinate> getCoordinates(){
		LinkedList<Coordinate> coordinates = new LinkedList<Coordinate>();

		coordinates.add(start);

		if(start.getX()==end.getX()){
			if(start.getY()<end.getX()){
				for(int i=1;i<(size-1);i++){
					coordinates.add(new Coordinate(start.getX(), start.getY()+i));
				}
			}
			else{
				for(int i=1;i<(size-1);i++){
					coordinates.add(new Coordinate(start.getX(), start.getY()-i));
				}
			}
		}
		else{
			if(start.getY()<end.getX()){
				for(int i=1;i<(size-1);i++){
					coordinates.add(new Coordinate(start.getX()+i, start.getY()));
				}
			}
			else{
				for(int i=1;i<(size-1);i++){
					coordinates.add(new Coordinate(start.getX()-i, start.getY()));
				}
			}
		}
		coordinates.add(end);

		return coordinates;
	}

	/**
	 * Checks if a target is a part of the ship
	 * @param target
	 * @return True if the target coordinate is a part of the ship
	 */
	public boolean isShip(Coordinate target){
		LinkedList<Coordinate> temp = getCoordinates();

		for(int i=0;i<size;i++){
			if(target.equals(temp.get(i))){
				return true;
			}
		}
		return false;

		/*
		return (start.getX()==end.getX() && target.getX()==start.getX() && start.getY() <= target.getY() && target.getY() <=end.getY()) 
				|| (start.getY()==end.getY() && target.getY()==start.getY() && start.getX() <= target.getX() && target.getX() <=end.getX());
		 */
	}

	/**
	 * Adds a hit to the ship
	 */
	public void shipHit(){
		hits++;
	}

	/**
	 * Checks if the ship is destroyed
	 * @return True if the boat is destroyed
	 */
	public boolean isDestroyed(){
		return hits >= size;
	}
}
