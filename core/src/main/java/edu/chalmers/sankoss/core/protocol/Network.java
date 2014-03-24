package edu.chalmers.sankoss.core.protocol;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    public static final int port = 30401;
    
    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(CreateRoom.class);
        kryo.register(FetchRooms.class);
        kryo.register(JoinRoom.class);
        kryo.register(Player.class);
        kryo.register(Player[].class);
        kryo.register(Room.class);
        kryo.register(Room[].class);
        kryo.register(CreateGame.class);
        kryo.register(Game.class);
        kryo.register(boolean[].class);
        kryo.register(boolean[][].class);
    }
}