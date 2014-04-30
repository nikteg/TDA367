package edu.chalmers.sankoss.core.protocol;

/**
 * Created by nikteg on 30/04/14.
 */
public class PlayerChangeName {
    private String name;

    public PlayerChangeName() {

    }

    public PlayerChangeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
