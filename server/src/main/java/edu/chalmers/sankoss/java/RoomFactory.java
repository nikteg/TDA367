package edu.chalmers.sankoss.java;

import edu.chalmers.sankoss.core.Room;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by nikteg on 28/03/14.
 */
public class RoomFactory {
    private static Map<Long, Room> rooms = new HashMap<Long, Room>();
    private static AtomicLong counter = new AtomicLong();

    public static Room createRoom(String name, String password) throws RoomNotFoundException {
        if (name.trim().length() == 0) {
            throw new RoomNotFoundException("Room could not be created");
        }

        Long roomID = counter.incrementAndGet();
        Room room = new Room(roomID, name, password);
        rooms.put(roomID, room);

        return room;
    }

    public static Room getRoom(Long id) throws RoomNotFoundException {
        if (rooms.containsKey(id)) {
            return rooms.get(id);
        }

        throw new RoomNotFoundException("Room was not found with given id: " + id.toString());
    }

    public static void removeRoom(Room room) throws RoomNotFoundException {
        Iterator it = rooms.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();

            if (pairs.getValue().equals(room)) {
                it.remove();

                return;
            }
        }

        throw new RoomNotFoundException("Game was not found");
    }

    public static void removeRoom(Long id) throws RoomNotFoundException {
        if (rooms.remove(id) == null) {
            throw new RoomNotFoundException("Game was not found with given id: " + id.toString());
        }
    }
    public static boolean hasPlayerWithID(Long id) {
        for (Room room : rooms.values()) {
            if (room.hasPlayerWithID(id))
                return true;
        }
        return false;
    }

    public static Map<Long, Room> getRooms() {
        return rooms;
    }
}
