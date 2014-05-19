package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.OptionsModel;
import edu.chalmers.sankoss.java.renderers.OptionsRenderer;

public class OptionsScreen extends AbstractScreen<OptionsModel, OptionsRenderer> {

    public OptionsScreen(Class<OptionsModel> model, Class<OptionsRenderer> renderer) {
        super(model, renderer);

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        super.show();
    }
}
