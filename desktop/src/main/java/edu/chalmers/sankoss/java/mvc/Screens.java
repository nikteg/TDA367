package edu.chalmers.sankoss.java.mvc;

import com.badlogic.gdx.Screen;

import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.credits.CreditsModel;
import edu.chalmers.sankoss.java.credits.CreditsRenderer;
import edu.chalmers.sankoss.java.credits.CreditsScreen;
import edu.chalmers.sankoss.java.game.GameModel;
import edu.chalmers.sankoss.java.game.GameRenderer;
import edu.chalmers.sankoss.java.game.GameScreen;
import edu.chalmers.sankoss.java.lobby.LobbyModel;
import edu.chalmers.sankoss.java.lobby.LobbyRenderer;
import edu.chalmers.sankoss.java.lobby.LobbyScreen;
import edu.chalmers.sankoss.java.mainMenu.MainMenuModel;
import edu.chalmers.sankoss.java.mainMenu.MainMenuRenderer;
import edu.chalmers.sankoss.java.mainMenu.MainMenuScreen;
import edu.chalmers.sankoss.java.placement.PlacementModel;
import edu.chalmers.sankoss.java.placement.PlacementRenderer;
import edu.chalmers.sankoss.java.placement.PlacementScreen;
import edu.chalmers.sankoss.java.waitingScreen.WaitingModel;
import edu.chalmers.sankoss.java.waitingScreen.WaitingRenderer;
import edu.chalmers.sankoss.java.waitingScreen.WaitingScreen;

/**
 * @author Niklas Tegnander
 */
public enum Screens {
    MAIN_MENU (new MainMenuScreen(MainMenuModel.class, MainMenuRenderer.class)),
    LOBBY (new LobbyScreen(LobbyModel.class, LobbyRenderer.class)),
    WAITING (new WaitingScreen(WaitingModel.class, WaitingRenderer.class)),
    PLACEMENT (new PlacementScreen(PlacementModel.class, PlacementRenderer.class)),
    GAME (new GameScreen(GameModel.class, GameRenderer.class)),
    CREDITS (new CreditsScreen(CreditsModel.class, CreditsRenderer.class));

    private Screen screen;

    Screens(Screen screen) {
        this.screen = screen;
    }

    public void show() {
        SankossGame.getInstance().setScreen(screen);
    }
}