package edu.chalmers.sankoss.core.protocol;

import java.util.LinkedList;

/**
 * Class representing a ship
 * @author Daniel Eineving
 * @modified Niklas Tegnander
 * @date 2014-03-30
 */

public class Ship {
	private int size;
	private Coordinate start, end;
	private int hits=0;
	
	/**
	 * Creates a ship with a given size
	 * @param size Length of the ship
	 */
	public Ship(int size){
		this.size=size;
	}

	/**
	 * Creates a Ship with a given position
	 * @param size - Grid length of the Ship
	 * @param start - Start Coordinate
	 * @param end - End Coordinate
	 */
	//TODO Do we need this constructor?
	public Ship(int size, Coordinate start, Coordinate end){
		this(size);
		setCoordinates(start, end);
	}
	
	/**
	 * Sets the coordinates of the ship
	 * @param start Start coordinate
	 * @param end End Coordinate
	 */
	public void setCoordinates(Coordinate start, Coordinate end){
		if(start.getX() != end.getX() && start.getY() != end.getY()){
			
			//TODO Write our own exceptions?
			throw new IllegalArgumentException("No valid coordinates for ship start and end");
		}
		//TODO Should we make copies of the coordinates instead?
		this.start=start;
		this.end=end;
	}
	
	/**
	 * Start coordinate of the ship
	 * @return the start coordinate
	 */
	public Coordinate getStartCoordinate(){
		return start;
	}
	
	/**
	 * End coordinate of the ship
	 * @return the end coordinate
	 */
	public Coordinate getEndCoordinate(){
		return end;
	}
	
	/**
	 * Gets the size of the ship
	 * @return Size of the ship
	 */
	public int getSize(){
		return size;
	}
	
	
	//TODO do we need this?
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + hits;
		result = prime * result + size;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		
		//TODO does now return false if no rotated the same direction
		Ship other = (Ship) obj;

		if(start.equals(other.start) && end.equals(other.end) && hits == other.hits && size == other.size){
			return true;
		}
		return false;
	}
}
