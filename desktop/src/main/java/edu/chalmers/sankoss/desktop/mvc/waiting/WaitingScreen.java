package edu.chalmers.sankoss.desktop.mvc.waiting;

import com.badlogic.gdx.Gdx;

import edu.chalmers.sankoss.core.model.CorePlayer;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

/**
 * Screen to be placed in when hosting a game and
 * waiting for an opponent to join.
 *
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 5/5/14
 */
public class WaitingScreen extends AbstractScreen<WaitingModel, WaitingRenderer> {

    public WaitingScreen(Class<WaitingModel> model, Class<WaitingRenderer> renderer) {
        super(model, renderer);

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */

            //TODO Create method in interface when YOU has successfully joined
            @Override
            public void joinedRoom(CorePlayer player) {
                getModel().addPlayer(player);
            }

            @Override
            public void leftRoom(CorePlayer player) {
                getModel().removePlayer(player);
            }

            @Override
            public void removedRoom() {
                Gdx.app.postRunnable(new Runnable() {

                    @Override
                    public void run() {
                    	getProptertyChangeSupport().firePropertyChange("ShowLobby", true, false);
                    }
                });
            }

            @Override
            public void startedGame(Long gameID) {
                Gdx.app.postRunnable(new Runnable() {

                    @Override
                    public void run() {
                    	getProptertyChangeSupport().firePropertyChange("showPlacement", true, false);
                    }
                });
            }
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        super.show();
        getModel().resetPlayers();
        getModel().setHosting(SankossGame.getInstance().getClient().isHosting());
    }
}
