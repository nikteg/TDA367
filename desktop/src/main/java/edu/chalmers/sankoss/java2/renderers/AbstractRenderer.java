package edu.chalmers.sankoss.java2.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.beans.PropertyChangeListener;

/**
 * Created by nikteg on 15/05/14.
 */
public abstract class AbstractRenderer implements PropertyChangeListener {

    private Stage stage = new Stage(new ExtendViewport(800, 600));
    private Table table = new Table();
    public SpriteBatch batch = new SpriteBatch();

    public AbstractRenderer() {
        table.setWidth(getStage().getWidth());
        table.setHeight(getStage().getHeight());
    }

    public void create() {
        batch = new SpriteBatch();
    }

    public Stage getStage() {
        return stage;
    }

    public Table getTable() {
        return table;
    }

    public void show() {

    }

    public void resize(int width, int height) {
        getStage().getViewport().update(width, height, false);
    }

    public abstract void render(float delta);
}
