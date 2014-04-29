package edu.chalmers.sankoss.java.web;

import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.Game;
import edu.chalmers.sankoss.java.JsonTransformerRoute;
import edu.chalmers.sankoss.java.SankossServer;
import spark.Request;
import spark.Response;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public WebServer(SankossServer sankossServer) {
    }

    @Override
    public void run() {
        if (server == null) return;

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
        if (evt.getPropertyName().equals("playerConnected") || evt.getPropertyName().equals("playerDisconnected")) {
            players.clear();

            for (Map.Entry<Player, SankossServer.PlayerConnection> entry : server.getPlayers().entrySet()) {
                Player player = entry.getKey();
                SankossServer.PlayerConnection connection = entry.getValue();

                String address = connection.getRemoteAddressTCP().toString().contains("127.0.0.1") ? "Bot" : connection.getRemoteAddressTCP().toString();

                players.add(new WebPlayer(player.getID(), player.getName(), address));
            }
        }

        if (evt.getPropertyName().equals("roomCreated") || evt.getPropertyName().equals("roomRemoved") || evt.getPropertyName().equals("roomJoined")) {
            rooms.clear();

            for (Room room : server.getRooms().values()) {
                WebRoom webRoom = new WebRoom(room.getID());

                for (Player player : room.getPlayers()) {
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

            for (Game game : server.getGames().values()) {
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
