package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Inputs.InGameInputProcessor;
import edu.chalmers.sankoss.java.Inputs.LobbyInputProcessor;
import edu.chalmers.sankoss.java.Inputs.MainMenuInputProcessor;
import edu.chalmers.sankoss.java.Inputs.PlacementInputProcessor;
import edu.chalmers.sankoss.java.screens.InGameScreen;
import edu.chalmers.sankoss.java.screens.LobbyScreen;
import edu.chalmers.sankoss.java.screens.MainMenuScreen;
import edu.chalmers.sankoss.java.screens.PlacementScreen;

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

    // Instance of the started game
    private SankossGame sankossGame;

    // Instances of all screens
    private InGameScreen inGameScreen;
    private PlacementScreen placementScreen;
    private MainMenuScreen mainMenuScreen;
    private LobbyScreen lobbyScreen;

    // ENUMS to keep track of the current Screen
    public enum CurrentScreen {
        MAINMENU, LOBBY, PLACEMENT, INGAME
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
        inGameScreen = new InGameScreen(this, sankossGame);
        placementScreen = new PlacementScreen(this, sankossGame);
        mainMenuScreen = new MainMenuScreen(this, sankossGame);
        lobbyScreen = new LobbyScreen(this, sankossGame);
        activeInputProcessor = new MainMenuInputProcessor();


        // Sets the input processor and the main menu screen
        Gdx.input.setInputProcessor(activeInputProcessor);
        this.sankossGame.setScreen(mainMenuScreen);
    }

    /**
     * Method for returning a new Screen and setting correct
     * InputProcessor.
     * This depends on what Screen is active at the moment
     * and uses an Enum of the type CurrentScreen to decide this.
     * The method does also set the InputProcessor depending on
     * the new Screen.
     * @param screen Enum representation of what screen
     *               is active at the moment.
     * @return Will return the instance of the needed Screen.
     */
    public Screen getNextScreen(CurrentScreen screen) {
        switch (screen) {
            case MAINMENU:
                changeInput(new LobbyInputProcessor());
                return lobbyScreen;

            case LOBBY:
                changeInput(new PlacementInputProcessor());
                return placementScreen;

            case PLACEMENT:
                changeInput(new InGameInputProcessor());
                return inGameScreen;

            case INGAME:
                changeInput(new MainMenuInputProcessor());
                return mainMenuScreen;
        }

        activeInputProcessor = new MainMenuInputProcessor();
        return new MainMenuScreen(this, sankossGame);
    }

    /**
     * Sets new InputProcessor.
     * @param inp is the new InputProcessor that will be set.
     */
    public void changeInput(InputProcessor inp) {
        activeInputProcessor = inp;
        Gdx.input.setInputProcessor(activeInputProcessor);
    }

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
