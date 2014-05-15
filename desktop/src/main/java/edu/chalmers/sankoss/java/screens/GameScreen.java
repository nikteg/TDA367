package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.SnapshotArray;
import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.misc.ShipButton;
import edu.chalmers.sankoss.java.models.GameModel;
import edu.chalmers.sankoss.java.renderers.GameRenderer;

import java.util.Map;
import java.util.Set;

/**
 * Screen used when placing the ships.
 * Handles game logic when placing ships, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class GameScreen extends AbstractScreen {

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
    public GameScreen(SankossController controller, SankossGame game, Map<Integer, Set<Coordinate>> shipMap, Map<Coordinate, ShipButton.Direction> rotationMap) {
        super(controller, game);
        model = new GameModel(shipMap, rotationMap);
        model.getClient().addListener(new GameListener());
        renderer = new GameRenderer(model);

        create();
    }

    public void hit(Coordinate coordinate) {
        // TODO Determine if you was hit or if opponent was hit
        ((GameModel) model).setHitOrMiss(coordinate.getX(), coordinate.getY(), "HIT");

    }

    public void miss(Coordinate coordinate) {
        ((GameModel) model).setHitOrMiss(coordinate.getX(), coordinate.getY(), "MISS");
    }

    private class GameListener extends SankossClientListener {

        @Override
        public void fireResult(Long gameID, BasePlayer target, Coordinate coordinate, boolean hit) {

            // TODO: Put most logic in here instead and call external methods
            if(target.equals(model.getClient().getPlayer())) {
                //Shows the enemy's hit on your board      	
            	((GameRenderer)renderer).setEnemyTarget(coordinate, hit);
            	
                ((GameRenderer)renderer).getYourTurnLabel().setText("Your Turn!");
                ((GameRenderer)renderer).getOppTurnLabel().setText("");
                model.getClient().getPlayer().setMyTurn(true);

            } else {
                ((GameRenderer)renderer).getYourTurnLabel().setText("");
                ((GameRenderer)renderer).getOppTurnLabel().setText(model.getClient().getOpponents().get(0).getName() + "'s turn!");
            }

            if(hit && !target.equals(model.getClient().getPlayer())) {
                //System.out.println("You shot at " + coordinate.getX() + ", " + coordinate.getY() + ". HIT!");
                hit(coordinate);


            } else if(!target.equals(model.getClient().getPlayer())){

                //System.out.println("You shot at " + coordinate.getX() + ", " + coordinate.getY() + ". Miss..");
                miss(coordinate);
            }

        }

        @Override
        public void destroyedShip(BasePlayer player, Ship ship) {
            System.out.println("Ship size " + ship.getSize() + " belonging to " + player.getName() + " has been destroyed!!!!");

            updateShipsDestroyed(player);
            ((GameModel)model).updateGameOverStatus();
            checkGameOver();

        }
    }

    /**
     * Method for checking if game is over.
     * The model determines this.
     */
    public void checkGameOver() {
        if(((GameModel)model).getGameOver()){
            gameIsOver();
        }
    }

    /**
     * Method for updating how many ships has been destroyed.
     * This method is only run when a ship has been destroyed.
     * @param player the player the destroyed ship belongs to.
     */
    public void updateShipsDestroyed(BasePlayer player) {
        ((GameModel)model).incrementShipsDestroyed(player);
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

        ((GameRenderer) renderer).getPlayerTable().addActor(nameLabel);
        ((GameRenderer) renderer).getOpponentNameLabel().setText(model.getClient().getOpponents().get(0).getName());
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

    public void gameIsOver() {
        System.out.println("Game is Over");

        // If you are the loser
        if(((GameModel)model).getLoser().equals(model.getClient().getPlayer())) {
            // TODO: Implement you are loser
            ((GameRenderer)renderer).getYourTurnLabel().setText("You lost!");
            ((GameRenderer)renderer).getOppTurnLabel().setText("");
        } else {
            // TODO: Implement you are winner
            ((GameRenderer)renderer).getYourTurnLabel().setText("You won!");
            ((GameRenderer)renderer).getOppTurnLabel().setText("");
        }

        // Disables shooting
        model.getClient().getPlayer().setMyTurn(false);
    }

    public ShootBtnListener getShootBtnListener() {
        return new ShootBtnListener();
    }

    private class ShootBtnListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            shoot(actor);
        }
    }
    
    private class EnemyShootListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            shoot(actor);
        }
    }

    /**
     * Method for shooting at a grid in the aim grid.
     * @param actor grid to be shot at.
     */
    public void shoot(Actor actor) {

        if(model.getClient().getPlayer().getMyTurn()){
            // Loops through grid
            for(int i = 0; i < 10; i++) {
                for(int j = 0; j < 10; j++){

                    // gets array of 1 button from every table in grid (1 table per square in grid)
                    SnapshotArray<Actor> children = ((GameRenderer)renderer).getAimGrid()[(i*10)+j].getChildren();
                    Actor[] childrenArray = children.toArray();

                    if(childrenArray.length > 0){
                        // Matches button with clicked one
                        if(childrenArray[0].equals(actor)) {
                            model.getClient().fire(model.getClient().getGameID(), model.getClient().getOpponents().get(0), new Coordinate(i+1, j+1));

                            System.out.println("RUN");
                            // Disables button, so player can't shoot there again
                            actor.removeListener(getShootBtnListener());

                            // Delay to get thread to catch up
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.getStackTrace();
                            }

                            ((GameRenderer)renderer).setHitOrMiss(((GameModel)model).getX(), ((GameModel)model).getY(), ((GameModel)model).getHitMsg());
                            model.getClient().getPlayer().setMyTurn(false);
                        }
                    }
                }
            }



        }

    }
}
