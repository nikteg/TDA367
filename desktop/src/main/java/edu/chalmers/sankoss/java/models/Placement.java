package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.misc.ShipButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Description of class.
 * More detailed description.
 *
 */
public class Placement extends ScreenModel{

    private Map<Coordinate, ShipButton.Direction> rotationMap;
    private Map<Integer, Set<Coordinate>> shipMap;
    private ReadyBtnState readyBtnState;
    private String land;


    /**
     * Enum representing the 3 different stages of the Ready button.
     */
    public enum ReadyBtnState {
        READY("Ready"), WAITING("Waiting.."), ENTER("Enter Game!");
        private String text;

        public ReadyBtnState getNext(){
            return values()[(ordinal() + 1) % values().length];
        }

        ReadyBtnState(String text) {
            this.text = text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public Placement() {
        shipMap = new HashMap<Integer, Set<Coordinate>>();
        rotationMap = new HashMap<Coordinate, ShipButton.Direction>();
        this.readyBtnState = ReadyBtnState.READY;
        shipArray = new int[100];
        zeroArray();
    }

    public Map<Coordinate, ShipButton.Direction> getRotationMap (){
        return rotationMap;
    }

    public Map<Integer, Set<Coordinate>> getShipMap() {
        return shipMap;
    }


    public void setReadyBtnState(ReadyBtnState state) {
        this.readyBtnState = state;

    }


    /**
     * Method for switching state of enum ReadyBtnState.
     * Goes READY - WAITING - ENTER
     */
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
