package edu.chalmers.sankoss.java;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import edu.chalmers.sankoss.core.Sankoss;

public class SankossServer {
    private Server server;
    //private ArrayList<Room> rooms = new ArrayList<Room>();
    
	public static void main (String[] args) {
	    new Server();
	}
	
	public SankossServer() throws IOException {
        server = new Server() {
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };
        
        Network.register(server);
        
        server.addListener(new Listener() {
            public void received(Connection c, Object object) {
                PlayerConnection connection = (PlayerConnection)c;
                Player player = connection.getPlayer();
            }
            
            public void disconnected(Connection c) {
                
            }
        });
        
        server.bind(Network.port);
        server.start();
        System.out.println("Server started on port: " + Network.port);

	}
}
