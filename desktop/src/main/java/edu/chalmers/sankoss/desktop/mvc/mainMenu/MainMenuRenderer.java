package edu.chalmers.sankoss.desktop.mvc.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;

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

    Image logo = new Image(new Texture(Gdx.files.internal("logo.png")));
    TextButton btnMultiPlayer = new TextButton("Multiplayer", Common.getSkin());
    TextButton btnOptions = new TextButton("Options", Common.getSkin());
    TextButton btnCredits = new TextButton("Credits", Common.getSkin());
    TextButton btnExit = new TextButton("Exit", Common.getSkin());

    public MainMenuRenderer(Observable observable) {
        super(observable);

        btnMultiPlayer.pad(8f);
        btnOptions.pad(8f);
        btnCredits.pad(8f);
        btnExit.pad(8f);

        getTable().add(logo);
        getTable().row();
        getTable().add(btnMultiPlayer).fillX().pad(8f);
        getTable().row();
        getTable().add(btnOptions).fillX().pad(8f);
        getTable().row();
        getTable().add(btnCredits).fillX().pad(8f);
        getTable().row();
        getTable().add(btnExit).fillX().pad(8f);
        getTable().row();
        //getTable().debug();
        getStage().addActor(getTable());

        btnMultiPlayer.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	getProptertyChangeSupport().firePropertyChange("showLobby", true, false);
            }
        });
        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Dialog dialog = new Dialog("Message", Common.getSkin()) {

                    {
                        text("Not implemented yet");
                        button("OK");
                    }
                };

                dialog.setMovable(false);
                dialog.show(getStage());
            }
        });
        btnCredits.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	getProptertyChangeSupport().firePropertyChange("showCredits", true, false);
            	System.out.println("Credits pressed @ mmRenederer");
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
