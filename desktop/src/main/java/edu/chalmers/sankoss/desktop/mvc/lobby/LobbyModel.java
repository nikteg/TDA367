package edu.chalmers.sankoss.desktop.mvc.lobby;

import edu.chalmers.sankoss.core.core.Room;
import edu.chalmers.sankoss.desktop.mvc.AbstractModel;

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
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        fireChange("name", name);
    }
    private Map<Long, Room> rooms = new HashMap<Long, Room>();

    public Map<Long, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<Long, Room> rooms) {
        this.rooms = rooms;
        fireChange("rooms", rooms);
    }
}
