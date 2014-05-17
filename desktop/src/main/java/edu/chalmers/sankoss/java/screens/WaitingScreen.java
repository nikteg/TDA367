package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.WaitingModel;
import edu.chalmers.sankoss.java.renderers.WaitingRenderer;

/**
 * Screen to be placed in when hosting a game and
 * waiting for an opponent to join.
 *
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 5/5/14
 */
public class WaitingScreen extends AbstractScreen<WaitingRenderer, WaitingModel> {

    public WaitingScreen() {
        setModel(new WaitingModel());
        setRenderer(new WaitingRenderer(getModel()));

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */

            @Override
            public void joinedRoom(BasePlayer player) {
                getModel().addPlayer(player);
            }

            /**
             * Should check if a player left the room...
             */
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        super.show();

        getModel().setHosting(SankossGame.getInstance().getClient().isHosting());
    }
}
