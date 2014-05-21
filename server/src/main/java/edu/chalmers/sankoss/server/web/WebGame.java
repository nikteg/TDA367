package edu.chalmers.sankoss.server.web;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Niklas Tegnander
 */
public class WebGame {
    private Long id;
    private List<WebPlayer> players = new ArrayList<WebPlayer>();

    protected WebGame(Long id) {
        this.id = id;
    }

    protected void addPlayer(WebPlayer player) {
        players.add(player);
    }
}
