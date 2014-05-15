package edu.chalmers.sankoss.java2.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.java2.SankossGame;

import java.beans.PropertyChangeEvent;

/**
 * Created by nikteg on 15/05/14.
 */
public class MainMenuRenderer extends AbstractRenderer {

    private Label lblTitle;
    private TextButton btnMultiPlayer;
    private TextButton btnOptions;
    private TextButton btnCredits;
    private TextButton btnExit;

    private Texture background = new Texture(Gdx.files.internal("textures/background.jpg"));

    public MainMenuRenderer() {

        BitmapFont biggerFont = SankossGame.getInstance().getSkin().getFont("bigger-font");
        biggerFont.scale(2f);

        BitmapFont defaultFont = SankossGame.getInstance().getSkin().getFont("default-font");
        defaultFont.scale(1.1f);

        lblTitle = new Label("Sankoss", SankossGame.getInstance().getSkin().get("bigger", Label.LabelStyle.class));
        lblTitle.setAlignment(Align.center);

        btnMultiPlayer = new TextButton("Multiplayer", SankossGame.getInstance().getSkin());
        btnMultiPlayer.setDisabled(true);

        btnOptions = new TextButton("Options", SankossGame.getInstance().getSkin());
        btnCredits = new TextButton("Credits", SankossGame.getInstance().getSkin());
        btnExit = new TextButton("Exit", SankossGame.getInstance().getSkin());

        btnMultiPlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SankossGame.Screens.LOBBY.show();
            }
        });

        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        btnMultiPlayer.pad(16);
        btnOptions.pad(16);
        btnCredits.pad(16);
        btnExit.pad(16);
        getTable().add(lblTitle).width(400).pad(8);
        getTable().row();
        getTable().add(btnMultiPlayer).width(400).pad(8);
        getTable().row();
        getTable().add(btnOptions).width(400).pad(8);
        getTable().row();
        getTable().add(btnCredits).width(400).pad(8);
        getTable().row();
        getTable().add(btnExit).width(400).pad(8);

        getStage().addActor(getTable());
        //getTable().debug();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public void setMultiPlayerDisabled(boolean disabled) {
        btnMultiPlayer.setDisabled(disabled);
    }
}
