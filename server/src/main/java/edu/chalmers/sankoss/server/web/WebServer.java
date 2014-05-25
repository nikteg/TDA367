package edu.chalmers.sankoss.server.web;

import edu.chalmers.sankoss.server.*;
import edu.chalmers.sankoss.server.server.Game;
import edu.chalmers.sankoss.server.server.GameFactory;
import edu.chalmers.sankoss.server.server.Player;
import edu.chalmers.sankoss.server.server.RoomFactory;
import edu.chalmers.sankoss.server.server.SankossServer;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Room;
import spark.Request;
import spark.Response;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.setPort;

/**
 * @author Niklas Tegnander
 */
public class WebServer implements Runnable, PropertyChangeListener {

    private SankossServer server;
    private int port;
    private List<WebPlayer> players = new ArrayList<WebPlayer>();
    private List<WebRoom> rooms = new ArrayList<WebRoom>();
    private List<WebGame> games = new ArrayList<WebGame>();

    public WebServer(SankossServer server, int port) {
        this.server = server;
        this.port = port;

        server.addPropertyChangeListener(this);
    }

    public WebServer(SankossServer server) {
        this(server, 8080);
    }

    @Override
    public void run() {
        setPort(port);

        get(new JsonTransformerRoute("/players") {
            @Override
            public Object handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", "*");

                return players;
            }
        });

        get(new JsonTransformerRoute("/rooms") {
            @Override
            public Object handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", "*");

                return rooms;
            }
        });

        get(new JsonTransformerRoute("/games") {
            @Override
            public Object handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", "*");

                return games;
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (server == null) return;

        if (evt.getPropertyName().equals("playerConnected") || evt.getPropertyName().equals("playerDisconnected") || evt.getPropertyName().equals("playerChangedName")) {
            players.clear();

            for (SankossServer.PlayerConnection playerConnection : server.getPlayerConnections()) {
                players.add(new WebPlayer(playerConnection.getPlayer().getID(), playerConnection.getPlayer().getName(), playerConnection.getRemoteAddressTCP().toString()));
            }
        }

        if (evt.getPropertyName().equals("roomCreated") || evt.getPropertyName().equals("roomRemoved") || evt.getPropertyName().equals("roomJoined")) {
            rooms.clear();

            for (Room room : RoomFactory.getRooms().values()) {
                WebRoom webRoom = new WebRoom(room.getID());

                for (CorePlayer player : room.getPlayers()) {
                    for (WebPlayer webPlayer : players) {
                        if (webPlayer.getID().equals(player.getID())) {
                            webRoom.addPlayer(webPlayer);
                        }
                    }
                }

                rooms.add(webRoom);
            }
        }

        if (evt.getPropertyName().equals("gameCreated") || evt.getPropertyName().equals("gameRemoved")) {
            games.clear();

            for (Game game : GameFactory.getGames().values()) {
                WebGame webGame = new WebGame(game.getID());

                for (Player player : game.getPlayers()) {
                    for (WebPlayer webPlayer : players) {
                        if (webPlayer.getID().equals(player.getID())) {
                            webGame.addPlayer(webPlayer);
                        }
                    }
                }

                games.add(webGame);
            }
        }
    }
}
