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
 * @modified Fredrik Thune, Mikael Malmqvist, Daniel Eineving
 * 
 */
public class Player implements KryoSerializable {
    private Long playerID;
    private String name;
    private List<Ship> fleet = new ArrayList<Ship>();
    private List<Coordinate> usedCoordinates = new ArrayList<Coordinate>();
    private boolean ready;
    private Nationality nationality = Nationality.USA;
    
    public Player() {
        this.nationality = Nationality.USA;
    }

    public Player(Long id) {
        this.playerID = id;
        this.nationality = Nationality.USA;

        this.name = "Player #" + playerID;
    }
    
    public Player(Long id, String name) {
        this(id);
        this.name = name;
        this.nationality = Nationality.USA;
    }


    public enum Nationality {
        USA("USA", "assets/textures/USA.png"),
        GERMANY("GER", "assets/textures/germany.png"),
        JAPAN("JAP", "assets/textures/japan.png"),
        ENGLAND("ENG", "assets/textures/england.png");

        private String landName;
        private java.awt.Color color;
        private String path;

        Nationality(String name, java.awt.Color color) {
            this.landName = name;
            this.color = color;
        }

        Nationality(String name, String path) {
            this.landName = name;
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public Nationality getNext(){
            return values()[(ordinal() + 1) % values().length];
        }

        public Nationality getLast(){
            return values()[(ordinal() + 3) % values().length];
        }

        public java.awt.Color getColor() {
            return color;
        }

        public void setColor(java.awt.Color color) {
            this.color = color;
        }

        public String getLandName() {
            return landName;
        }

        public void setLandName(String landName) {
            this.landName = landName;
        }
    }


    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Nationality getNationality() {
        return this.nationality;
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
    public String toString() {
        return this.name;
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
