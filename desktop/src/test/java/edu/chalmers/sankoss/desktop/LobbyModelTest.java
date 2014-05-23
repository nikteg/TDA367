package edu.chalmers.sankoss.desktop;

import edu.chalmers.sankoss.core.core.Room;
import edu.chalmers.sankoss.desktop.mvc.lobby.LobbyModel;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;

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

        for (Room room : testModel.getRooms().values()) {
            if (room.getName().equals(newRoom.getName())) {
                found = true;
                return;
            }
        }

        assertTrue(found);
    }

    @Test
    public void testSetRoomMap() throws Exception {

        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        testModel.setRooms(roomMap);

        assertTrue(testModel.getRooms().equals(roomMap));
    }

    @Test
    public void testGetKeys() throws Exception {

        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        roomMap.put(5l, new Room());
        roomMap.put(6l, new Room());
        roomMap.put(7l, new Room());

        Set<Long> mapsKeys = roomMap.keySet();

        Set<Long> correctKeys = new HashSet<Long>();
        correctKeys.add(5l);
        correctKeys.add(6l);
        correctKeys.add(7l);

        testModel.setRooms(roomMap);

        assertTrue(testModel.getRooms().keySet().equals(correctKeys));
    }

    @Test
    public void testGetRooms() throws Exception {

        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        testModel.setRooms(roomMap);

        assertTrue(testModel.getRooms().equals(roomMap));
    }

    @Test
    public void testGetRoomNames() throws Exception {
        LobbyModel testModel = new LobbyModel();

        Map<Long, Room> roomMap = new HashMap<Long, Room>();

        roomMap.put(2l, new Room(2l, "Room2", ""));
        roomMap.put(3l, new Room(3l, "Room3", ""));
        roomMap.put(4l, new Room(4l, "Room4", ""));

        testModel.setRooms(roomMap);

        assertTrue(testModel.getRooms().get(1l).getName().equals("Room2"));
        assertTrue(testModel.getRooms().get(2l).getName().equals("Room3"));
        assertTrue(testModel.getRooms().get(3l).getName().equals("Room4"));

    }

}
