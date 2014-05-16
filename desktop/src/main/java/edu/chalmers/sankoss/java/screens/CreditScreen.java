package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.CreditModel;
import edu.chalmers.sankoss.java.renderers.CreditRenderer;

public class CreditScreen extends AbstractScreen<CreditRenderer, CreditModel> {

    public CreditScreen() {
        setModel(new CreditModel());
        setRenderer(new CreditRenderer(getModel()));

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */
        });
    }

    @Override
    public void update(float delta) {

    }
}
