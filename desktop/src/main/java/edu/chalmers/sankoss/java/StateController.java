package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

/**
 * Main state controller for control of the application.
 * The class will be using an instance of the interface
 * State depending on where in the application the user
 * is at.
 *
 * @author Mikael Malmqvist
 * @date 3/23/14
 */
public class StateController extends Game {
    private InputHandler inputHandler;
    private GameModel gameModel;
    private State currentState;
    private boolean appRunning;
    private GameState stateNow;

    // Enums for referencing to different states
    private enum GameState {
        MENU, LOBBY, PLACEMENT, INGAME
    }

    /**
     * Instantiates necessary variables to create a new game.
      */
    public StateController() {
        appRunning = true;
        currentState = new MainMenuController();
        stateNow = GameState.MENU;
        gameModel = new GameModel();
        inputHandler = new InputHandler();
    }

    @Override
    public void create() {
        // will we be having the game-loop here?
        // while(appRunning) {
               switch (stateNow) {
                   case MENU:

                   case LOBBY:

                   case PLACEMENT:

                   case INGAME:

               }
        // }
    }

    public void switchState(State state) {
        this.currentState = state;
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
