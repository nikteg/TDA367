package edu.chalmers.sankoss.core.protocol;

public class Room {
    private int id;
    private String name;
    private String password;
    private Player player;
    
    public Room() {
        
    }
    
    public Room(int id, String name, String password, Player player) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.player = player;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
