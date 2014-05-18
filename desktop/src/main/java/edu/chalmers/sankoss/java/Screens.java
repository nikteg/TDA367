package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.models.*;
import edu.chalmers.sankoss.java.renderers.*;
import edu.chalmers.sankoss.java.screens.*;

/**
 * @author Niklas Tegnander
 */
public enum Screens {
    MAIN_MENU (new MainMenuScreen(MainMenuModel.class, MainMenuRenderer.class)),
    LOBBY (new LobbyScreen(LobbyModel.class, LobbyRenderer.class)),
    WAITING (new WaitingScreen(WaitingModel.class, WaitingRenderer.class)),
    PLACEMENT (new PlacementScreen(PlacementModel.class, PlacementRenderer.class)),
    GAME (new GameScreen(GameModel.class, GameRenderer.class)),
    CREDITS (new CreditScreen(CreditModel.class, CreditRenderer.class));

    private Screen screen;

    Screens(Screen screen) {
        this.screen = screen;
    }

    public void show() {
        SankossGame.getInstance().setScreen(screen);
    }
}