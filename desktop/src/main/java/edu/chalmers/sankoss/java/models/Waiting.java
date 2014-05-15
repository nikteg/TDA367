package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.Room;

import java.util.Map;

/**
 * Necessary model for WaitingScreen.
 *
 * @author Mikael Malmqvist
 * @date 5/5/14
 */
public class Waiting extends ScreenModel {

    @Override
    public Room getRoomByName(String roomName, Map<Long, Room> rooms) {
        return null;
    }

    @Override
    public void setRoomMap(Map<Long, Room> roomMap) {

    }
}
