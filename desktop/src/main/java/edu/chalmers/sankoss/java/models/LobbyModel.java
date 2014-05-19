package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.Room;

import java.util.HashMap;
import java.util.Map;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class LobbyModel extends AbstractModel {
    private Map<Long, Room> rooms = new HashMap<Long, Room>();

    public Map<Long, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<Long, Room> rooms) {
        this.rooms = rooms;

        setChanged();
        notifyObservers("rooms");
    }
}
