package edu.chalmers.sankoss.java.client;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;

import java.util.List;
import java.util.Map;

/**
 * @author Niklas Tegnander
 */
public class SankossClientListener implements ISankossClientListener {

    @Override
    public void connected(Long playerID) {

    }

    @Override
    public void fetchedRooms(Map<Long, Room> rooms) {

    }

    @Override
    public void createdRoom(Long roomID) {

    }

    @Override
    public void joinedRoom(Player player) {

    }

    @Override
    public void startedGame(Long gameID, List<Player> players) {

    }

    @Override
    public void gameReady() {

    }

    @Override
    public void playerIsReady(Player player) {

    }

    @Override
    public void turn() {

    }

    @Override
    public void fireResult(Long gameID, Player target, Coordinate coordinate, boolean hit) {

    }

    @Override
    public void destroyedShip(Player player, Ship ship) {

    }

    @Override
    public void disconnected() {

    }
}
