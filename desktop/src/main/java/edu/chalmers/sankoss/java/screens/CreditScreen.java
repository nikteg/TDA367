package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.CreditModel;
import edu.chalmers.sankoss.java.renderers.CreditRenderer;

public class CreditScreen extends AbstractScreen<CreditModel, CreditRenderer> {

    public CreditScreen(Class<CreditModel> model, Class<CreditRenderer> renderer) {
        super(model, renderer);

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */
        });
    }

    @Override
    public void update(float delta) {

    }
}
