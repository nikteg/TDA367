package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import edu.chalmers.sankoss.java.Models.Placement;
import edu.chalmers.sankoss.java.Renderers.PlacementRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

/**
 * Screen used when placing the ships.
 * Handles game logic when placing ships, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class PlacementScreen extends AbstractScreen implements ApplicationListener {

	private final int GRID_SIDE=10;

	/**
	 * This will keep a reference of the main game.
	 * @param game reference to the SankossGame class
	 * @param controller reference to the SankossController class
	 */
	public PlacementScreen(SankossController controller, SankossGame game) {
		super(controller, game);

		create();
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void show() {
		model = new Placement();
		renderer = new PlacementRenderer(model);
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	/**
	 * Method to run upon creation of instance.
	 * Configs visual controllers and sets listeners.
	 */
	@Override
	public void create() {
		super.create();


		//TODO Testing

		final int BUTTON_SIDE=40;
		/*
		setButtons();
		
		btnStyle.font = skin.getFont("default");
		Stage stage = new Stage(GRID_SIDE,GRID_SIDE, false, batch);
		TextButton[][] gridButton = new TextButton[GRID_SIDE][GRID_SIDE];
		for(int y = 0; y < GRID_SIDE; y++){
			for(int x = 0; x < GRID_SIDE; x++){
				
				gridButton[x][y]= new TextButton("6",btnStyle);
				stage.addActor(gridButton[x][y]); 
			}
		}*/
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {

	}
}
