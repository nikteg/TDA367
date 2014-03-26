package edu.chalmers.sankoss.core.protocol;
/**
 * 
 * @author Niklas Tegnander
 * 
 */
public class PlayerReady {
	private boolean[][] grid = new boolean[10][10];
	
	public PlayerReady() {
		
	}
	public PlayerReady(boolean[][] grid) {
		this.grid = grid;
	}

	public boolean[][] getGrid() {
		return grid;
	}

	public void setGrid(boolean[][] grid) {
		this.grid = grid;
	}
}
