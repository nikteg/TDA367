package edu.chalmers.sankoss.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.mvc.AbstractModel;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;
import edu.chalmers.sankoss.desktop.mvc.credits.CreditsModel;
import edu.chalmers.sankoss.desktop.mvc.credits.CreditsRenderer;
import edu.chalmers.sankoss.desktop.mvc.credits.CreditsScreen;
import edu.chalmers.sankoss.desktop.mvc.game.GameModel;
import edu.chalmers.sankoss.desktop.mvc.game.GameRenderer;
import edu.chalmers.sankoss.desktop.mvc.game.GameScreen;
import edu.chalmers.sankoss.desktop.mvc.lobby.LobbyModel;
import edu.chalmers.sankoss.desktop.mvc.lobby.LobbyRenderer;
import edu.chalmers.sankoss.desktop.mvc.lobby.LobbyScreen;
import edu.chalmers.sankoss.desktop.mvc.mainMenu.MainMenuModel;
import edu.chalmers.sankoss.desktop.mvc.mainMenu.MainMenuRenderer;
import edu.chalmers.sankoss.desktop.mvc.mainMenu.MainMenuScreen;
import edu.chalmers.sankoss.desktop.mvc.placement.PlacementModel;
import edu.chalmers.sankoss.desktop.mvc.placement.PlacementRenderer;
import edu.chalmers.sankoss.desktop.mvc.placement.PlacementScreen;
import edu.chalmers.sankoss.desktop.mvc.waiting.WaitingModel;
import edu.chalmers.sankoss.desktop.mvc.waiting.WaitingRenderer;
import edu.chalmers.sankoss.desktop.mvc.waiting.WaitingScreen;
import edu.chalmers.sankoss.desktop.utils.Common;

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

	private SankossGame() {
	}

	public static SankossGame getInstance() {
		return instance;
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
	 * Create method which is called automatically upon creation. The method
	 * creates a new controller for the application.
	 * */
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		client = new SankossClient();

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
		Common.getSkin().dispose();

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
