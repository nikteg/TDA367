package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.Models.GameModel;
import edu.chalmers.sankoss.java.Renderers.GameRenderer;
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
        /**
         * This is where we override the methods we want to use
         */
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
        ((GameRenderer) renderer).getOpponentNameLabel().setText(model.getClient().getOpponents().toString());
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
}
