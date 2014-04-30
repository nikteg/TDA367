package edu.chalmers.sankoss.java.client;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;

import java.util.List;
import java.util.Map;

/**
 * Created by nikteg on 14/04/14.
 */
public interface ISankossClientListener {
    public void connected(Long playerID);
    public void fetchedRooms(Map<Long, Room> rooms);
    public void createdRoom(Long roomID);
    public void joinedRoom(Player player);
    public void startedGame(Long gameID, List<Player> players);
    public void gameReady();
    public void playerIsReady(Player player);
    public void turn();
    public void fireResult(Long gameID, Player target, Coordinate coordinate, boolean hit);
    public void destroyedShip(Player player, Ship ship);
    public void disconnected();
}
