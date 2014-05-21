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

import java.beans.PropertyChangeEvent;
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
    private PlayerPanel opponentPanel = new PlayerPanel("Hans Gunter", CorePlayer.Nationality.GERMANY, PlayerPanel.Alignment.LEFT);
    private PlayerPanel playerPanel = new PlayerPanel("T0ng", CorePlayer.Nationality.JAPAN, PlayerPanel.Alignment.RIGHT);
    private Table container = new Table();
    private final float GRID_SQUARE = 32f;

    private int textureXOffset;
    private int textureYOffset;

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

    /**
     * Method for updating the visuals of representing
     * the opponent.
     */
    public void updateOpponentVisuals() {

        // Sets name
        ((PlayerPanel)opponentPanel).setLblName(SankossClient.getInstance().getOpponents().get(0).getName());

        // Sets Nationality
        System.out.println("OPP NAT: " + SankossClient.getInstance().getOpponents().get(0).getNationality());
        ((PlayerPanel)opponentPanel).setImgNationality(SankossClient.getInstance().getOpponents().get(0).getNationality());


    }

    public void disableYourTurn() {
        // If opponent's turn

        opponentPanel.setTurnLabelText("Opponent's turn!");
        playerPanel.setTurnLabelText("");

        disableShooting();
    }

    public void enableYourTurn() {
        // If your turn

        opponentPanel.setTurnLabelText("");
        playerPanel.setTurnLabelText("Your Turn!");

        enableShooting();
    }

    public void enableShooting() {
        grid1.setTouchable(Touchable.enabled);
    }

    public void disableShooting() {
        grid1.setTouchable(Touchable.disabled);
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
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("opponent")) {
            CorePlayer msg = (CorePlayer)evt.getNewValue();
            opponentPanel.setName(msg.getName());
            opponentPanel.setNationality(msg.getNationality());
        }

        // Changed to your turn
        if (evt.getPropertyName().equals("shooting_allowed")) {
            Boolean msg = (Boolean)evt.getNewValue();
            if(msg) {
                enableYourTurn();
            } else {
                disableYourTurn();
            }

        }

        // If you've shot at enemy
        if (evt.getPropertyName().equals("shot")) {
            Coordinate msg = (Coordinate)evt.getNewValue();
            grid1.add(new Image(new Texture("textures/explosion.png")), msg);
        }

        if(evt.getPropertyName().equals("flag")) {
            Coordinate msg = (Coordinate)evt.getNewValue();
            grid1.toggleFlag(msg);
        }

        // When game is over
        if(evt.getPropertyName().equals("state")) {
            GameModel.State msg = (GameModel.State)evt.getNewValue();
            if (msg == GameModel.State.WON) {
                // TODO WINNING

                grid1.setTouchable(Touchable.disabled);
            }

            if (msg == GameModel.State.LOST) {
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

    public Texture getHitTexture() {
        return hitTexture;
    }

    public Texture getMissTexture() {
        return  missTexture;
    }


}
