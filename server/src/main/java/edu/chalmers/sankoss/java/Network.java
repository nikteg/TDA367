package edu.chalmers.sankoss.java;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    public static final int port = 30401;
    
    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        //kryo.register(Fire.class);
    }
}