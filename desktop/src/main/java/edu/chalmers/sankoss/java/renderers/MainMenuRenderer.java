package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.Screens;

import java.util.Observable;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 3/24/14
 */
public class MainMenuRenderer extends AbstractRenderer {

    TextButton btnMultiPlayer = new TextButton("Multiplayer", SankossGame.getInstance().getSkin());
    TextButton btnOptions = new TextButton("Options", SankossGame.getInstance().getSkin());
    TextButton btnCredits = new TextButton("Credits", SankossGame.getInstance().getSkin());
    TextButton btnExit = new TextButton("Exit", SankossGame.getInstance().getSkin());

    public MainMenuRenderer(Observable observable) {
        super(observable);

        float BUTTON_WIDTH = 600f;

        getTable().add(btnMultiPlayer).width(BUTTON_WIDTH).pad(8f);
        getTable().row();
        getTable().add(btnOptions).width(BUTTON_WIDTH).pad(8f);
        getTable().row();
        getTable().add(btnCredits).width(BUTTON_WIDTH).pad(8f);
        getTable().row();
        getTable().add(btnExit).width(BUTTON_WIDTH).pad(8f);
        getTable().row();
        //getTable().debug();
        getStage().addActor(getTable());

        btnMultiPlayer.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Screens.LOBBY.show();
            }
        });
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SankossGame.getInstance().exitApplication();
            }
        });

        btnMultiPlayer.setDisabled(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void update(Observable object, Object arg) {
        if (arg.equals("connected")) {
            btnMultiPlayer.setDisabled(false);
        }
    }
}
