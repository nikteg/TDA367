package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Gdx;
import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.Screens;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.LobbyModel;
import edu.chalmers.sankoss.java.renderers.LobbyRenderer;

import java.util.Map;

/**
 * Screen used at the game lobby when finding a game/room to join.
 * Handles game logic in lobby, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @modified Fredrik Thune, Niklas Tegnander
 * @date 3/24/14
 */
public class LobbyScreen extends AbstractScreen<LobbyRenderer, LobbyModel> {

    public LobbyScreen() {
        setModel(new LobbyModel());
        setRenderer(new LobbyRenderer(getModel()));

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */

            @Override
            public void fetchedRooms(Map<Long, Room> rooms) {
                getModel().setRooms(rooms);

                Gdx.app.debug("LobbyScreen", "Fetched " + rooms.size() + " rooms");
            }

            @Override
            public void createdRoom(Long roomID) {
                Gdx.app.debug("LobbyScreen", "Room #" + roomID + " was created");

                Gdx.app.postRunnable(new Runnable() {

                    @Override
                    public void run() {
                        Screens.WAITING.show();
                    }
                });
            }

            @Override
            public void joinedRoom(BasePlayer player) {
                if (player.getID().equals(SankossGame.getInstance().getClient().getPlayer().getID())) {
                    Gdx.app.postRunnable(new Runnable() {

                        @Override
                        public void run() {
                            Screens.WAITING.show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        super.show();

        SankossGame.getInstance().getClient().fetchRooms();
    }
}
