package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.screens.AbstractScreen;
import edu.chalmers.sankoss.java.screens.MainMenuScreen;

/**
 * Logical controller for the application.
 * This class handles the overall logic of the application. It
 * switches between screens and handles user input trough an
 * InputProcessor.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class SankossController{
    private InputProcessor activeInputProcessor;

    private SankossGame sankossGame;
    private AbstractScreen screen;

    // ENUMS to keep track of the current Screen
    public enum ScreenState {
        MAINMENU, LOBBY, PLACEMENT, INGAME
    }

    /**
     * Default constructor.
     */
    public SankossController() {
        super();
    }


    /**
     * This constructor instantiates all necessary variables
     * for the game.
     * Additionally it sets the main menu Screen as the initial
     * Screen.
     * @param sankossGame is the current Game that has been
     *                    started in the GameStarter.
     */
    public SankossController(SankossGame sankossGame) {
        this.sankossGame = sankossGame;
        // setMainMenuScreen();
        changeScreen(new MainMenuScreen(this, this.sankossGame));
        screen.getModel().connectClient();
        screen.getModel().setNumberOfShips(4);
    }

    // TODO: Remove this method
    /**
     * Method for returning a new Screen and setting correct
     * InputProcessor.
     * This depends on what Screen is active at the moment
     * and uses an Enum of the type CurrentScreen to decide this.
     * The method does also set the InputProcessor depending on
     * the new Screen.
     * @param fromScreen Enum representation of what screen
     *                   is active at the moment.
     * @param toScreen Enum representation of what screen
     *                 that should be switched to.
     * @return Will return the instance of the needed Screen.
     */
    public Screen getNextScreen(ScreenState fromScreen, ScreenState toScreen) {
        switch (fromScreen) {
            case MAINMENU:
                // changeInput(new LobbyInputProcessor());
                return null;

            case LOBBY:
                // changeInput(new PlacementInputProcessor());
                return null;

            case PLACEMENT:
                // changeInput(new InGameInputProcessor());
                return null;

            case INGAME:
                // changeInput(new MainMenuInputProcessor());
                return null;
        }

        // activeInputProcessor = new MainMenuInputProcessor();
        return null;
    }

    /**
     * Sets new InputProcessor.
     * @param inp is the new InputProcessor that will be set.
     */
    public void changeInput(InputProcessor inp) {
        this.activeInputProcessor = inp;
        Gdx.input.setInputProcessor(activeInputProcessor);
    }

    public void changeScreen(AbstractScreen screen) {
        this.screen = screen;
        this.sankossGame.setScreen(this.screen);
    }

    // TODO: Remove this method
    /**
     *  Screen updater for testing.
     *  If F5 is pressed in the InputProcessor we'll change to next Screen.
     */
    public boolean updateNow() {
        if(activeInputProcessor.keyDown(Input.Keys.F5)) {
            return true;
        }

        return false;
    }

}
