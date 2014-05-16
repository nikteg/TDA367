package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.screens.*;

/**
 * Created by nikteg on 16/05/14.
 */
public enum Screens {
    MAIN_MENU (new MainMenuScreen()),
    LOBBY (new LobbyScreen()),
    PLACEMENT (new PlacementScreen()),
    GAME (new GameScreen()),
    CREDITS (new CreditScreen());

    private Screen screen;

    Screens(Screen screen) {
        this.screen = screen;
    }

    public void show() {
        SankossGame.getInstance().setScreen(screen);
    }
}