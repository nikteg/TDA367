package edu.chalmers.sankoss.java.Models;

import edu.chalmers.sankoss.core.protocol.Room;

import java.util.HashMap;
import java.util.Map;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class Lobby extends ScreenModel {
    private Map<Long, Room> rooms = new HashMap<Long, Room>();

    public Map<Long, Room> getRoomMap() {
        return this.rooms;
    }

}
