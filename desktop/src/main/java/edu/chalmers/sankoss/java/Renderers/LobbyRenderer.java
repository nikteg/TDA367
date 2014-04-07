package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import edu.chalmers.sankoss.java.Models.ScreenModel;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class LobbyRenderer extends Renderer {

    /**
     * @inheritdoc
     */
    public LobbyRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);

    }

    @Override
    public void drawStaticControllers() {

    }
}
