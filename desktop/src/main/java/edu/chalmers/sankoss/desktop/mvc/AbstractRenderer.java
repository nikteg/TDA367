package edu.chalmers.sankoss.desktop.mvc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
public abstract class AbstractRenderer<M extends AbstractModel> implements PropertyChangeListener {
    private Stage stage;
    private Table table;
    private SpriteBatch spriteBatch;
    private M model;

    public AbstractRenderer(M model) {
        this.model = model;
        this.model.addPcl(this);

        stage = new Stage(new ExtendViewport(800, 600)); // TODO Window size constant?
        table = new Table();

        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());

        spriteBatch = new SpriteBatch();
    }

    public M getModel() {
        return model;
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
    public abstract void propertyChange(PropertyChangeEvent evt);
}
