package edu.chalmers.sankoss.java.client;

import edu.chalmers.sankoss.core.*;

import java.util.List;
import java.util.Map;

/**
 * Created by nikteg on 14/04/14.
 */
public interface ISankossClientListener {
    public void connected(Long playerID);
    public void fetchedRooms(Map<Long, Room> rooms);
    public void createdRoom(Long roomID);
    public void joinedRoom(BasePlayer player);
    public void startedGame(Long gameID, List<Player> players);
    public void gameReady();
    public void playerIsReady(BasePlayer player);
    public void turn();
    public void fireResult(Long gameID, BasePlayer target, Coordinate coordinate, boolean hit);
    public void destroyedShip(BasePlayer player, Ship ship);
    public void disconnected();
    public void playerChangedName(BasePlayer player, String name);
}
