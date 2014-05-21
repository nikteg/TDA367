package edu.chalmers.sankoss.desktop.mvc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.misc.GridImage;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;

import java.util.Observable;

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
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
    }

    @Override
    public void update(Observable object, Object arg) {

        if (arg.equals("opponent")) {
            opponentPanel.setLblName(getModel().getOpponent().getName());
            opponentPanel.setNationality(getModel().getOpponent().getNationality());
        }

        // Changed to your turn
        if (arg.equals("shooting_allowed")) {
            if(getModel().isShootingAllowed()) {
                opponentPanel.setTurnLabelText("");
                playerPanel.setTurnLabelText("Your Turn!");
            } else {
                opponentPanel.setTurnLabelText("Opponent's turn!");
                playerPanel.setTurnLabelText("");
            }

        }

        // If you've shot at enemy
        if (arg.equals("shot")) {
            grid1.addShot(getModel().getShots().get(getModel().getShots().size() - 1));
        }

        if (arg.equals("opponent_shot")) {
            grid2.addShot(getModel().getOpponentShots().get(getModel().getOpponentShots().size() - 1));
        }

        if(arg.equals("flag")) {
            for (Coordinate flagCoordinate : getModel().getFlags()) {
                grid1.toggleFlag(flagCoordinate);
            }
        }

        // When game is over
        if(arg.equals("state")) {
            if (getModel().getState() == GameModel.State.WON) {
                // TODO WINNING

                grid1.setTouchable(Touchable.disabled);
            }

            if (getModel().getState() == GameModel.State.LOST) {
                // TODO LOST

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
}
