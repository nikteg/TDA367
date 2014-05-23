package edu.chalmers.sankoss.desktop.mvc.waiting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;

import java.beans.PropertyChangeEvent;

/**
 * AbstractRenderer for WaitingScreen.
 * Will only draw a label and two buttons.
 *
 * @author Mikael Malmqvist
 * @modified Niklas Tegnander
 * @date 5/5/14
 */
public class WaitingRenderer extends AbstractRenderer<WaitingModel> {

    private Label lblWaiting = new Label("Loading...", Common.getSkin());
    private TextButton btnBack = new TextButton("Back", Common.getSkin());
    private TextButton btnStart = new TextButton("Start", Common.getSkin());

    public WaitingRenderer(WaitingModel model) {
        super(model);

        getTable().pad(8f);

        getTable().add(lblWaiting).expand().colspan(2);
        getTable().row();
        getTable().add(btnBack);
        getTable().add(btnStart);
        //getTable().debug();

        btnStart.setDisabled(true);

        getStage().addActor(getTable());
    }

    public TextButton getBtnBack() {
        return btnBack;
    }

    public TextButton getBtnStart() {
        return btnStart;
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
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("reset")) {
            lblWaiting.setText("Loading...");
            btnStart.setText("Start");
            btnStart.setDisabled(true);


        }

        if (evt.getPropertyName().equals("hosting")) {
            boolean msg = (boolean) evt.getNewValue();
            if (msg) {
                lblWaiting.setText("Waiting for players to join...");
            } else {
                lblWaiting.setText("Waiting for the host to start the game...");
            }
        }
        if (getModel().isHosting()) {
            if (evt.getPropertyName().equals("player_joined")) {
                CorePlayer msg = (CorePlayer) evt.getNewValue();
                btnStart.setDisabled(false);
                lblWaiting.setText(msg.getName() + " has joined the room!");
            } else if (evt.getPropertyName().equals("player_left")) {
                btnStart.setDisabled(true);
                lblWaiting.setText("Player left. Waiting for players to join...");
            }

        }
    }
}
