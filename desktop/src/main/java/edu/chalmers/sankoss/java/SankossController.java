package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.screens.InGameScreen;
import edu.chalmers.sankoss.java.screens.LobbyScreen;
import edu.chalmers.sankoss.java.screens.MainMenuScreen;
import edu.chalmers.sankoss.java.screens.PlacementScreen;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class SankossController {
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


    public SankossController(SankossGame sankossGame) {
        this.sankossGame = sankossGame;
        inGameScreen = new InGameScreen(this, sankossGame);
        placementScreen = new PlacementScreen(this, sankossGame);
        mainMenuScreen = new MainMenuScreen(this, sankossGame);
        lobbyScreen = new LobbyScreen(this, sankossGame);
    }

    public Screen getNextScreen(CurrentScreen screen) {
        switch (screen) {
            case MAINMENU:
                return lobbyScreen;

            case LOBBY:
                return placementScreen;

            case PLACEMENT:
                return inGameScreen;

            case INGAME:
                return mainMenuScreen;
        }

        return new MainMenuScreen(this, sankossGame);
    }



}
