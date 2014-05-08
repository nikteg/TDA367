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
        //((GameRenderer)renderer).setNationality(model.getClient().getPlayer().getNationality());

        create();
    }

    public void hit(Coordinate coordinate) {
        ((GameModel) model).setHitOrMiss(coordinate.getX(), coordinate.getY(), "HIT");
        //((GameRenderer)renderer).setHitOrMiss((coordinate.getX()-1)*10, coordinate.getY(), "HIT");

    }

    public void miss(Coordinate coordinate) {
        ((GameModel) model).setHitOrMiss(coordinate.getX(), coordinate.getY(), "MISS");
        //((GameRenderer)renderer).setHitOrMiss((coordinate.getX()-1)*10, coordinate.getY(), "MISS");
    }

    private class GameListener extends SankossClientListener {

        @Override
        public void fireResult(Long gameID, BasePlayer target, Coordinate coordinate, boolean hit) {

            if(hit && !target.equals(model.getClient().getPlayer())) {
                System.out.println("You shot at " + coordinate.getX() + ", " + coordinate.getY() + ". HIT!");
                // ((GameRenderer)renderer).getAimGrid()[(coordinate.getX()-1)*10 + (coordinate.getY()-1)].addActor(new TextButton("HIT", ((GameRenderer)renderer).getBtnStyle()));
                //((GameRenderer)renderer).getAimGrid()[(coordinate.getX()-1)*10 + (coordinate.getY()-1)].addActor(new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("desktop/src/main/java/assets/textures/explosion.png"))))));
                //((GameRenderer)renderer).setHitOrMiss((coordinate.getX()-1)*10, coordinate.getY(), "HIT");
                hit(coordinate);

            } else if(!target.equals(model.getClient().getPlayer())){

                System.out.println("You shot at " + coordinate.getX() + ", " + coordinate.getY() + ". Miss..");
                // ((GameRenderer)renderer).getAimGrid()[(coordinate.getX()-1)*10 + (coordinate.getY()-1)].addActor(new TextButton("MISS", ((GameRenderer)renderer).getBtnStyle()));
                //((GameRenderer)renderer).getAimGrid()[(coordinate.getX()-1)*10 + (coordinate.getY()-1)].addActor(new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("desktop/src/main/java/assets/textures/miss.png"))))));
                //((GameRenderer)renderer).setHitOrMiss((coordinate.getX()-1)*10, coordinate.getY(), "MISS");
                miss(coordinate);
            }

        }

        @Override
        public void destroyedShip(BasePlayer player, Ship ship) {
            System.out.println("Ship size " + ship.getSize() + " belonging to " + player.getName() + " has been destroyed!!!!");
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
            shoot(actor);
        }
    }

    /**
     * Method for shooting at a grid in the aim grid.
     * @param actor grid to be shot at.
     */
    public void shoot(Actor actor) {
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

        // Delay to get thread to catch up
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.getStackTrace();
        }

        ((GameRenderer)renderer).setHitOrMiss(((GameModel)model).getX(), ((GameModel)model).getY(), ((GameModel)model).getHitMsg());

    }
}
