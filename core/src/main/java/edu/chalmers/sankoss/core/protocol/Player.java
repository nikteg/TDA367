package edu.chalmers.sankoss.core.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 * 
 */
public class Player {
    private int id;
    private String name;
    private List<Ship> fleet = new ArrayList<Ship>();
    private boolean ready;
    
    public Player() {
        
    }

    public Player(int id) {
        this.id = id;
    }
    
    public Player(int id, String name) {
        this(id);
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(List<Ship> fleet) {
        this.fleet = fleet;
    }

    public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Player) {
			return (getID() == ((Player) o).getID());
		} else {
			return false;
		}
	}
}
