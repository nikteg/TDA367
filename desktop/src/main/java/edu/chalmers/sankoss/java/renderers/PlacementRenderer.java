package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.util.Observable;

/**
 * AbstractRenderer for the placement of ships.
 * This class will handle all rendering
 * called by the PlacementScreen.
 *
 * @author Mikael Malmqvist
 */
public class PlacementRenderer extends AbstractRenderer {

    public PlacementRenderer(Observable observable) {
        super(observable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);
    }

    @Override
    public void update(Observable object, Object arg) {

    }
}
