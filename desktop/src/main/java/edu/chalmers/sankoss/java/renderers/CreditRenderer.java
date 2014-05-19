package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import edu.chalmers.sankoss.java.SankossGame;

import java.util.Observable;

/**
 * AbstractRenderer for the Credit text
 * 
 * @author Daniel Eineving
 * @date 2014-05-13
 */
public class CreditRenderer extends AbstractRenderer {

    Label creditsLabel = new Label("SUPER\nDUPER\nTEXT", SankossGame.getInstance().getSkin());

    public CreditRenderer(Observable observable) {
        super(observable);

        creditsLabel.setAlignment(Align.center);
        creditsLabel.setY(-creditsLabel.getHeight());
        creditsLabel.setOriginY(creditsLabel.getHeight());

        getStage().addActor(creditsLabel);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

        getStage().act();
        getStage().draw();

        // TODO FIXA MITTEN
        creditsLabel.setX(getStage().getWidth() / 2 - creditsLabel.getWidth() / 2);
        creditsLabel.setY(creditsLabel.getY() + 0.005f / delta);
    }

    @Override
    public void update(Observable object, Object arg) {

    }
}
