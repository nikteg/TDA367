package edu.chalmers.sankoss.java.models;

import edu.chalmers.sankoss.core.Room;
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
 * @group
 * @date 4/7/14
 */
public class LobbyTest {
    @Test
    public void testGetRoomByName() throws Exception {
        Map<Long, Room> rooms = new HashMap<Long, Room>();
        Room newRoom = new Room();
        newRoom.setName("testroom");
        rooms.put(1l, newRoom);
        boolean found = false;

        for(Room room : rooms.values()) {
            if(room.getName().equals("testroom")) {
                found = true;
            }
        }

        assert(found);
    }

    @Test
    public void testSetRoomMap() throws Exception {
        Map<Long, Room> roomMap1 = new HashMap<Long, Room>();
        Map<Long, Room> roomMap2 = roomMap1;

        assert(roomMap1 == roomMap2);
    }

    @Test
    public void testGetKeys() throws Exception {
        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        roomMap.put(1l, new Room());
        roomMap.put(2l, new Room());
        roomMap.put(3l, new Room());

        Set<Long> mapsKeys = roomMap.keySet();

        Set<Long> correctKeys = new HashSet<Long>();
        correctKeys.add(1l);
        correctKeys.add(2l);
        correctKeys.add(3l);

        assert(mapsKeys.equals(correctKeys));
    }

    @Test
    public void testGetRooms() throws Exception {

        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        roomMap.put(1l, new Room(1l, "Room1", ""));
        roomMap.put(2l, new Room(2l, "Room2", ""));

        Object[] keys = {1l, 2l};
        Room[] mapEntries = new Room[keys.length];

        for(int i = 0; i < mapEntries.length; i++) {
            mapEntries[i] = roomMap.get(keys[i]);
        }

        Room[] correctEntries = {new Room(1l, "Room1", ""), new Room(2l, "Room2", "")};

        boolean same = true;

        for(int i = 0; i < mapEntries.length; i++) {
            if(!mapEntries[i].equals(correctEntries[i])) {
                same = false;
            }

        }

        assert(same);
    }

    @Test
    public void testGetRoomNames() throws Exception {
        Map<Long, Room> roomMap = new HashMap<Long, Room>();
        roomMap.put(1l, new Room(1l, "Room1", ""));
        roomMap.put(2l, new Room(2l, "Room2", ""));
        roomMap.put(3l, new Room(3l, "Room3", ""));

        Object[] mapsRooms = roomMap.values().toArray();
        String[] mapsNames = new String[mapsRooms.length];


        for(int i = 0; i < mapsNames.length; i++) {
            mapsNames[i] = ((Room) mapsRooms[i]).getName();
        }

        String[] correctNames = {"Room1", "Room2", "Room3"};

        boolean same = true;

        for(int i = 0; i < mapsNames.length; i++) {
            if(!mapsNames[i].equals(correctNames[i])) {
                same = false;
            }
        }

        assert(same);
    }

}
