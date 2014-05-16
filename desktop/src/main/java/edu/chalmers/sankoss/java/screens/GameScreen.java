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
public class GameScreen extends AbstractScreen<GameRenderer, GameModel> {

    public GameScreen() {
        setModel(new GameModel());
        setRenderer(new GameRenderer(getModel()));

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */
        });
    }

    @Override
    public void update(float delta) {

    }
}
