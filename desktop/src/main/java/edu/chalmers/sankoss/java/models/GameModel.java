package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.Room;

import java.util.Map;

/**
 * Description of class.
 * More detailed description.
 *
 */
public class GameModel extends ScreenModel{

    private String hitMsg = "";
    private int x;
    private int y;

    public GameModel() {

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
