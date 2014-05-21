package edu.chalmers.sankoss.desktop.mvc.credits;

import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class CreditsScreen extends AbstractScreen<CreditsModel, CreditsRenderer> {

    public CreditsScreen() {
        getRenderer().getBtnBack().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                changeScreen("mainmenu");
            }
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        super.show();

        /**
         * Reset text position
         */
        getModel().setCreditsTextPosition(-96f);
    }
}
