package edu.chalmers.sankoss.desktop.mvc.credits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * AbstractRenderer for the Credit text
 * 
 * @author Daniel Eineving
 * @date 2014-05-13
 */
public class CreditsRenderer extends AbstractRenderer<CreditsModel> {

    private final float scrollSpeed = 0.01f;

    TextButton btnBack = new TextButton("Back", Common.getSkin());
    Label lblCredits = new Label("", Common.getSkin());

    public PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public CreditsRenderer(CreditsModel model) {
        super(model);

        lblCredits.setText(getModel().getCreditsText());
        lblCredits.setAlignment(Align.center);
        lblCredits.setX(getStage().getViewport().getViewportWidth() / 2 - lblCredits.getWidth() / 2);
        //lblCredits.setY(-500);

        getTable().pad(8f);
        getTable().add(btnBack.pad(8f)).expand().bottom().left().width(160);
        //getTable().debug();

        getStage().addActor(lblCredits);
        getStage().addActor(getTable());
    }

    public Button getBtnBack() {
        return btnBack;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.663f, 0.663f, 0.663f, 1);

        lblCredits.setY(lblCredits.getY() + scrollSpeed / delta);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("text_position")) {
            float msg = (float)evt.getNewValue();
            lblCredits.setY(msg);
        }
    }

}
