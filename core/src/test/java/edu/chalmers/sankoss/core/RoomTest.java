package edu.chalmers.sankoss.core;

import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Room;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by nikteg on 23/05/14.
 * Modified by mikmal.
 */
public class RoomTest {
    @Test
    public void testRooms() throws Exception {
        CorePlayer player = new CorePlayer(1l);
        Room room = new Room(1l, "Test", "");
        room.addPlayer(player);

        assertTrue(room.hasPlayerWithID(1l));

        room.removePlayerWithID(1l);

        assertTrue(room.getPlayers().isEmpty());
    }

    @Test
    public void testAddPlayer() throws Exception {
        Room testRoom = new Room(1l, "Test", "");
        CorePlayer player = new CorePlayer(1l);

        testRoom.addPlayer(player);

        assertTrue(testRoom.getPlayers().contains(player));

    }

    @Test
    public void testRemovePlayerWithID() throws Exception {
        Room testRoom = new Room(1l, "Test", "");
        CorePlayer player = new CorePlayer(1l);

        testRoom.addPlayer(player);
        testRoom.removePlayerWithID(player.getID());

        assertTrue(!testRoom.getPlayers().contains(player));

    }

    @Test
    public void testToString() throws Exception {
        Room testRoom = new Room(1l, "Test", "");

        assertTrue(testRoom.toString().equals(testRoom.getName()));
    }

    @Test
    public void testEquals() throws Exception {
        Room testRoom = new Room();

        testRoom.setName("Test");
        testRoom.setID(1l);
        testRoom.setPassword("");

        assertTrue(testRoom.equals(new Room(1l, "Test", "")));
    }

    @Test
    public void testHasPlayerWithID() throws Exception{
        Room testRoom = new Room(1l, "Test", "");
        CorePlayer player = new CorePlayer(1l);

        testRoom.addPlayer(player);

        assertTrue(testRoom.hasPlayerWithID(1l));


    }

    @Test
    public void testIsPlayerWithIDHost() throws Exception {

        Room testRoom = new Room(1l, "Test", "");
        CorePlayer player = new CorePlayer(1l);
        CorePlayer player2 = new CorePlayer(2l);

        testRoom.addPlayer(player);
        testRoom.addPlayer(player2);

        assertTrue(testRoom.getPlayers().get(0).getID().equals(player.getID()));
    }

}
