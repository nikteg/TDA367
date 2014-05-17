package edu.chalmers.sankoss.java.client;

import edu.chalmers.sankoss.core.*;

import java.util.Map;

/**
 * @author Niklas Tegnander
 */
public interface ISankossClientListener {
    public void connected();
    public void fetchedRooms(Map<Long, Room> rooms);
    public void createdRoom(Room room);
    public void joinedRoom(CorePlayer player);
    public void startedGame(Long gameID);
    public void gameReady();
    public void playerIsReady(CorePlayer player);
    public void turn();
    public void fireResult(CorePlayer target, Coordinate coordinate, boolean hit);
    public void destroyedShip(CorePlayer player, Ship ship);
    public void disconnected();
    public void playerChangedName(CorePlayer player);
    public void leftGame(CorePlayer player);
    public void leftRoom(CorePlayer player);
    public void removedRoom();
}
