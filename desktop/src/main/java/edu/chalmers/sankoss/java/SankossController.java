package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.esotericsoftware.kryonet.Client;
import edu.chalmers.sankoss.core.SankossClient;
import edu.chalmers.sankoss.core.protocol.CreateRoom;
import edu.chalmers.sankoss.java.Inputs.InGameInputProcessor;
import edu.chalmers.sankoss.java.Inputs.LobbyInputProcessor;
import edu.chalmers.sankoss.java.Inputs.MainMenuInputProcessor;
import edu.chalmers.sankoss.java.Inputs.PlacementInputProcessor;
import edu.chalmers.sankoss.java.Models.*;
import edu.chalmers.sankoss.java.screens.InGameScreen;
import edu.chalmers.sankoss.java.screens.LobbyScreen;
import edu.chalmers.sankoss.java.screens.MainMenuScreen;
import edu.chalmers.sankoss.java.screens.PlacementScreen;
import sun.net.www.content.text.plain;

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
    private InGameScreen inGameScreen;
    private PlacementScreen placementScreen;
    private MainMenuScreen mainMenuScreen;
    private LobbyScreen lobbyScreen;

    // ENUMS to keep track of the current Screen
    public enum ScreenState {
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
        setMainMenuScreen();
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
                return lobbyScreen;

            case LOBBY:
                // changeInput(new PlacementInputProcessor());
                return placementScreen;

            case PLACEMENT:
                // changeInput(new InGameInputProcessor());
                return inGameScreen;

            case INGAME:
                // changeInput(new MainMenuInputProcessor());
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
     * Creates instance of MainMenuScreen.
     * This type of method needs to be available for the Screens.
     */
    public void setMainMenuScreen(){
        this.model = new MainMenu();
        this.mainMenuScreen = new MainMenuScreen(this, sankossGame);
        this.sankossGame.setScreen(mainMenuScreen);
    }

    /**
     * Creates instance of LobbyScreen.
     * This type of method needs to be available for the Screens.
     */
    public void setLobbyScreen(){
        this.model = new Lobby();
        this.lobbyScreen = new LobbyScreen(this, sankossGame);
        this.sankossGame.setScreen(lobbyScreen);
    }

    /**
     * Creates instance of PlacementScreen.
     * This type of method needs to be available for the Screens.
     */
    public void setPlacementScreen(){
        this.model = new Placement();
        this.placementScreen = new PlacementScreen(this, sankossGame);
        this.sankossGame.setScreen(placementScreen);
    }

    /**
     * Creates instance of InGameScreen.
     * This type of method needs to be available for the Screens.
     */
    public void setInGameScreen(){
        this.model = new InGame();
        this.inGameScreen = new InGameScreen(this, sankossGame);
        this.sankossGame.setScreen(inGameScreen);
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
