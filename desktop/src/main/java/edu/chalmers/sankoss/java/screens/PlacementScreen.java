package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.Placement;
import edu.chalmers.sankoss.java.Renderers.PlacementRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class PlacementScreen implements Screen {

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
        this.controller = controller;
        this.game = game;
    }

    /**
     * Game loop for the current Screen.
     * This method loops as long this Screen is active.
     * @param delta
     */
    @Override
    public void render(float delta) {
        game.setScreen(controller.getNextScreen(SankossController.CurrentScreen.PLACEMENT));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        placement = new Placement();
        placementRenderer = new PlacementRenderer(placement);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
