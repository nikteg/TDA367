package edu.chalmers.sankoss.desktop.mvc.waiting;

import com.badlogic.gdx.Gdx;

import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Screen to be placed in when hosting a game and
 * waiting for an opponent to join.
 *
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 5/5/14
 */
public class WaitingScreen extends AbstractScreen<WaitingModel, WaitingRenderer> {

    public WaitingScreen() {

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
                        changeScreen("lobby");
                    }
                });
            }

            @Override
            public void startedGame(Long gameID) {
                Gdx.app.postRunnable(new Runnable() {

                    @Override
                    public void run() {
                        changeScreen("placement");
                    }
                });
            }
        });

        getRenderer().getBtnBack().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (SankossGame.getInstance().getClient().isHosting()) {
                    SankossGame.getInstance().getClient().removeRoom(SankossGame.getInstance().getClient().getRoom().getID());

                    Gdx.app.debug("WaitingRenderer", "Removing hosted room");
                } else {
                    SankossGame.getInstance().getClient().leaveRoom();
                }

                changeScreen("lobby");
            }
        });

        getRenderer().getBtnStart().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SankossGame.getInstance().getClient().startGame();
                //changeScreen("game");
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
        System.out.println(SankossGame.getInstance().getClient().isHosting());
    }
}
