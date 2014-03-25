package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.InGame;
import edu.chalmers.sankoss.java.Renderers.InGameRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class InGameScreen implements Screen{

    private InGame inGame;
    private InGameRenderer inGameRenderer;
    private SankossGame game;
    private SankossController controller;

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public InGameScreen(SankossController controller, SankossGame game) {
        this.controller = controller;
        this.game = game;
    }

    @Override
    public void render(float delta) {
        game.setScreen(controller.getNextScreen(SankossController.CurrentScreen.INGAME));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        inGame = new InGame();
        inGameRenderer = new InGameRenderer(inGame);
    }

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
    public void dispose() {

    }
}
