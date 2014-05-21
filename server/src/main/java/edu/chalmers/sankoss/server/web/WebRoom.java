package edu.chalmers.sankoss.server.web;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Niklas Tegnander
 */
public class WebRoom {
    private Long id;
    private List<WebPlayer> players = new ArrayList<WebPlayer>();

    protected WebRoom(Long id) {
        this.id = id;
    }

    protected void addPlayer(WebPlayer player) {
        players.add(player);
    }
}
