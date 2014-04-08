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

    public enum ReadyBtnState {
        READY, WAITING, ENTER;

        public ReadyBtnState getNext(){
            return values()[(ordinal() + 1) % values().length];
        }
    }

    public Placement() {
        this.readyBtnState = ReadyBtnState.READY;
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
