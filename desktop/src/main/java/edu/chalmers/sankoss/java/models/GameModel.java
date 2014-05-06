package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.misc.ShipButton;

import java.util.Map;
import java.util.Set;

/**
 * Description of class.
 * More detailed description.
 *
 */
public class GameModel extends ScreenModel{

    private Map<Coordinate, ShipButton.Direction> rotationMap;
    private Map<Integer, Set<Coordinate>> shipMap;
    private String hitMsg = "";
    private int x;
    private int y;

    public GameModel(Map<Integer, Set<Coordinate>> shipMap, Map<Coordinate, ShipButton.Direction> rotationMap) {
        this.shipMap = shipMap;
        this.rotationMap = rotationMap;
        //Object[] coordinates = shipMap.get(2).toArray();
        //System.out.println("ShipMap with 2 Ship with start at " + ((Coordinate) coordinates[0]).getX() + ", " + ((Coordinate)coordinates[0]).getY());
    }

    public Map<Integer, Set<Coordinate>> getShipMap() {
        return shipMap;
    }

    public Map<Coordinate, ShipButton.Direction> getRotationMap() {
        return rotationMap;
    }

    /**
     * @inheritdoc
     */
    public Room getRoomByName(String roomName, Map<Long, Room> rooms) {
        return null;
    }

    public void setHitOrMiss(int x, int y, String msg) {
        hitMsg = msg;
        this.x = x;
        this.y = y;
    }

    public String getHitMsg() {
        return hitMsg;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @inheritdoc
     */
    public void setRoomMap(Map<Long, Room> roomMap) {

    }

}
