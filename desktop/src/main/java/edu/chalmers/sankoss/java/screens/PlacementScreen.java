package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
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
	private final int GRID_TILE_SIDE=45;
	

	//Containers
	private WidgetGroup gridPanel;
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


		//TODO I am testing
				
		Pixmap pixmap = new Pixmap(new FileHandle("src/main/java/edu/chalmers/sankoss/java/texures/testSquare.png"));
		skin.add("gridTile", new Texture(pixmap));
		
		gridPanel= new WidgetGroup();
		
		//Stage stage = new Stage(GRID_SIDE,GRID_SIDE, false, batch);
		ImageButton[][] gridButton = new ImageButton[GRID_SIDE][GRID_SIDE];
		for(int y = 0; y < GRID_SIDE; y++){
			for(int x = 0; x < GRID_SIDE; x++){
				
				System.out.println("Doing stuff");
				gridButton[x][y]= new ImageButton(skin.getDrawable("gridTile"));
				gridButton[x][y].setSize(GRID_TILE_SIDE, GRID_TILE_SIDE);
				gridPanel.addActor(gridButton[x][y]); 
				gridButton[x][y].setPosition(x*GRID_TILE_SIDE, y*GRID_TILE_SIDE);
			}
		}

		stage.addActor(gridPanel);
		stage.draw();

		
		
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
