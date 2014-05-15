package edu.chalmers.sankoss.java2.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.beans.PropertyChangeEvent;

/**
 * Created by nikteg on 15/05/14.
 */
public class LobbyRenderer extends AbstractRenderer {
    private List roomList;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
