package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import edu.chalmers.sankoss.java.client.SankossClient;
import edu.chalmers.sankoss.java.credits.CreditsScreen;
import edu.chalmers.sankoss.java.game.GameScreen;
import edu.chalmers.sankoss.java.lobby.LobbyScreen;
import edu.chalmers.sankoss.java.mainMenu.MainMenuScreen;
import edu.chalmers.sankoss.java.mvc.AbstractScreen;
import edu.chalmers.sankoss.java.placement.PlacementScreen;
import edu.chalmers.sankoss.java.waitingScreen.WaitingScreen;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The Game. Upon creation the game will create a new Controller class and use
 * it self as a parameter.
 * 
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 3/24/14
 */
public class SankossGame extends Game implements PropertyChangeListener {

	private static SankossGame instance = new SankossGame();
	private SankossClient client;
	private Skin skin;

    private Map<String, AbstractScreen> screens = new HashMap<String, AbstractScreen>();

	private SankossGame() {
        /**
         * Do things in create instead...
         */
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
	 * Create method which is called automatically upon creation.
	 * */
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		client = new SankossClient();
		skin = new Skin(Gdx.files.internal("uiskin.json")); // TODO skall flyttas ut

        /**
         * Create screens
         */
        screens.put("credits", new CreditsScreen());
        screens.put("game", new GameScreen());
        screens.put("lobby", new LobbyScreen());
        screens.put("mainmenu", new MainMenuScreen());
        screens.put("placement", new PlacementScreen());
        screens.put("waiting", new WaitingScreen());

        /**
         * Add the game as a listener to every screen
         */
        for (AbstractScreen screen : screens.values()) {
            screen.addPcl(this);
        }

		setScreen(screens.get("mainmenu"));

		try {
			client.connect(Settings.HOSTNAME);
		} catch (IOException e) {
			Gdx.app.debug("SankossGame", "Could not connect to" + Settings.HOSTNAME + ":" + Settings.PORT);
		} finally {
			if (client.isConnected()) {
				Gdx.app.debug("SankossGame", "Connected to " + Settings.HOSTNAME + ":" + Settings.PORT);
			}
		}
	}

	public void exitApplication() {
		getClient().disconnect();

        for (AbstractScreen screen : screens.values()) {
            screen.dispose();
        }

        getSkin().dispose();

		Gdx.app.exit();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("changescreen")){
            String screen = (String)evt.getNewValue();

            setScreen(screens.get(screen));
		}

        if (evt.getPropertyName().equals("exitgame")) {
            exitApplication();
        }
	}
}
