package edu.chalmers.sankoss.desktop.mvc.credits;

import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

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
