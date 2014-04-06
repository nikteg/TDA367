package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Inputs.MainMenuInputProcessor;
import edu.chalmers.sankoss.java.Models.*;
import edu.chalmers.sankoss.java.client.SankossClient;
import edu.chalmers.sankoss.java.screens.*;

import java.io.IOException;

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
    private SankossClient client;
    private ScreenModel model;

    // Instance of the started game
    private SankossGame sankossGame;

    // Instances of all screens
    /*private InGameScreen inGameScreen;
    private PlacementScreen placementScreen;
    private MainMenuScreen mainMenuScreen;
    private LobbyScreen lobbyScreen;*/
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
        //this.client = new SankossClient("localhost");
        setMainMenuScreen();
        this.client = screen.getClient();

        try {
            client.connect();

        } catch (IOException exc) {
            System.out.print("ERROR: Could not connect to " + client.getHost());
            exc.getStackTrace();
        }
        // lobbyScreen = new LobbyScreen(this, sankossGame);
        // activeInputProcessor = new MainMenuInputProcessor();

        // Sets the input processor and the main menu screen
        // Gdx.input.setInputProcessor(activeInputProcessor);
    }

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

        activeInputProcessor = new MainMenuInputProcessor();
        return new MainMenuScreen(this, sankossGame);
    }

    /**
     * Sets new InputProcessor.
     * @param inp is the new InputProcessor that will be set.
     */
    public void changeInput(InputProcessor inp) {
        this.activeInputProcessor = inp;
        Gdx.input.setInputProcessor(activeInputProcessor);
    }

    /**
     * Creates instance of MainMenuScreen.
     * This type of method needs to be available for the Screens.
     */
    public void setMainMenuScreen(){
        this.model = new MainMenu();
        this.screen = new MainMenuScreen(this, sankossGame);
        //this.screen.setClient(client);
        //this.client.addListener(screen);
        this.sankossGame.setScreen(screen);
    }

    /**
     * Creates instance of LobbyScreen.
     * This type of method needs to be available for the Screens.
     */
    public void setLobbyScreen(){
        this.model = new Lobby();
        this.screen = new LobbyScreen(this, sankossGame);
        //this.screen.setClient(client);
        //this.client.addListener(screen);
        ((Lobby) model).setClient(this.client);
        this.sankossGame.setScreen(screen);
    }

    /**
     * Creates instance of PlacementScreen.
     * This type of method needs to be available for the Screens.
     */
    public void setPlacementScreen(){
        this.model = new Placement();
        this.screen = new PlacementScreen(this, sankossGame);
        screen.setClient(client);
        this.sankossGame.setScreen(screen);
    }

    /**
     * Creates instance of InGameScreen.
     * This type of method needs to be available for the Screens.
     */
    public void setInGameScreen(){
        this.model = new InGame();
        this.screen = new InGameScreen(this, sankossGame);
        screen.setClient(client);
        this.sankossGame.setScreen(screen);
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

    public ScreenModel getModel() {
        return this.model;
    }
}
