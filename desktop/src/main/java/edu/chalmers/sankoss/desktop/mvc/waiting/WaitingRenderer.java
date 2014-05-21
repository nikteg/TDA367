package edu.chalmers.sankoss.desktop.mvc.waiting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;

import java.util.Observable;

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
    public void update(Observable object, Object arg) {
        WaitingModel model = (WaitingModel) object;

        if (arg.equals("hosting")) {
            if (model.isHosting()) {
                lblWaiting.setText("Waiting for players to join...");
            } else {
                lblWaiting.setText("Waiting for the host to start the game...");
            }
        }
        if (model.isHosting()) {
            if (arg.equals("player_joined")) {
                System.out.println("NUMBER OF PLAYER IN ROOMEN " + model.getPlayers().size());
                btnStart.setDisabled(false);
                lblWaiting.setText(model.getPlayers().get(0).getName() + " has joined the room!");
            } else if (arg.equals("player_left")) {
                btnStart.setDisabled(true);
                lblWaiting.setText("Player left. Waiting for players to join...");
            }

        }

    }
}