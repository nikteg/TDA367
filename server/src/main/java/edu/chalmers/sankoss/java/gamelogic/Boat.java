package edu.chalmers.sankoss.java.gamelogic;

import java.util.LinkedList;

public class Boat {
	private int size;
	private LinkedList<Coordinate> coordinates;
	
	public Boat(int size){
		this.size=size;
	}
	
	public void setCoordinates(LinkedList<Coordinate> coordinates){
		if(coordinates.size() != size){
			throw new IllegalArgumentException("Coordinates does not mach boatsize");
		}
		this.coordinates=(LinkedList<Coordinate>) coordinates.clone();
	}
	
	public LinkedList<Coordinate> getCoordinates(){
		return (LinkedList<Coordinate>)coordinates.clone();
	}
}
