package edu.chalmers.sankoss.java.Models;

import edu.chalmers.sankoss.core.Room;

import java.util.Map;

/**
 * Description of class.
 * More detailed description.
 *
 */
public class GameModel extends ScreenModel{

    // in array 1 represent occupied and 0 free
    // merely a array for checking if coordinates are
    // occupied or free
    private int[] shipArray;

    public GameModel() {
        shipArray = new int[100];
        zeroArray();
    }

    public void zeroArray() {
        for(int i = 0; i < 100; i++) {
            shipArray[i] = 0;
        }
    }

    /**
     * Method for setting a position in array to occupied
     * @param x
     * @param y
     */
    public void addToShipArray(int x, int y) {
        shipArray[x*10 + y] = 1;
    }

    public int[] getShipArray() {
        return shipArray;
    }

    /**
     * @inheritdoc
     */
    public Room getRoomByName(String roomName, Map<Long, Room> rooms) {
        return null;
    }

    /**
     * @inheritdoc
     */
    public void setRoomMap(Map<Long, Room> roomMap) {

    }

}
