package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import edu.chalmers.sankoss.java.Models.ScreenModel;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class MainMenuRenderer extends Renderer{

    private Label battleLabel;
    private Label statusLabel;

    /**
     * @inheritdoc
     */
    public MainMenuRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    /**
     * This method loops.
     */
    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);
    }

    public void drawStaticControllers() {

    }

}
