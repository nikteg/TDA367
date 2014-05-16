package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import edu.chalmers.sankoss.java.client.SankossClient;

import java.io.IOException;

/**
 * The Game.
 * Upon creation the game will create a new Controller class
 * and use it self as a parameter.
 *
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 3/24/14
 */
public class SankossGame extends Game {

    private static SankossGame instance = new SankossGame();
    private SankossClient client;
    private Skin skin;

    private SankossGame() {
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

    /**
     * Create method which is called automatically upon
     * creation.
     * The method creates a new controller for the application.
     * */
    @Override
    public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        client = new SankossClient("localhost");
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Screens.MAIN_MENU.show();

        try {
            client.connect();
        } catch (IOException e) {
            Gdx.app.debug("SankossGame", "Could not connect to server");
        } finally {
            if (client.isConnected()) {
                Gdx.app.debug("SankossGame", "Connected to localhost");
            }
        }
    }

    public void exitApplication() {
        getClient().disconnect();
        getScreen().dispose();
        getSkin().dispose();

        Gdx.app.exit();
    }
}
