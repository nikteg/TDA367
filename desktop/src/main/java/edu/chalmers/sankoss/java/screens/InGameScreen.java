package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.Models.InGame;
import edu.chalmers.sankoss.java.Renderers.InGameRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;

import java.util.List;
import java.util.Map;

/**
 * Screen used ingame when actually playing!
 * Handles game logic ingame, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class InGameScreen extends AbstractScreen {

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public InGameScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new InGame();
        model.getClient().addListener(new GameListener());
        renderer = new InGameRenderer(model);

        create();
    }

    private class GameListener extends SankossClientListener {
        /**
         * This is where we override the methods we want to use
         */
    }

    /**
     * @inheritdoc
     */
    @Override
    public void show() {

    }

    /**
     * @inheritdoc
     */
    @Override
    public void hide() {
        if(stage.getRoot().hasChildren()) {
            stage.getRoot().clearChildren();
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void create() {
        super.create();

        // Sets the stage as input source
        controller.changeInput(stage);

        renderer.drawControllers(this);

        stage.addActor(renderer.getActorPanel());
        stage.draw();
    }

    /**
     * @inheritdoc
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        renderer.resize(width, height);
    }

    /**
     * Game loop for the current Screen.
     * This method loops as long this Screen is active.
     * @param delta
     */
    @Override
    public void render(float delta) {

    }

    @Override
    public void render() {

    }
}
