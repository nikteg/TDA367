package edu.chalmers.sankoss.core.protocol;

public class Player {
    private int id;
    private String name;
    private boolean[][] grid = new boolean[10][10];
    
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
}
