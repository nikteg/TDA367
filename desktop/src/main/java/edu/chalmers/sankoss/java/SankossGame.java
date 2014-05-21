package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import edu.chalmers.sankoss.java.client.SankossClient;
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
import edu.chalmers.sankoss.java.mvc.AbstractModel;
import edu.chalmers.sankoss.java.mvc.AbstractRenderer;
import edu.chalmers.sankoss.java.mvc.AbstractScreen;
import edu.chalmers.sankoss.java.placement.PlacementModel;
import edu.chalmers.sankoss.java.placement.PlacementRenderer;
import edu.chalmers.sankoss.java.placement.PlacementScreen;
import edu.chalmers.sankoss.java.waiting.WaitingModel;
import edu.chalmers.sankoss.java.waiting.WaitingRenderer;
import edu.chalmers.sankoss.java.waiting.WaitingScreen;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

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
	 * Create method which is called automatically upon creation. The method
	 * creates a new controller for the application.
	 * */
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		client = new SankossClient();
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		Screens.MAIN_MENU.show();

		try {
			client.connect(Settings.HOSTNAME);
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

	private enum Screens {
		MAIN_MENU (new MainMenuScreen(MainMenuModel.class, MainMenuRenderer.class)), 
		LOBBY (new LobbyScreen(LobbyModel.class, LobbyRenderer.class)), 
		WAITING (new WaitingScreen(WaitingModel.class, WaitingRenderer.class)), 
		PLACEMENT (new PlacementScreen(PlacementModel.class, PlacementRenderer.class)), 
		GAME (new GameScreen(GameModel.class, GameRenderer.class)), 
		CREDITS (new CreditsScreen(CreditsModel.class, CreditsRenderer.class));

		private AbstractScreen screen;

		Screens(AbstractScreen screen) {
			this.screen = screen;
			this.screen.addPropertyChangeListener(SankossGame.getInstance());
		}

		public void show() {
			SankossGame.getInstance().setScreen(screen);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("showMainMenu")){
			
			Screens.MAIN_MENU.show();
		}
		else if(evt.getPropertyName().equals("showLobby")){
			Screens.LOBBY.show();
		}
		else if(evt.getPropertyName().equals("showWaiting")){
			Screens.WAITING.show();
		}
		else if(evt.getPropertyName().equals("showPlacement")){
			Screens.PLACEMENT.show();
		}
		else if(evt.getPropertyName().equals("showGame")){
			Screens.GAME.show();
		}
		else if(evt.getPropertyName().equals("showCredits")){
			Screens.CREDITS.show();
		}
	}
}
