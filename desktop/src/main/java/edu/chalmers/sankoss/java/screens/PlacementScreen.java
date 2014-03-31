package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.ApplicationListener;
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

    private Placement placement;
    private PlacementRenderer placementRenderer;
    private SankossGame game;
    private SankossController controller;

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
        placement = new Placement();
        placementRenderer = new PlacementRenderer(placement);
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
