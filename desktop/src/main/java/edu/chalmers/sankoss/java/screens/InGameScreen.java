package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.ApplicationListener;
import edu.chalmers.sankoss.java.Models.InGame;
import edu.chalmers.sankoss.java.Renderers.InGameRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

/**
 * Screen used ingame when actually playing!
 * Handles game logic ingame, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class InGameScreen extends AbstractScreen implements ApplicationListener {

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public InGameScreen(SankossController controller, SankossGame game) {
        super(controller, game);

        create();
    }

    /**
     * @inheritdoc
     */
    @Override
    public void show() {
        model = new InGame();
        renderer = new InGameRenderer(model);
    }

    /**
     * @inheritdoc
     */
    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void create() {

    }

    /**
     * @inheritdoc
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
