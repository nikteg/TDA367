package edu.chalmers.sankoss.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;
import edu.chalmers.sankoss.desktop.mvc.credits.CreditsScreen;
import edu.chalmers.sankoss.desktop.mvc.game.GameScreen;
import edu.chalmers.sankoss.desktop.mvc.lobby.LobbyScreen;
import edu.chalmers.sankoss.desktop.mvc.mainMenu.MainMenuScreen;
import edu.chalmers.sankoss.desktop.mvc.placement.PlacementScreen;
import edu.chalmers.sankoss.desktop.mvc.waiting.WaitingScreen;
import edu.chalmers.sankoss.desktop.utils.Common;

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

	private SankossClient client;

    private Map<String, AbstractScreen> screens = new HashMap<String, AbstractScreen>();

	public SankossGame() {
        /**
         * Do things in create instead...
         */
	}
	public SankossClient getClient() {
		return client;
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

		client = SankossClient.getInstance();
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

		Common.getSkin().dispose();
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
