package edu.chalmers.sankoss.server;

import java.io.IOException;

import edu.chalmers.sankoss.server.server.SankossServer;
import edu.chalmers.sankoss.server.web.WebServer;

public class ServerStarter {
    /**
     * Constructor
     * @param args
     * @throws IOException if the server couldn't start and bind to an address
     */
    public static void main(String[] args) throws IOException {
        SankossServer sankossServer = new SankossServer();
        //sankossServer.startHTTPServer(8080);

        new Thread(new WebServer(sankossServer)).start();
    }
}
