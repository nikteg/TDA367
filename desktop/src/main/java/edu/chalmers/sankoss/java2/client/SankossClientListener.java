package edu.chalmers.sankoss.java2.client;

import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;

import java.util.Map;

/**
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 */
public class SankossClientListener implements ISankossClientListener {

    @Override
    public void connected(BasePlayer player) {

    }

    @Override
    public void fetchedRooms(Map<Long, Room> rooms) {

    }

    @Override
    public void createdRoom(Long roomID) {

    }

    @Override
    public void joinedRoom(BasePlayer player) {

    }

    @Override
    public void startedGame(Long gameID) {

    }

    @Override
    public void gameReady() {

    }

    @Override
    public void playerIsReady(BasePlayer player) {

    }

    @Override
    public void turn() {

    }

    @Override
    public void fireResult(Long gameID, BasePlayer target, Coordinate coordinate, boolean hit) {

    }

    @Override
    public void destroyedShip(BasePlayer player, Ship ship) {

    }

    @Override
    public void disconnected() {

    }

    @Override
    public void playerChangedName(BasePlayer player) {

    }
}
