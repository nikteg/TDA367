package edu.chalmers.sankoss.java2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java2.screens.LobbyScreen;
import edu.chalmers.sankoss.java2.screens.MainMenuScreen;

/**
 * Created by nikteg on 15/05/14.
 */
public class ScreenManager {

    //private Logger logger = new Logger("ScreenManager", Logger.DEBUG);
    private static ScreenManager instance = new ScreenManager();
    private Game game;

    private ScreenManager() {

    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public static ScreenManager getInstance() {
        return instance;
    }


}
