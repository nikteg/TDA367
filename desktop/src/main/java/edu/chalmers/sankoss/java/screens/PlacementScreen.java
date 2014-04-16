package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.SnapshotArray;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.core.exceptions.IllegalShipCoordinatesException;
import edu.chalmers.sankoss.java.Models.Placement;
import edu.chalmers.sankoss.java.Renderers.PlacementRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.misc.ShipButton;

/**
 * Screen used when placing the ships.
 * Handles game logic when placing ships, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
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
        public void playerIsReady(Player player) {
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


        // Sets the stage as input source
        controller.changeInput(stage);

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
            ((PlacementRenderer)renderer).rotateShips();
        }
    }

    public class Ship2Listener extends  ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {

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
    }

    private class ShipBtnListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {

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

                                // TODO Checks if it's available for ships
                                boolean free = true;

                                // If ship is horizontal
                                if(((PlacementRenderer) renderer).getFollow().getDirection() == ShipButton.Direction.HORIZONTAL
                                        && j + (((PlacementRenderer) renderer).getFollow().getLength()-1) <= 9) {


                                    // Runs through array in model to check if spots are free
                                    for(int n = 0; n < ((PlacementRenderer)renderer).getFollow().getLength(); n++) {
                                        if(((Placement)model).getShipArray()[((i)*10)+j+n] == 1) {
                                            free = false;
                                        }
                                    }

                                    // If spots are free
                                    if(free){

                                        // Sets start and end coordinate
                                        Coordinate start = new Coordinate((i+1), (j+1));
                                        Coordinate end = new Coordinate((i+1), (j +((PlacementRenderer)renderer).getFollow().getLength()));

                                        // Adds ships with start and end coordinate to player's fleet
                                        try {
                                            model.getClient().getPlayer().getFleet().add(new Ship(start, end));
                                        } catch (IllegalShipCoordinatesException e) {
                                            System.out.println("ERROR: Cannot place ship at give coordinates!");
                                            e.getStackTrace();
                                        }

                                        // Adds ship to grid visually
                                        System.out.println("Added ship at: ");
                                        for(int n = 0; n < ((PlacementRenderer)renderer).getFollow().getLength(); n++) {
                                            children = ((PlacementRenderer)renderer).getGrid()[((i)*10)+j+n].getChildren();

                                            childrenArray = children.toArray();
                                            ((TextButton)childrenArray[0]).setText("XX");
                                            System.out.println(i + ", " + (j+n));

                                            // Marks the select coordinate as occupied
                                            ((Placement)model).addToShipArray(i, (j+n));

                                            // Adds to players fleet
                                            model.getClient().getPlayer().addUsedCoordiante(new Coordinate((i + 1), (j + n + 1)));
                                        }

                                        // Removes placed ship from ship panel
                                        ((PlacementRenderer)renderer).getTopTable().removeActor(((PlacementRenderer)renderer).getFollow());
                                        ((PlacementRenderer)renderer).setFollow(null);
                                    }

                                } else if(((PlacementRenderer) renderer).getFollow().getDirection() == ShipButton.Direction.VERTICAL
                                        && i + (((PlacementRenderer) renderer).getFollow().getLength()-1) <= 9) {

                                    // Runs through array in model to check if spots are free
                                    for(int n = 0; n < ((PlacementRenderer)renderer).getFollow().getLength(); n++) {
                                        if(((Placement)model).getShipArray()[((i + n)*10)+j] == 1) {
                                            free = false;
                                        }
                                    }

                                    // If spots are free
                                    if(free){

                                        // Sets start and end coordinate
                                        Coordinate start = new Coordinate((i+1), (j+1));
                                        Coordinate end = new Coordinate((i+1), (j +((PlacementRenderer)renderer).getFollow().getLength()));

                                        // Adds ships with start and end coordinate to player's fleet
                                        try {
                                            model.getClient().getPlayer().getFleet().add(new Ship(start, end));
                                        } catch (IllegalShipCoordinatesException e) {
                                            System.out.println("ERROR: Cannot place ship at give coordinates!");
                                            e.getStackTrace();
                                        }

                                        // Adds ship to grid visually
                                        System.out.println("Added ship at: ");
                                        for(int n = 0; n < ((PlacementRenderer)renderer).getFollow().getLength(); n++) {
                                            children = ((PlacementRenderer)renderer).getGrid()[((i + n)*10)+j].getChildren();

                                            childrenArray = children.toArray();
                                            ((TextButton)childrenArray[0]).setText("XX");
                                            System.out.println((i+n) + ", " + j);

                                            // Marks the select coordinate as occupied
                                            ((Placement)model).addToShipArray((i+n), j);

                                            // Adds to players fleet
                                            model.getClient().getPlayer().addUsedCoordiante(new Coordinate((i + n + 1), (j + 1)));
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
    }

    private class ReadyBtnListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {

            // If all your boats are on the board
            if(model.getClient().getPlayer().getFleet().size() == model.getNumberOfShips()){


                // Tells server that you are ready if necessary
                if(!model.getClient().getReady()) {
                    model.getClient().playerReady(model.getClient().getGameID(), model.getClient().getPlayer().getFleet());
                    model.getClient().setReady(true);
                }

                // You're done placing you're ships but opponent isn't done
                if(((Placement)model).getReadyBtnState() == Placement.ReadyBtnState.READY) {
                    System.out.println("CLIENT: You are ready with " + model.getNumberOfShips() + " ships on the board!");

                    // Switches state of ready button
                    ((Placement)model).switchReadyBtnState();
                    ((PlacementRenderer)renderer).setReadyBtn(((Placement) model).getReadyBtnState());

                } else if(((Placement)model).getReadyBtnState() == Placement.ReadyBtnState.ENTER){
                    // This means your opponent is done and you can enter game directly
                    controller.changeScreen(new GameScreen(controller, game));

                } else {
                    // This means you're marked as waiting since before, but the opponent is not yet done
                    System.out.println("Waiting for opponent!");
                }


            } else {
                // If you haven't placed all your ships
                System.out.println("Cannot enter game until all " + model.getNumberOfShips() + " ships are placed on the board!");
            }

        }
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
