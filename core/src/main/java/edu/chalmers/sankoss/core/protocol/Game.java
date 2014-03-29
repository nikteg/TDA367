package edu.chalmers.sankoss.core.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 * 
 */
public class Game {
    private Long id;
    private List<Player> players;
    private Player attacker;
    private List<Coordinate> usedCoordinates = new ArrayList<Coordinate>();
    
    public Game() {

    }
    
    public Game(Long id, List<Player> players) {
        this.id = id;
        this.players = players;
    }
    
    public Long getID() {
        return id;
    }
    public void setID(Long id) {
        this.id = id;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

	public Player getAttacker() {
		return attacker;
	}

	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}
	
	public void changeAttacker() {
        setAttacker((players.indexOf(attacker) >= players.size() - 1) ? players.get(0) : players.get(players.indexOf(attacker) + 1));
	}

    public List<Coordinate> getUsedCoordinates() {
        return usedCoordinates;
    }

    public void setUsedCoordinates(List<Coordinate> usedCoordinates) {
        this.usedCoordinates = usedCoordinates;
    }

    public boolean fire(Player target, Coordinate coordinate) {

        // TODO Throw exception
        if (usedCoordinates.contains(coordinate)) {

        }

        usedCoordinates.add(coordinate);

        // TODO To fire
        System.out.println(String.format("Fire: #%d %d,%d", target.getID(), coordinate.getX(), coordinate.getY()));

        return true;
    }
}
