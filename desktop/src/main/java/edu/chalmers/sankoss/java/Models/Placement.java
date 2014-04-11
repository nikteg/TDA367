package edu.chalmers.sankoss.java.Models;

import edu.chalmers.sankoss.core.Room;

import java.util.Map;

/**
 * Description of class.
 * More detailed description.
 *
 */
public class Placement extends ScreenModel{

    public ReadyBtnState readyBtnState;

    // in array 1 represent occupied and 0 free
    // merely a array for checking if coordinates are
    // occupied or free
    public int[] shipArray;

    public enum ReadyBtnState {
        READY, WAITING, ENTER;

        public ReadyBtnState getNext(){
            return values()[(ordinal() + 1) % values().length];
        }
    }

    public Placement() {
        this.readyBtnState = ReadyBtnState.READY;
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

    public void switchReadyBtnState() {
       readyBtnState = readyBtnState.getNext();
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

    public ReadyBtnState getReadyBtnState() {
        return readyBtnState;
    }

}
