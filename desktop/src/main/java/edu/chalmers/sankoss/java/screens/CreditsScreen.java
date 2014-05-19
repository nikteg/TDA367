package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.CreditsModel;
import edu.chalmers.sankoss.java.renderers.CreditsRenderer;

public class CreditsScreen extends AbstractScreen<CreditsModel, CreditsRenderer> {

    public CreditsScreen(Class<CreditsModel> model, Class<CreditsRenderer> renderer) {
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

        getModel().setCreditsTextPosition(-96f);
    }
}
