package edu.chalmers.sankoss.core;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Room;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.core.protocol.*;

/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 * 
 */
public class Network {
    public static final int PORT = 30401;
    
    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(CorePlayer.class);
        kryo.register(CorePlayer.Nationality.class);
        kryo.register(Connected.class);
        kryo.register(Coordinate.class);
        kryo.register(CreateAI.class);
        kryo.register(CreatedRoom.class);
        kryo.register(CreateRoom.class);
        kryo.register(DestroyedShip.class);
        kryo.register(Disconnect.class);
        kryo.register(FetchedRooms.class);
        kryo.register(FetchRooms.class);
        kryo.register(Fire.class);
        kryo.register(FireResult.class);
        kryo.register(GameReady.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(JoinedRoom.class);
        kryo.register(JoinRoom.class);
        kryo.register(LeaveGame.class);
        kryo.register(LeaveRoom.class);
        kryo.register(LeftGame.class);
        kryo.register(LeftRoom.class);
        kryo.register(Looser.class);
        kryo.register(PlayerChangedName.class);
        kryo.register(PlayerChangeName.class);
        kryo.register(PlayerIsReady.class);
        kryo.register(PlayerReady.class);
        kryo.register(Room.class);
        kryo.register(RemoveRoom.class);
        kryo.register(RemovedRoom.class);
        kryo.register(Ship.class);
        kryo.register(StartedGame.class);
        kryo.register(StartGame.class);
        kryo.register(Turn.class);
        kryo.register(PlayerChangeNat.class);
        kryo.register(PlayerChangedNat.class);
        kryo.register(Winner.class);
    }
}