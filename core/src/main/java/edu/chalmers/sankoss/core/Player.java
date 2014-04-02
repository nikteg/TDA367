package edu.chalmers.sankoss.core;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 * 
 */
public class Player implements KryoSerializable {
    private Long playerID;
    private String name;
    private List<Ship> fleet = new ArrayList<Ship>();
    private List<Coordinate> usedCoordinates = new ArrayList<Coordinate>();
    private boolean ready;
    
    public Player() {
        
    }

    public Player(Long id) {
        this.playerID = id;
    }
    
    public Player(Long id, String name) {
        this(id);
        this.name = name;
    }

    public Long getID() {
        return playerID;
    }

    public void setID(Long id) {
        this.playerID = id;
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

    public List<Coordinate> getUsedCoordinates() {
        return usedCoordinates;
    }

    public void addUsedCoordiante(Coordinate coordinate) {
        this.usedCoordinates.add(coordinate);
    }

    public void setUsedCoordinates(List<Coordinate> usedCoordinates) {
        this.usedCoordinates = usedCoordinates;
    }

    @Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			return (getID().equals(((Player) o).getID()));
		} else {
			return false;
		}
	}

    @Override
    public int hashCode() {
        return playerID.hashCode();
    }

    @Override
    public void write(Kryo kryo, Output output) {
        output.writeLong(playerID);
        output.writeString(name);
    }

    @Override
    public void read(Kryo kryo, Input input) {
        this.playerID = input.readLong();
        this.name = input.readString();
    }
}
