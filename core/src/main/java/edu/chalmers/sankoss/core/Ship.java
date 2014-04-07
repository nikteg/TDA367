package edu.chalmers.sankoss.core;

import java.util.LinkedList;

/**
 * Class representing a ship
 * @author Daniel Eineving
 * @modified Niklas Tegnander
 * @modified Fredrik Thune
 * @date 2014-03-30
 */

public class Ship {
	private int size;
	private Coordinate start, end;
	private int hits=0;

	/**
	 * Enum that describes in what way a ship is directed.
	 * @author Daniel Eineving
	 * @date 2014-04-07
	 *
	 */
	private enum ROTATION {
		NORTH,
		SOUTH,
		EAST,
		WEST
	}


	/**
	 * Creates a ship without length and coordinates
	 */
	public Ship() {
		//KryoNet needs an empty constructor
	}

	/**
	 * Creates a Ship with a given position
	 * @param start - Start Coordinate
	 * @param end - End Coordinate
	 */

	public Ship(Coordinate start, Coordinate end){
		size = distance(start, end) + 1;
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
	public Coordinate getStart(){
		return start;
	}

	/**
	 * End coordinate of the ship
	 * @return the end coordinate
	 */
	public Coordinate getEnd(){
		return end;
	}

	/**
	 * Gets the size of the ship
	 * @return Size of the ship
	 */
	public int getSize(){
		return size;
	}

	@Override
	public String toString() {
		return String.format("%d:(%d, %d)(%d, %d)", size, start.getX(), start.getY(), end.getX(), end.getY());
	}

	/**
	 * Gets all the boats coordinates as a LinkedList
	 * @return The coordinates of the boat.
	 */
	//TODO Do we need this?
	public LinkedList<Coordinate> getCoordinates(){
		LinkedList<Coordinate> coordinates = new LinkedList<Coordinate>();
		if(size > 0){
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
		}
		return coordinates;
	}

	/**
	 * Checks if a target is a part of the ship
	 * @param target
	 * @return True if the target coordinate is a part of the ship
	 */
	public boolean isShip(Coordinate target){
		switch (getRotation()) {
		case EAST:
			return (target.getY() == start.getY() && target.getX() >= start.getX() && target.getX() <= end.getX());
		case WEST:
			return (target.getY() == start.getY() && target.getX() <= start.getX() && target.getX() >= end.getX());
		case SOUTH:
			return (target.getX() == start.getX() && target.getY() >= start.getY() && target.getY() <= end.getY());
		default:
			return (target.getX() == start.getX() && target.getY() <= start.getY() && target.getY() >= end.getY());
		}
	}

	public ROTATION getRotation() {
		if (start.getX() < end.getX())
			return ROTATION.WEST;

		if (start.getX() > end.getX())
			return ROTATION.EAST;

		if (start.getY() < end.getY())
			return ROTATION.NORTH;

		return ROTATION.SOUTH;

	}

	private int distance(Coordinate start, Coordinate end) {
		return (int)Math.sqrt(Math.pow((double)(start.getX() - end.getX()), 2) +
				Math.pow((double)(start.getY() - end.getY()), 2));
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
