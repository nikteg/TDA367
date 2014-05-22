package edu.chalmers.sankoss.core.core;

import edu.chalmers.sankoss.core.exceptions.IllegalShipCoordinatesException;

/**
 * Represents a coordinate in the game grid
 * @author Daniel Eineving
 * @date 2014-03-27
 */
public class Coordinate {

	private int x, y;

    public Coordinate() {

    }

	/**
	 * Creates a new coordinate
	 * @param x 1-10
	 * @param y 1-10
	 */
	public Coordinate(int x, int y) {
		this.x=x;
		this.y=y;
	}

	/**
	 * Returns the coordinates x coordinate
	 * @return x coordinate
	 */
	public int getX(){
		return x;
	}

	/**
	 * Returns the coordinates y coordinate
	 * @return y coordinate
	 */
	public int getY(){
		return y;
	}

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

    @Override
    public String toString() {
        return " (" + x + ", " + y + ") ";
    }
}
