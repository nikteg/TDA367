package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.core.Room;

import edu.chalmers.sankoss.desktop.mvc.lobby.LobbyModel;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class LobbyModelTest {

    @Test
    public void testGetRoomByName() throws Exception {

        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> rooms = new HashMap<Long, Room>();
        Room newRoom = new Room();
        newRoom.setName("testroom");
        rooms.put(1l, newRoom);

        testModel.setRooms(rooms);

        boolean found = false;

        for(Room room : testModel.getRooms().values()) {
            if(room.getName().equals(newRoom.getName())) {
                found = true;
                return;
            }
        }

        assert(found);
    }

    @Test
    public void testSetRoomMap() throws Exception {

        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        testModel.setRooms(roomMap);

        assert(testModel.getRooms().equals(roomMap));
    }

    @Test
    public void testGetKeys() throws Exception {

        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        roomMap.put(1l, new Room());
        roomMap.put(2l, new Room());
        roomMap.put(3l, new Room());

        Set<Long> mapsKeys = roomMap.keySet();

        Set<Long> correctKeys = new HashSet<Long>();
        correctKeys.add(1l);
        correctKeys.add(2l);
        correctKeys.add(3l);

        testModel.setRooms(roomMap);

        assert(testModel.getRooms().keySet().equals(correctKeys));
    }

    @Test
    public void testGetRooms() throws Exception {

        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        testModel.setRooms(roomMap);

        assert(testModel.getRooms().equals(roomMap));
    }

    @Test
    public void testGetRoomNames() throws Exception {
        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        roomMap.put(1l, new Room(1l, "Room1", ""));
        roomMap.put(2l, new Room(2l, "Room2", ""));
        roomMap.put(3l, new Room(3l, "Room3", ""));

        testModel.setRooms(roomMap);

        String[] correctNames = {"Room1", "Room2", "Room3"};

        boolean same = true;

        for(int i = 0; i < correctNames.length; i++) {
            if(!testModel.getRooms().values().toArray()[i].equals(correctNames[i])) {
                same = false;
                return;
            }
        }

        assert(same);
    }

}
