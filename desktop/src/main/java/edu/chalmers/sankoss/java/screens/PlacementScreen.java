package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.SnapshotArray;
import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.core.exceptions.IllegalShipCoordinatesException;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.misc.ShipButton;
import edu.chalmers.sankoss.java.models.Placement;
import edu.chalmers.sankoss.java.renderers.PlacementRenderer;

import java.util.HashSet;

/**
 * Screen used when placing the ships.
 * Handles game logic when placing ships, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @modified Fredrik Thune
 * @date 3/24/14
 * @modified Daniel Eineving 2014-05-12
 */
public class PlacementScreen extends AbstractScreen {

    private final int GRID_SIDE=10;
    private final int GRID_TILE_SIDE=45;

    // Controllers
    private Label nameLabel;

    //Containers
    private WidgetGroup gridPanel;

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public PlacementScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new Placement();
        model.getClient().addListener(new PlacementListener());
        renderer = new PlacementRenderer(model);

        create();
    }

    private class PlacementListener extends SankossClientListener {
        @Override
        public void gameReady() {
            System.out.println("SERVER: Game is ready!");
        }

        @Override
        public void playerIsReady(BasePlayer player) {
            System.out.println("SERVER: " + model.getClient().getPlayer().getName() + " is ready!");
            ((Placement)model).setReadyBtnState(Placement.ReadyBtnState.ENTER);
            ((PlacementRenderer)renderer).setReadyBtn(Placement.ReadyBtnState.ENTER);
        }
    }

    /**
     * @inheritdoc
     */
    @Override
    public void show() {

        // Sets the stage as input source
        controller.changeInput(stage);

        nameLabel.setText(model.getClient().getPlayer().getName());
    }

    /**
     * @inheritdoc
     */
    @Override
    public void hide() {
        if(stage.getRoot().hasChildren()) {
            stage.getRoot().clearChildren();
        }

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
        renderer.drawControllers(this);

        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        // Adds font to skin
        skin.add("default", font);

        createKey(2);
        createKey(3);
        createKey(4);
        createKey(5);

        labelStyle.font = skin.getFont("default");

        // Label better of here than in Renderer cause of the model, client, player name
        nameLabel = new Label(model.getClient().getPlayer().getName(), labelStyle);
        nameLabel.setX(10);
        nameLabel.setY(110);

        ((PlacementRenderer) renderer).getPlayerTable().addActor(nameLabel);

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
        /*stage.addActor(((PlacementRenderer) renderer).getPlayerTable());
        stage.addActor(((PlacementRenderer) renderer).getTopTable());*/
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

    @Override
    public void render() {

    }

    public NextBtnListener getNextBtnListener() {
        return new NextBtnListener();
    }

    public BackBtnListener getBackBtnListener() {
        return new BackBtnListener();
    }

    public ReadyBtnListener getReadyBtnListener() {
        return new ReadyBtnListener();
    }

    public ShipBtnListener getShipBtnListener() {
        return new ShipBtnListener();
    }

    public Ship2Listener getShip2Listener() {
        return new Ship2Listener();
    }

    public RotateBtnListener getRotateBtnListener() {
        return new RotateBtnListener();
    }


    public class RotateBtnListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            rotateShips();
        }
    }

    public class Ship2Listener extends  ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            followCursor(actor);
        }
    }

    private class ShipBtnListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            placeShip(actor);
        }
    }

    private class ReadyBtnListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            readyClicked();
        }
    }

    private class NextBtnListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            nextFlag();
        }
    }


    private class BackBtnListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            previousFlag();
        }
    }

    /**
     * Method for switching to next nationality. (flag and country name)
     */
    public void nextFlag() {
        ((PlacementRenderer) renderer).getPlayerTable().removeActor(((PlacementRenderer) renderer).getFlag());
        ((PlacementRenderer) renderer).getPlayerTable().removeActor(((PlacementRenderer) renderer).getLandLabel());
        ((PlacementRenderer) renderer).switchNationality(model.getClient().getPlayer(), true);

        ((PlacementRenderer) renderer).setNationality(model.getClient().getPlayer().getNationality());
        ((PlacementRenderer) renderer).setFlag();
    }

    /**
     * Method for switching to previous nationality. (flag and country name)
     */
    public void previousFlag() {
        ((PlacementRenderer) renderer).getPlayerTable().removeActor(((PlacementRenderer) renderer).getFlag());
        ((PlacementRenderer) renderer).getPlayerTable().removeActor(((PlacementRenderer) renderer).getLandLabel());
        ((PlacementRenderer) renderer).switchNationality(model.getClient().getPlayer(), false);

        ((PlacementRenderer) renderer).setNationality(model.getClient().getPlayer().getNationality());
        ((PlacementRenderer) renderer).setFlag();
    }

    public void enterGame() {
        // This means your opponent is done and you can enter game directly
        controller.changeScreen(new GameScreen(controller, game, ((Placement)model).getShipMap(), ((Placement)model).getRotationMap()));
    }

    public void setReady() {
        System.out.println("CLIENT: You are ready with " + model.getNumberOfShips() + " ships on the board!");

        // Switches state of ready button
        ((Placement)model).switchReadyBtnState();
        ((PlacementRenderer)renderer).setReadyBtn(((Placement) model).getReadyBtnState());
    }

    /**
     * Determines ready or not, and tells server if so.
     */
    public void determineReady() {
        // Tells server that you are ready if necessary
        if(!model.getClient().getReady()) {
            model.getClient().playerReady(model.getClient().getGameID(), model.getClient().getPlayer().getFleet());
            model.getClient().setReady(true);
        }
    }

    public void setReadyOrEnter() {
        // You're done placing you're ships but opponent isn't done
        if(((Placement)model).getReadyBtnState() == Placement.ReadyBtnState.READY) {
            setReady();

        } else if(((Placement)model).getReadyBtnState() == Placement.ReadyBtnState.ENTER){
            enterGame();

        }
    }

    public void readyClicked() {

        // If all your boats are on the board
        if(model.getClient().getPlayer().getFleet().getShips().size() == model.getNumberOfShips()){
            determineReady();
            setReadyOrEnter();

        } else {
            // If you haven't placed all your ships
            System.out.println("Cannot enter game until all " + model.getNumberOfShips() + " ships are placed on the board!");
        }
    }

    /**
     * Creates new key in ShipMap, in model
     * to place Set of coordinates in.
     * @param length length of ship.
     */
    public void createKey(int length) {
        ((Placement)model).getShipMap().put(length, new HashSet<Coordinate>());
    }

    /**
     * Method for adding a coordinate to a specific key based on length.
     * @param length length of ship.
     * @param coordinate coordinate to be added.
     */
    public void addCoordinateToShipMap(int length, Coordinate coordinate) {
        ((Placement)model).getShipMap().get(length).add(coordinate);
        System.out.println("X: " + coordinate.getX() + ". Y: " + coordinate.getY() + ". Added to " + length);
    }

    public void addToRotationMap(Coordinate coordinate, ShipButton.Direction direction) {
        ((Placement)model).getRotationMap().put(coordinate, direction);
    }

    // TODO: Refactor this method ASAP
    /**
     * Method for placing ship on player game board.
     * @param actor is the clicked square in grid.
     */
    public void placeShip(Actor actor) {
        // Checks if a follow-button exists
        if(((PlacementRenderer)renderer).getFollow() != null){


            // Loops through grid
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++){

                    // gets array of 1 button from every table in grid (1 table per square in grid)
                    SnapshotArray<Actor> children = ((PlacementRenderer)renderer).getGrid()[(i*10)+j].getChildren();
                    Actor[] childrenArray = children.toArray();

                    if(childrenArray.length > 0){
                        // Matches button with clicked one
                        if(childrenArray[0].equals(actor)) {

                            // Checks if it's available for ships
                            boolean free = true;

                            // If ship is horizontal
                            if(((PlacementRenderer) renderer).getFollow().getDirection() == ShipButton.Direction.HORIZONTAL
                                    && j + (((PlacementRenderer) renderer).getFollow().getLength()-1) <= 9) {


                                // Runs through array in model to check if spots are free
                                for(int n = 0; n < ((PlacementRenderer)renderer).getFollow().getLength(); n++) {
                                    if(model.getShipArray()[((i)*10)+j+n] == 1) {
                                        free = false;
                                    }
                                }

                                // If spots are free
                                if(free){

                                    // Sets start and end coordinate
                                    Coordinate start = new Coordinate((i+1), (j+1));
                                    Coordinate end = new Coordinate((i+1), (j +((PlacementRenderer)renderer).getFollow().getLength()));

                                    // Creates new key in ShipMap for a set of coordinates
                                    //createKey(end.getY() - start.getY() + 1);

                                    // Adds ships with start and end coordinate to player's fleet
                                    try {
                                        model.getClient().getPlayer().getFleet().add(new Ship(start, end));
                                    } catch (IllegalShipCoordinatesException e) {
                                        System.out.println("ERROR: Cannot place ship at give coordinates!");
                                        e.getStackTrace();
                                    }

                                    // Path to ship texture
                                    String path = "assets/textures/HORIZONTAL_";

                                    // Adds ship to grid visually
                                    System.out.println("Added ship at: ");
                                    for(int n = 0; n < ((PlacementRenderer)renderer).getFollow().getLength(); n++) {
                                        children = ((PlacementRenderer)renderer).getGrid()[((i)*10)+j+n].getChildren();

                                        // Add to coordinateMap and rotationMap
                                        addCoordinateToShipMap(end.getY() - start.getY() + 1, new Coordinate(i + 1, j + 1 + n));
                                        addToRotationMap(new Coordinate(i + 1, j + 1 + n), ShipButton.Direction.HORIZONTAL);

                                        if(((PlacementRenderer)renderer).getFollow().getLength() == 2) {
                                            path = path + "ship_small_body_" + (n+1) + ".png";

                                        } else if(((PlacementRenderer)renderer).getFollow().getLength() == 3) {
                                            path = path + "ship_medium_body_" + (n+1) + ".png";

                                        } else if(((PlacementRenderer)renderer).getFollow().getLength() == 4) {
                                            path = path + "ship_large_body_" + (n+1) + ".png";

                                        } else if(((PlacementRenderer)renderer).getFollow().getLength() == 5) {
                                            path = path + "ship_huge_body_" + (n+1) + ".png";
                                        }

                                        childrenArray = children.toArray();
                                        ((ImageButton)childrenArray[0]).setStyle(new ImageButton.ImageButtonStyle(null, null, null, new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath(path)))), null, null));
                                        System.out.println(i + ", " + (j+n));
                                        path = "assets/textures/HORIZONTAL_";

                                        // Marks the select coordinate as occupied
                                        model.addToShipArray(i, (j+n));

                                        //TODO WHY??
                                        // Adds to players fleet
                                        //model.getClient().getPlayer().addUsedCoordiante(new Coordinate((i + 1), (j + n + 1)));
                                    }

                                    // Removes placed ship from ship panel
                                    ((PlacementRenderer)renderer).getTopTable().removeActor(((PlacementRenderer)renderer).getFollow());
                                    ((PlacementRenderer)renderer).setFollow(null);
                                }

                                // If ship is vertical
                            } else if(((PlacementRenderer) renderer).getFollow().getDirection() == ShipButton.Direction.VERTICAL
                                    && i + (((PlacementRenderer) renderer).getFollow().getLength()-1) <= 9) {

                                // Runs through array in model to check if spots are free
                                for(int n = 0; n < ((PlacementRenderer)renderer).getFollow().getLength(); n++) {
                                    if(model.getShipArray()[((i + n)*10)+j] == 1) {
                                        free = false;
                                    }
                                }

                                // If spots are free
                                if(free){

                                    // Sets start and end coordinate
                                    Coordinate start = new Coordinate((i+1), (j+1));
                                    Coordinate end = new Coordinate((i+(((PlacementRenderer)renderer).getFollow().getLength())), (j +1));

                                            //TODO Why???
                                            // Adds to players fleet
                                            //model.getClient().getPlayer().addUsedCoordiante(new Coordinate((i + n + 1), (j + 1)));
                                    // Creates new key in ShipMap for a set of coordinates
                                    createKey(end.getX() - start.getX() + 1);

                                    // Adds ships with start and end coordinate to player's fleet
                                    try {
                                        model.getClient().getPlayer().getFleet().add(new Ship(start, end));
                                    } catch (IllegalShipCoordinatesException e) {
                                        System.out.println("ERROR: Cannot place ship at give coordinates!");
                                        e.getStackTrace();
                                    }

                                    String path = "assets/textures/VERTICAL_";

                                    // Adds ship to grid visually
                                    System.out.println("Added ship at: ");
                                    for(int n = 0; n < ((PlacementRenderer)renderer).getFollow().getLength(); n++) {
                                        children = ((PlacementRenderer)renderer).getGrid()[((i + n)*10)+j].getChildren();

                                        // Add to coordinateMap and rotationMap
                                        addCoordinateToShipMap(end.getX() - start.getX() + 1, new Coordinate(i + 1 + n, j + 1));
                                        addToRotationMap(new Coordinate(i + 1 + n, j + 1), ShipButton.Direction.VERTICAL);

                                        if(((PlacementRenderer)renderer).getFollow().getLength() == 2) {
                                            path = path + "ship_small_body_" + (n+1) + ".png";

                                        } else if(((PlacementRenderer)renderer).getFollow().getLength() == 3) {
                                            path = path + "ship_medium_body_" + (n+1) + ".png";

                                        } else if(((PlacementRenderer)renderer).getFollow().getLength() == 4) {
                                            path = path + "ship_large_body_" + (n+1) + ".png";

                                        } else if(((PlacementRenderer)renderer).getFollow().getLength() == 5) {
                                            path = path + "ship_huge_body_" + (n+1) + ".png";
                                        }

                                        childrenArray = children.toArray();
                                        ((ImageButton)childrenArray[0]).setStyle(new ImageButton.ImageButtonStyle(null, null, null, new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath(path)))), null, null));
                                        System.out.println((i+n) + 1 + ", " + (j+1));
                                        path = "assets/textures/VERTICAL_";

                                        // Marks the select coordinate as occupied
                                        model.addToShipArray((i+n), j);

                                        //TODO WHY?
                                        // Adds to players fleet
                                        //model.getClient().getPlayer().addUsedCoordiante(new Coordinate((i + n + 1), (j + 1)));
                                    }

                                    // Removes placed ship from ship panel
                                    ((PlacementRenderer)renderer).getTopTable().removeActor(((PlacementRenderer)renderer).getFollow());
                                    ((PlacementRenderer)renderer).setFollow(null);
                                }


                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * Method for ship following the cursor.
     * @param actor ship that will follow.
     */
    public void followCursor(Actor actor) {
        // If there's a ship following the cursor, place it where it's at
        if(((PlacementRenderer)renderer).getFollow() != null) {
            ((PlacementRenderer)renderer).getFollow().setX(Gdx.input.getX());
            ((PlacementRenderer)renderer).getFollow().setY(((PlacementRenderer)renderer).getFollow().getY()-Gdx.input.getDeltaY());
            ((PlacementRenderer)renderer).setFollow(null);

        } else { // If no ship is following the cursor, let clicked ship follow cursor
            // Type-casting safe, since only ShipButtons has this as listener
            ((PlacementRenderer)renderer).setFollow((ShipButton) actor);

        }
    }

    /**
     * Method for rotating ships.
     */
    public void rotateShips() {
        ((PlacementRenderer)renderer).rotateShips();
    }
}
