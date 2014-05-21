package edu.chalmers.sankoss.core.protocol;

import edu.chalmers.sankoss.core.model.CorePlayer;
import edu.chalmers.sankoss.core.model.Room;

/**
 * 
 * @author Fredrik Thune
 * 
 */
public class JoinedRoom {
    private Room room;
    private CorePlayer player;

    public JoinedRoom() {

    }

    public JoinedRoom(Room room, CorePlayer player) {
        this.room = room;
        this.player = player;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public CorePlayer getPlayer() {
        return player;
    }

    public void setPlayer(CorePlayer player) {
        this.player = player;
    }
}
