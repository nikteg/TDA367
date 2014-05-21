package edu.chalmers.sankoss.desktop.mvc.lobby;

import com.badlogic.gdx.Gdx;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

import java.util.Map;

/**
 * Screen used at the game lobby when finding a game/room to join.
 * Handles game logic in lobby, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @modified Fredrik Thune, Niklas Tegnander
 * @date 3/24/14
 */
public class LobbyScreen extends AbstractScreen<LobbyModel, LobbyRenderer> {

    public LobbyScreen(Class<LobbyModel> model, Class<LobbyRenderer> renderer) {
        super(model, renderer);

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */

            @Override
            public void fetchedRooms(Map<Long, Room> rooms) {
                getModel().setRooms(rooms);

                Gdx.app.debug("LobbyScreen", "Fetched " + rooms.size() + " rooms");
            }

            @Override
            public void createdRoom(Room room) {
                Gdx.app.debug("LobbyScreen", "Room #" + room.getID() + " was created");

                Gdx.app.postRunnable(new Runnable() {

                    @Override
                    public void run() {
                    	getProptertyChangeSupport().firePropertyChange("ShowWaiting", true, false);
                    }
                });
            }

            @Override
            public void joinedRoom(CorePlayer player) {
                if (player.getID().equals(SankossGame.getInstance().getClient().getPlayer().getID())) {
                    Gdx.app.postRunnable(new Runnable() {

                        @Override
                        public void run() {
                        	getProptertyChangeSupport().firePropertyChange("ShowWaiting", true, false);
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
        getModel().setName(SankossGame.getInstance().getClient().getPlayer().getName());
        SankossGame.getInstance().getClient().fetchRooms();
    }
}
