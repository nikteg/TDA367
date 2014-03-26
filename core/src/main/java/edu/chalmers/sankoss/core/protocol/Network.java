package edu.chalmers.sankoss.core.protocol;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
/**
 * 
 * @author Niklas Tegnander
 * @modified Fredrik Thune
 * 
 */
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
        kryo.register(Miss.class);
        kryo.register(Hit.class);
        kryo.register(Fire.class);
        kryo.register(GameReady.class);
        kryo.register(PlayerReady.class);
        kryo.register(Turn.class);
        kryo.register(Disconnect.class);
        kryo.register(boolean[].class);
        kryo.register(boolean[][].class);
    }
}