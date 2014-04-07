package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.Models.Placement;
import edu.chalmers.sankoss.java.Renderers.PlacementRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;

import java.util.List;
import java.util.Map;

/**
 * Screen used when placing the ships.
 * Handles game logic when placing ships, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class PlacementScreen extends AbstractScreen implements SankossClientListener {

	private final int GRID_SIDE=10;
	private final int GRID_TILE_SIDE=45;

    // Controllers
    private TextButton nextBtn;
    private TextButton backBtn;
    private Label nameLabel;

	//Containers
	private WidgetGroup gridPanel;
    // private Table playerTable;
    // private Table flag;

	/**
	 * This will keep a reference of the main game.
	 * @param game reference to the SankossGame class
	 * @param controller reference to the SankossController class
	 */
	public PlacementScreen(SankossController controller, SankossGame game) {
		super(controller, game);
        model = new Placement();
        model.getClient().addListener(this);
        renderer = new PlacementRenderer(model);

		create();
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void show() {

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
        renderer.drawStaticControllers();

        // Adds font to skin

        btnStyle = new TextButton.TextButtonStyle();

        // Configures necessary attributes for buttons
        setButtons();

        // Sets the stage as input source
        controller.changeInput(stage);

        btnStyle.font = skin.getFont("default");

        nextBtn = new TextButton(">", btnStyle);
        nextBtn.setX(160);
        nextBtn.setY(0);
        nextBtn.setHeight(30);
        backBtn = new TextButton("<", btnStyle);
        backBtn.setX(10);
        backBtn.setY(0);
        backBtn.setHeight(30);

        labelStyle.font = skin.getFont("default");

        // Label better of here than in Renderer cause of the model, client, player name
        nameLabel = new Label(model.getClient().getPlayer().getName(), labelStyle);
        nameLabel.setX(10);
        nameLabel.setY(110);

        ((PlacementRenderer) renderer).getPlayerTable().addActor(nameLabel);
        ((PlacementRenderer) renderer).getPlayerTable().addActor(backBtn);
        ((PlacementRenderer) renderer).getPlayerTable().addActor(nextBtn);

		//TODO I am testing
        /*Pixmap pixmap = new Pixmap(new FileHandle("src/main/java/edu/chalmers/sankoss/java/texures/testSquare.png"));
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

		stage.addActor(gridPanel);*/
        stage.addActor(((PlacementRenderer) renderer).getPlayerTable());
		stage.draw();

        nextBtn.addListener(new NextBtnListener());
        backBtn.addListener(new BackBtnListener());
		
		
	}

    /**
     * Makes default configuration for a menu button.
     * Sets Pixmap, Skin, BitmapFont and btnStyle.
     */
    public void setButtons() {
        Pixmap pixmap = new Pixmap(20, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();

        // Adds Texture with pixmap to skin
        skin.add("white", new Texture(pixmap));

        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        // Adds font to skin
        skin.add("default", font);

        // Configures how the Style of a button should behave and
        // names is "white"
        btnStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        skin.add("default", btnStyle);

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

    @Override
    public void connected(Long playerID) {

    }

    @Override
    public void fetchedRooms(Map<Long, Room> rooms) {

    }

    @Override
    public void createdRoom(Long roomID) {

    }

    @Override
    public void joinedRoom(Player player) {

    }

    @Override
    public void startedGame(Long gameID, List<Player> players) {

    }

    @Override
    public void gameReady() {

    }

    @Override
    public void playerIsReady(Player player) {

    }

    @Override
    public void turn() {

    }

    @Override
    public void fireResult(Long gameID, Player target, Coordinate coordinate, boolean hit) {

    }

    @Override
    public void destroyedShip(Player player, Ship ship) {

    }

    @Override
    public void disconnected() {

    }

    private class NextBtnListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {

            //TODO: Switch flag
            ((PlacementRenderer) renderer).getPlayerTable().removeActor(((PlacementRenderer) renderer).getFlag());
            ((PlacementRenderer) renderer).getPlayerTable().removeActor(((PlacementRenderer) renderer).getLandLabel());
            ((PlacementRenderer) renderer).switchNationality(model.getClient().getPlayer(), true);
            ((PlacementRenderer) renderer).setFlag();
        }
    }

    private class BackBtnListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {

            //TODO: Switch flag
            ((PlacementRenderer) renderer).getPlayerTable().removeActor(((PlacementRenderer) renderer).getFlag());
            ((PlacementRenderer) renderer).getPlayerTable().removeActor(((PlacementRenderer) renderer).getLandLabel());
            ((PlacementRenderer) renderer).switchNationality(model.getClient().getPlayer(), false);
            ((PlacementRenderer) renderer).setFlag();
        }
    }
}
