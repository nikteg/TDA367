package edu.chalmers.sankoss.desktop.mvc.gameover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;

import java.beans.PropertyChangeEvent;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class GameOverRenderer extends AbstractRenderer<GameOverModel> {

    private Label statusLabel = new Label("You Won!", Common.getSkin());
    private TextButton backBtn = new TextButton("Back to Lobby", Common.getSkin());

    public GameOverRenderer(GameOverModel model) {
        super(model);

        getTable().pad(8f);
        getTable().add(statusLabel).expand().colspan(2);
        getTable().row();
        getTable().add(backBtn.pad(8f)).expand().bottom().left().width(160f);

        getStage().addActor(getTable());
    }

    public TextButton getBackBtn() {
        return backBtn;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabelText(String txt) {
        statusLabel.setText(txt);
    }

    /**
     * Renders background and stage.
     * @param delta
     */
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

        if(evt.getPropertyName().equals("state")) {
            // setStatusLabelText("You Won!");
            if (getModel().getState().equals(GameOverModel.State.WON)) {
                setStatusLabelText("You Won!");
            } else {
                setStatusLabelText("You Lost!");
            }
        }

        /*// If you won
        if(evt.getPropertyName().equals("won")) {
            setStatusLabelText("You Won!");
        }

        // If you lost
        if(evt.getPropertyName().equals("lost")) {
            setStatusLabelText("You Lost!");
        }*/
    }
}
