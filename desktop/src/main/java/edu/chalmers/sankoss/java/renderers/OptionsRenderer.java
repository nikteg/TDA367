package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.Screens;

import java.util.Observable;

/**
 * AbstractRenderer for the Credit text
 * 
 * @author Daniel Eineving
 * @date 2014-05-13
 */
public class OptionsRenderer extends AbstractRenderer {

    SelectBox selectBox = new SelectBox<String>(SankossGame.getInstance().getSkin());
    TextButton btnBack = new TextButton("Back", SankossGame.getInstance().getSkin());

    public OptionsRenderer(Observable observable) {
        super(observable);

        selectBox.setItems(new String[] {"Test", "Korv", "Fisk"});

        getTable().pad(8f);

        getTable().add(selectBox).expand();
        getTable().row();
        getTable().add(btnBack).bottom().left();
        getTable().debug();

        getStage().addActor(getTable());

        btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Screens.MAIN_MENU.show();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void update(Observable object, Object arg) {

    }
}
