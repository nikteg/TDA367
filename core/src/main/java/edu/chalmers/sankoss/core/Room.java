package edu.chalmers.sankoss.core;

import edu.chalmers.sankoss.core.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 * 
 */
public class Room {
    private Long id;
    private String name;
    private String password;
    private List<Player> players = new ArrayList<Player>();
    
    public Room() {
        
    }
    
    public Room(Long id, String name, String password, Player player) {
        this.id = id;
        this.name = name;
        this.password = password;

        players.add(player);
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
