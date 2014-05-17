package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.Screens;
import edu.chalmers.sankoss.java.models.WaitingModel;

import java.util.Observable;

/**
 * AbstractRenderer for WaitingScreen.
 * Will only draw a label and two buttons.
 *
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 5/5/14
 */
public class WaitingRenderer extends AbstractRenderer {

    private Label lblWaiting = new Label("Loading...", SankossGame.getInstance().getSkin());
    private TextButton btnBack = new TextButton("Back", SankossGame.getInstance().getSkin());
    private TextButton btnStart = new TextButton("Start", SankossGame.getInstance().getSkin());

    public WaitingRenderer(Observable observable) {
        super(observable);

        getTable().pad(8f);

        getTable().add(lblWaiting).expand().colspan(2);
        getTable().row();
        getTable().add(btnBack);
        getTable().add(btnStart);
        //getTable().debug();

        btnStart.setDisabled(true);

        getStage().addActor(getTable());

        btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (SankossGame.getInstance().getClient().isHosting()) {
                    SankossGame.getInstance().getClient().removeRoom(SankossGame.getInstance().getClient().getRoomID());

                    Gdx.app.debug("WaitingRenderer", "Removing hosted room");
                }

                // TODO send leave room message?

                Screens.LOBBY.show();
            }
        });

        btnStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SankossGame.getInstance().getClient().startGame(SankossGame.getInstance().getClient().getRoomID()); // TODO fix start game method?
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);

        getStage().act();
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void update(Observable object, Object arg) {
        WaitingModel model = (WaitingModel) object;

        if (arg.equals("hosting")) {
            if (model.isHosting()) {
                lblWaiting.setText("Waiting for players to join...");
            } else {
                lblWaiting.setText("Waiting for the host to start the game...");
            }
        }

        if (arg.equals("player")) {
            if (model.getPlayers().size() > 0 && model.isHosting()) {
                btnStart.setDisabled(false);
                lblWaiting.setText(model.getPlayers().get(0).getName() + " has joined the room!");
            }
        }
    }
}
