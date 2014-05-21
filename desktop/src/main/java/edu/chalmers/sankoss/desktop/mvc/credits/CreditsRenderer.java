package edu.chalmers.sankoss.desktop.mvc.credits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;

import java.util.Observable;

/**
 * AbstractRenderer for the Credit text
 * 
 * @author Daniel Eineving
 * @date 2014-05-13
 */
public class CreditsRenderer extends AbstractRenderer {

    private final float scrollSpeed = 0.01f;

    TextButton btnBack = new TextButton("Back", Common.getSkin());
    Label lblCredits = new Label("", Common.getSkin());

    public CreditsRenderer(Observable observable) {
        super(observable);

        lblCredits.setText(((CreditsModel) observable).getCreditsText());
        lblCredits.setAlignment(Align.center);
        lblCredits.setX(getStage().getViewport().getViewportWidth() / 2 - lblCredits.getWidth() / 2);
        //lblCredits.setY(-500);

        getTable().pad(8f);
        getTable().add(btnBack).expand().bottom().left();
        //getTable().debug();

        getStage().addActor(lblCredits);
        getStage().addActor(getTable());

        btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	getProptertyChangeSupport().firePropertyChange("showMainMenu", true, false);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

        lblCredits.setY(lblCredits.getY() + scrollSpeed / delta);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void update(Observable object, Object arg) {
        if (arg.equals("text_position")) {
            lblCredits.setY(((CreditsModel)object).getCreditsTextPosition());
        }
    }
}
