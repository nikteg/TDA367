package edu.chalmers.sankoss.core.protocol;

public class Player {
    private int id;
    private String name;
    private boolean[][] grid = new boolean[10][10];
    private boolean ready;
    
    public Player() {
        
    }
    
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void setGrid(boolean[][] grid) {
        this.grid = grid;
    }

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Player) {
			return (getId() == ((Player) o).getId());
		} else {
			return false;
		}
		
			
	}
	public boolean isHit(int x, int y) {
		return grid[x][y];
	}
}
