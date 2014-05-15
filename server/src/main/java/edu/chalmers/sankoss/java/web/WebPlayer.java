package edu.chalmers.sankoss.java.web;

/**
 * @author Niklas Tegnander
 */
public class WebPlayer {
    private Long id;
    private String name;
    private String address;

    protected WebPlayer(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    protected Long getID() {
        return id;
    }

    protected String getName() {
        return name;
    }

    protected String getAddress() {
        return address;
    }
}
