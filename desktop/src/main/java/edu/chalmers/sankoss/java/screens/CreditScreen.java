package edu.chalmers.sankoss.java.screens;

import java.util.prefs.BackingStoreException;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.models.MainMenu;
import edu.chalmers.sankoss.java.renderers.CreditRenderer;
import edu.chalmers.sankoss.java.renderers.MainMenuRenderer;
import edu.chalmers.sankoss.java.client.SankossClientListener;

public class CreditScreen extends AbstractScreen<CreditRenderer> {

	// This constructor have to exist
	public CreditScreen(SankossController controller, SankossGame game) {
		super(controller, game);
		renderer = new CreditRenderer(model);
		
		create();
		renderer.drawControllers(this);
	}

	public BackButtonListener getBackButtonListener() {
		return new BackButtonListener();
	}

	private class BackButtonListener extends ChangeListener {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			jumpToMainMenu();
		}
	}

	@Override
	public void show() {
		// Sets the stage as input source
		controller.changeInput(stage);
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void hide() {
		if (stage.getRoot().hasChildren()) {
			stage.getRoot().clearChildren();
		}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void create() {
		// Defines variables for visuals
		super.create();
		renderer.drawControllers(this);

		stage.addActor(renderer.getActorPanel());
		stage.draw();

	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		renderer.resize(width, height);
	}

	public void jumpToMainMenu() {
		controller.changeScreen(new MainMenuScreen(controller, game));
	}
}
