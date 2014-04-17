package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.SnapshotArray;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.GameModel;
import edu.chalmers.sankoss.java.renderers.GameRenderer;

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
    public GameScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new GameModel();
        model.getClient().addListener(new GameListener());
        renderer = new GameRenderer(model);

        create();
    }

    private class GameListener extends SankossClientListener {

        @Override
        public void fireResult(Long gameID, Player target, Coordinate coordinate, boolean hit) {

            if(hit) {
                System.out.println(target.getName() + " was shot at " + coordinate.getX() + ", " + coordinate.getY() + ". HIT!");
            } else {
                System.out.println(target.getName() + " was shot at " + coordinate.getX() + ", " + coordinate.getY() + ". Miss..");
            }
        }

        @Override
        public void destroyedShip(Player player, Ship ship) {
            System.out.println("DESTROYED!!!!");
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

    public ShootBtnListener getShootBtnListener() {
        return new ShootBtnListener();
    }

    private class ShootBtnListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {

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
                        }
                    }
                }
            }

        }
    }
}
