package edu.chalmers.sankoss.java2;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import edu.chalmers.sankoss.java2.client.SankossClient;
import edu.chalmers.sankoss.java2.screens.LobbyScreen;
import edu.chalmers.sankoss.java2.screens.MainMenuScreen;

import java.io.IOException;

/**
 * Created by nikteg on 15/05/14.
 */
public class SankossGame extends Game {

    private static SankossGame instance = new SankossGame();
    private SankossClient client = new SankossClient();

    private Skin skin;

    private SankossGame() {
        //

    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        ScreenManager.getInstance().setGame(this);

        Screens.MAIN_MENU.show();

        try {
            client.connect("localhost"); // TODO Fix settings manager?
        } catch (IOException e) {
            Gdx.app.debug("SankossGame", "Could not connect to server");
        } finally {
            if (client.isConnected()) {
                Gdx.app.debug("SankossGame", "Connected to localhost");
            }
        }
    }

    public static SankossGame getInstance() {
        return instance;
    }

    public SankossClient getClient() {
        return client;
    }

    public Skin getSkin() {
        return skin;
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);

        Gdx.app.debug("ScreenManager", "Setting screen: " + screen.getClass());
    }

    public enum Screens {
        MAIN_MENU (new MainMenuScreen()),
        LOBBY (new LobbyScreen());

        private Screen screen;

        Screens(Screen screen) {
            this.screen = screen;
        }

        public void show() {
            SankossGame.getInstance().setScreen(screen);
        }
    }
}
