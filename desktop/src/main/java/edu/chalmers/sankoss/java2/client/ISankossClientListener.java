package edu.chalmers.sankoss.java2.client;

import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;

import java.util.Map;

/**
 * Created by nikteg on 14/04/14.
 */
public interface ISankossClientListener {
    public void connected(BasePlayer player);
    public void fetchedRooms(Map<Long, Room> rooms);
    public void createdRoom(Long roomID);
    public void joinedRoom(BasePlayer player);
    public void startedGame(Long gameID);
    public void gameReady();
    public void playerIsReady(BasePlayer player);
    public void turn();
    public void fireResult(Long gameID, BasePlayer target, Coordinate coordinate, boolean hit);
    public void destroyedShip(BasePlayer player, Ship ship);
    public void disconnected();
    public void playerChangedName(BasePlayer player);
}
