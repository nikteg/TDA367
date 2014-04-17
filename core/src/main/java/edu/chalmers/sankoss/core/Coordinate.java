package edu.chalmers.sankoss.core;

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
	public Coordinate(int x, int y){
		//TODO Throw exception?
		if(x<0 || 10<x || y<0 || 10<y){
			throw new IllegalArgumentException();
		}
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
}
