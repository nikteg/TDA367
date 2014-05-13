package edu.chalmers.sankoss.java.screens;

import java.util.prefs.BackingStoreException;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.models.Credit;
import edu.chalmers.sankoss.java.models.MainMenu;
import edu.chalmers.sankoss.java.renderers.CreditRenderer;
import edu.chalmers.sankoss.java.client.SankossClientListener;

public class CreditScreen extends AbstractScreen{

	private MainMenuScreen mmScreen;

	//This constructor have to exist
	public CreditScreen(SankossController controller, SankossGame game) {
		super(controller, game);
		model = new Credit();
		renderer = new CreditRenderer(model);

	}
	public CreditScreen(SankossController controller, SankossGame game, MainMenuScreen mmScreen) {
		this(controller, game);
		this.mmScreen = mmScreen;


	}
	
	public BackButtonListener getBackButtonListener(){
		return new BackButtonListener();
	}
	
	private class CreditScreenListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			// TODO Auto-generated method stub

		}
	}
	private class BackButtonListener extends ChangeListener{

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			jumpToMainMenu();
		}
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	public void jumpToMainMenu(){
		controller.changeScreen(mmScreen);
	}

}
