package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.GameModel;
import edu.chalmers.sankoss.java.renderers.GameRenderer;

/**
 * Screen used when placing the ships.
 * Handles game logic when placing ships, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class GameScreen extends AbstractScreen<GameModel, GameRenderer> {

    public GameScreen(Class<GameModel> model, Class<GameRenderer> renderer) {
        super(model, renderer);

        /**
         * Listener for GameScreen.
         */
        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {

            /**
             * Sets to your turn when getting
             * this package call.
             */
            @Override
            public void turn() {
                getModel().setMyTurn(true);

            }
        });
    }

    @Override
    public void update(float delta) {

    }


}
