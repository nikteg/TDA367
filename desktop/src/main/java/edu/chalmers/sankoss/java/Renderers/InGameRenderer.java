package edu.chalmers.sankoss.java.Renderers;

import edu.chalmers.sankoss.java.Models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class InGameRenderer extends Renderer {

    /**
     * @inheritdoc
     */
    public InGameRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    @Override
    public void resize(int width, int height) {

    }

    /**
     * @inheritdoc
     */
    @Override
    public void render() {

    }

    @Override
    public void drawControllers(AbstractScreen screen) {

    }
}
