package edu.chalmers.sankoss.java.gamelogic;

/**
 * Represents a coordinate in the game grid
 * @author Daniel Eineving
 *
 */
public class Coordinate {
	
	private int x, y;
	
	/**
	 * 
	 * @param x 1-10
	 * @param y 1-10
	 */
	public Coordinate(int x, int y){
		//TODO Throw exception?
		if(x<1 || 10<x || y<1 || 10<y){
			throw new IllegalArgumentException("Illegal coordinates");
		}
		this.x=x;
		this.y=y;
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
}
