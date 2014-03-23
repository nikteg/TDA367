package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

/**
 * Main controller to handle all the others.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @group
 * @date 3/23/14
 */
public class GameController extends Game {
    private InputHandler inputHandler;
    private GameModel gameModel;
    private MainMenuController mainMenuController;
    private PlaceController placeController;
    private LobbyController lobbyController;
    private InGameController inGameController;

    /**
     * Instantiates necessary variables to create a new game.
      */
    public GameController() {
        mainMenuController = new MainMenuController();
        gameModel = new GameModel();
        inputHandler = new InputHandler();
    }

    @Override
    public void create() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
