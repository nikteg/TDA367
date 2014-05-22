package edu.chalmers.sankoss.core.core;

import java.util.LinkedList;

import edu.chalmers.sankoss.core.exceptions.IllegalShipCoordinatesException;

/**
 * Class representing a ship
 * @author Daniel Eineving
 * @modified Niklas Tegnander
 * @modified Fredrik Thune
 * @date 2014-03-30
 */

public class Ship {
	private int size;
	private Coordinate front, rear;
	private int hits=0;
    private Rotation rotation;

    /**
	 * Enum that describes in what way a ship is directed.
	 * @author Daniel Eineving
	 * @date 2014-04-07
	 *
	 */
	public enum Rotation {
		NORTH(90f),
		SOUTH(270f),
		EAST(0f),
		WEST(180f);
        private float rotation;

        Rotation(float v) {
            rotation = v;
        }

        public float asFloat() {
            return rotation;
        }

        public void setRotation(float rotation) {
            this.rotation = rotation;
        }
    }

	/**
	 * Creates a ship without length and coordinates
	 */
	public Ship() {
		//KroNet needs an empty constructor
	}

	/**
	 * Creates a Ship with a given position
	 * @param start - Start Coordinate
	 * @param end - End Coordinate
	 * @throws IllegalShipCoordinatesException 
	 */

	public Ship(Coordinate start, Coordinate end) throws IllegalShipCoordinatesException{
		size = distance(start, end) + 1;
		setCoordinates(end, start);
	}

    public void setLocation(Coordinate coordinate) {
        front.setX(front.getX() + coordinate.getX() - rear.getX());
        front.setY(front.getY() + coordinate.getY() - rear.getY());
        rear = coordinate;
    }

    public void rotateLeft() {
        int frontX = rear.getX() + (front.getY() - rear.getY());
        int frontY = rear.getY() + (rear.getX() - front.getX());
        front = new Coordinate(frontX, frontY);
    }


    /**
	 * Sets the coordinates of the ship
	 * @param start Start coordinate
	 * @param end End Coordinate
	 * @throws IllegalShipCoordinatesException 
	 */
	public void setCoordinates(Coordinate start, Coordinate end) throws IllegalShipCoordinatesException{
		if(start.getX() != end.getX() && start.getY() != end.getY()){
			throw new IllegalShipCoordinatesException();
		}
		//TODO Should we make copies of the coordinates instead?
		this.front=start;
		this.rear=end;
	}

	/**
	 * Start coordinate of the ship
	 * @return the start coordinate
	 */
	public Coordinate getFront(){
		return front;
	}

	/**
	 * End coordinate of the ship
	 * @return the end coordinate
	 */
	public Coordinate getRear(){
		return rear;
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
		return String.format("%d:(%d, %d)(%d, %d)", size, front.getX(), front.getY(), rear.getX(), rear.getY());
	}

	/**
	 * Checks if a target is a part of the ship
	 * @param target
	 * @return True if the target coordinate is a part of the ship
	 */
	public boolean isShip(Coordinate target){

		switch (getRotation()) {
		case WEST:
			return (target.getY() == front.getY() && target.getX() >= front.getX() && target.getX() <= rear.getX());
		case EAST:
			return (target.getY() == front.getY() && target.getX() <= front.getX() && target.getX() >= rear.getX());
		case NORTH:
			return (target.getX() == front.getX() && target.getY() >= front.getY() && target.getY() <= rear.getY());
		default:
			return (target.getX() == front.getX() && target.getY() <= front.getY() && target.getY() >= rear.getY());
		}
	}

	public Rotation getRotation() {
		if (front.getX() < rear.getX())
			return Rotation.WEST;

		if (front.getX() > rear.getX())
			return Rotation.EAST;

		if (front.getY() < rear.getY())
			return Rotation.NORTH;

		return Rotation.SOUTH;

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
		result = prime * result + ((rear == null) ? 0 : rear.hashCode());
		result = prime * result + hits;
		result = prime * result + size;
		result = prime * result + ((front == null) ? 0 : front.hashCode());
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

		if(front.equals(other.front) && rear.equals(other.rear) && hits == other.hits && size == other.size){
			return true;
		}
		return false;
	}
}
