package edu.chalmers.sankoss.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune, Mikael Malmqvist
 * 
 */
public class Player {
    private Long playerID;
    private String name;
    private List<Ship> fleet = new ArrayList<Ship>();
    private List<Coordinate> usedCoordinates = new ArrayList<Coordinate>();
    private boolean ready;
    private BasePlayer.Nationality nationality = BasePlayer.Nationality.USA;

    public Player() {
        this.nationality = BasePlayer.Nationality.USA;
    }

    public Player(Long id) {
        this.playerID = id;
        this.nationality = BasePlayer.Nationality.USA;

        this.name = "Player #" + playerID;
    }
    
    public Player(Long id, String name) {
        this(id);
        this.name = name;
        this.nationality = BasePlayer.Nationality.USA;
    }

    public BasePlayer getBasePlayer() {
        return new BasePlayer(playerID, name, nationality);
    }

    public void setNationality(BasePlayer.Nationality nationality) {
        this.nationality = nationality;
    }

    public BasePlayer.Nationality getNationality() {
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
}
