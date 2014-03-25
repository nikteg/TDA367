package edu.chalmers.sankoss.java.gamelogic;

import java.util.LinkedList;

/**
 * Class representing the gridboard, with boats, hits, and misses.
 * 
 * @author Daniel Eineving
 *
 */
public class GridBoard {
	
	CoordinateStatus[][] grid = new CoordinateStatus[10][10];
	
	/**
	 * Creates an empty GridBoard
	 */
	public GridBoard(){
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				grid[i][j] = CoordinateStatus.EMPTY;
			}
		}
	}
	/**
	 * Creates a grid with boats in them
	 * @param boats
	 */
	public GridBoard(LinkedList<Boat> boats){
		//TODO Implement
	}
}
