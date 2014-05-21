package edu.chalmers.sankoss.desktop.client;

import edu.chalmers.sankoss.core.*;
import edu.chalmers.sankoss.core.model.Coordinate;
import edu.chalmers.sankoss.core.model.CorePlayer;
import edu.chalmers.sankoss.core.model.Room;
import edu.chalmers.sankoss.core.model.Ship;

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
    public void playerChangedNat(CorePlayer player);
    public void playerChangeNat(CorePlayer player);
    public void leftGame(CorePlayer player);
    public void leftRoom(CorePlayer player);
    public void removedRoom();
    public void looser();
    public void winner();
}
