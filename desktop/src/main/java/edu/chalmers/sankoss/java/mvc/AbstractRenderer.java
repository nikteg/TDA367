package edu.chalmers.sankoss.java.mvc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Observable;
import java.util.Observer;

/**
 * Abstraction for drawing/rendering.
 * Renders the current Screenmodel
 * (MainMenuModel/LobbyModel/PlacementModel/Ingame) on the Screen.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public abstract class AbstractRenderer implements Observer {
    private Stage stage = new Stage(new ExtendViewport(800, 600)); // TODO Window size constant?
    private Table table = new Table();
    private SpriteBatch spriteBatch = new SpriteBatch();

    public AbstractRenderer(Observable observable) {
        observable.addObserver(this);

        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
    }

    public Table getTable() {
        return table;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    /**
     * Render method to be implemented by each
     * AbstractRenderer.
     * Will be called on, when it's time to update
     * the view of the application.
     */
    public abstract void render(float delta);

    public void dispose() {
        stage.dispose();
    }

    @Override
    public abstract void update(Observable object, Object arg);
}
