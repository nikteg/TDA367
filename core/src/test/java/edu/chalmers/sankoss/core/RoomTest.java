package edu.chalmers.sankoss.core;

import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Room;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by nikteg on 23/05/14.
 */
public class RoomTest {
    @Test
    public void testRooms() {
        CorePlayer player = new CorePlayer(1l);
        Room room = new Room(1l, "Test", "");
        room.addPlayer(player);

        assertTrue(room.hasPlayerWithID(1l));

        room.removePlayerWithID(1l);

        assertTrue(room.getPlayers().isEmpty());
    }
}
