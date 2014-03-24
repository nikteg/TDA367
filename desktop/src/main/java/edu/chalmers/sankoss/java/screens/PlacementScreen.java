package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.Placement;
import edu.chalmers.sankoss.java.Renderers.PlacementRenderer;

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

    @Override
    public void render(float delta) {

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
