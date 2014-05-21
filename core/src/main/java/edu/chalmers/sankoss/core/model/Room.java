package edu.chalmers.sankoss.core.model;

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
    private List<CorePlayer> players = new ArrayList<CorePlayer>();
    
    public Room() {
        
    }
    
    public Room(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public List<CorePlayer> getPlayers() {
        return players;
    }

    public void addPlayer(CorePlayer player) {
        players.add(player);
    }

    public boolean hasPlayerWithID(Long id) {
        for (CorePlayer corePlayer : players) {
            if (corePlayer.getID().equals(id))
                return true;
        }

        return false;
    }

    public boolean isPlayerWithIDHost(Long id) {
        return players.get(0).getID().equals(id);
    }

    public void removePlayerWithID(Long id) {
        for (CorePlayer corePlayer : players) {
            if (corePlayer.getID().equals(id)) {
                players.remove(corePlayer);

                return;
            }

        }
    }

    public String toString() {
        return this.getName();
    }

    public boolean equals(Room room) {
        return(name.equals(room.getName()) && id == room.getID()
                && password.equals(room.getPassword()));
    }
}
