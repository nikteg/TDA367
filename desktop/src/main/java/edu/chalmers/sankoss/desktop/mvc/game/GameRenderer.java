package edu.chalmers.sankoss.desktop.mvc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;

import java.beans.PropertyChangeEvent;

/**
 * Renderer for Game Screen.
 * Is listened to by GameModel.
 *
 * @author Mikael Malmqvist
 * @modified Daniel Eineving, Fredrik Thune
 */
public class GameRenderer extends AbstractRenderer<GameModel> {
    // Controllers
    private Texture crosshairTexture = new Texture(Gdx.files.internal("textures/corshair.png"));
    private Texture hitTexture = new Texture(Gdx.files.internal("textures/explosion.png"));
    private Texture missTexture = new Texture(Gdx.files.internal("textures/miss.png"));
    private GridImage grid1 = new GridImage();
    private GridImage grid2 = new GridImage();
    private Image crosshair = new Image(crosshairTexture);
    private PlayerPanel opponentPanel = new PlayerPanel(PlayerPanel.Alignment.LEFT);
    private PlayerPanel playerPanel = new PlayerPanel(PlayerPanel.Alignment.RIGHT);
    private final float GRID_SQUARE = 32f;

    public GameRenderer(GameModel model) {
        super(model);

        crosshair.setWidth(GRID_SQUARE);
        crosshair.setHeight(GRID_SQUARE);

        // Crosshair always need to be disabled, else
        // players wont be able to click grid
        crosshair.setTouchable(Touchable.disabled);

        Gdx.input.setInputProcessor(getStage());

        getTable().add(opponentPanel).colspan(2).expandX().fill().height(100f);
        getTable().row();
        getTable().add(grid1).pad(16f).expand();
        getTable().add(grid2).pad(16f).expand();
        getTable().row();
        getTable().add(playerPanel).colspan(2).expandX().fill().height(100f);

        grid1.addCrosshair(crosshair);

        getStage().addActor(getTable());


        //getTable().debug();

        // Starts out as disabled. When Server randomly calls turn, someone will
        // enable their grid
        grid1.setTouchable(Touchable.enabled);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.663f, 0.663f, 0.663f, 1);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("reset")) {
            // Removes all ships before adding new ones

            getGrid1().clearActions();
            getGrid2().clearActions();
            getGrid1().clearChildren();
            getGrid2().clearChildren();

            getGrid1().addCrosshair(crosshair);
            getGrid1().setTouchable(Touchable.enabled);
        }

        if (evt.getPropertyName().equals("opponent")) {
            CorePlayer msg = (CorePlayer)evt.getNewValue();
            opponentPanel.setName(msg.getName());
            opponentPanel.setNationality(msg.getNationality());
        }

        // Changed to your turn
        if (evt.getPropertyName().equals("shooting_allowed")) {
            Boolean msg = (Boolean)evt.getNewValue();
            if(msg) {
                opponentPanel.setTurnLabelText("");
                playerPanel.setTurnLabelText("Your turn!");
            } else {
                opponentPanel.setTurnLabelText("Opponent's turn!");
                playerPanel.setTurnLabelText("");
            }

        }

        if (evt.getPropertyName().equals("shot")) {
            Shot msg = (Shot)evt.getNewValue();
            grid1.addShot(msg);
        }

        if (evt.getPropertyName().equals("opponent_shot")) {
            Shot msg = (Shot)evt.getNewValue();
            grid2.addShot(msg);
        }

        if(evt.getPropertyName().equals("flag")) {
            Coordinate msg = (Coordinate)evt.getNewValue();
            grid1.toggleFlag(msg);
        }

        // When game is over
        if(evt.getPropertyName().equals("state")) {
            GameModel.State msg = (GameModel.State)evt.getNewValue();

            // If player won
            if (msg == GameModel.State.WON) {

                grid1.setTouchable(Touchable.disabled);
            }

            if (msg == GameModel.State.LOST) {

                grid1.setTouchable(Touchable.disabled);
            }
        }
    }



    public GridImage getGrid1() {
        return grid1;
    }

    public GridImage getGrid2() {
        return grid2;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public PlayerPanel getOpponentPanel() {
        return opponentPanel;
    }
}
