package edu.chalmers.sankoss.java2.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import edu.chalmers.sankoss.java2.SankossGame;

import java.beans.PropertyChangeEvent;

/**
 * Created by nikteg on 15/05/14.
 */
public class MainMenuRenderer extends AbstractRenderer {

    private TextButton btnMultiPlayer;

    public MainMenuRenderer() {
        btnMultiPlayer = new TextButton("Multiplayer", SankossGame.getInstance().getSkin());

        getTable().add(btnMultiPlayer).expand().center();

        getStage().addActor(getTable());
        getTable().debug();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
