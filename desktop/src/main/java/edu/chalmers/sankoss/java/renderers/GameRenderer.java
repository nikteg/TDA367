package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.java.misc.PlayerPanel;

import java.util.Observable;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @modified Daniel Eineving, Fredrik Thune
 */
public class GameRenderer extends AbstractRenderer {
    private Texture gridTexture = new Texture(Gdx.files.internal("textures/grid.png"));
    private Texture corshairTexture = new Texture(Gdx.files.internal("textures/corshair.png"));
    private Image grid1 = new Image(gridTexture);
    private Image grid2 = new Image(gridTexture);
    private Image corshair = new Image(corshairTexture);
    private Actor opponentPanel = new PlayerPanel("Kalle", CorePlayer.Nationality.GERMANY, PlayerPanel.Alignment.LEFT);
    private Actor playerPanel = new PlayerPanel("TOng", CorePlayer.Nationality.JAPAN, PlayerPanel.Alignment.RIGHT);
    private Table container = new Table();

    private int textureXOffset;
    private int textureYOffset;

    public GameRenderer(Observable observable) {
        super(observable);

        corshair.addListener(new InputListener() {
            @Override
            public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {  // If left mouse button was pressed
                return true;
            }

        });

        corshair.setWidth(32);
        corshair.setHeight(32);

        Gdx.input.setInputProcessor(getStage());

        getTable().add(opponentPanel).colspan(2).expandX().fill().height(100f);
        getTable().row();
        getTable().add(grid1).pad(16f).expand();
        getTable().add(grid2).pad(16f).expand();
        getTable().row();
        getTable().add(playerPanel).colspan(2).expandX().fill().height(100f);

        getStage().addActor(getTable());

        getStage().addActor(corshair);
        //getTable().debug();

        grid1.addListener(new InputListener() {

            @Override
            public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {  // If left mouse button was pressed

                if (button == 0) {
                    shotAt(x, y);
                }

                return false;

            }

            public void shotAt(float x, float y) {
                System.out.println("shot at: " + x + ", " + y);
            }
        });

        /*
        @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        super.touchDown(x, y, pointer, button);

        // If left mouse button was pressed
        if(button == 0) {
            shotAt(x, y);
        }

        return false;
    }

    public void shotAt(int x, int y) {

    }
         */
    }

    public void updateYourTurn() {
        // If your turn
        ((PlayerPanel)opponentPanel).setTurnLabelText("");
        ((PlayerPanel)playerPanel).setTurnLabelText("Your Turn!");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());

        if(isInside(corshair, grid1))
            corshair.setVisible(true);
        else
            corshair.setVisible(false);

        textureXOffset = ((int)container.getWidth()/32) / 2 * 32;
        textureYOffset = ((int)container.getHeight()/32) / 2 * 32;

        corshair.setX(((mouseOnGridX()) / 32) * 32 + grid1.getX() - textureXOffset);
        corshair.setY((((int)grid1.getHeight()-mouseOnGridY()) / 32) * 32 + grid1.getY() - textureYOffset);
    }

    @Override
    public void update(Observable object, Object arg) {
        // Changed to your turn
        if(arg.equals("turn")) {
            updateYourTurn();
        }
    }

    /**
     * Method for determine if mouse is on a valid position
     * or not.
     * @param corshair Corshair to follow mouse in grid.
     * @param grid grid to shoot in.
     * @return
     */
    public boolean isInside(Actor corshair, Actor grid) {
        int corshairX = (int)corshair.getX();
        int corshairY = (int)corshair.getY();
        int corshairWidth = (int)corshair.getWidth();
        int corshairHeight = (int)corshair.getHeight();

        int gridX = (int)grid.getX();
        int gridY = (int)grid.getY();
        int gridWidth = (int)grid.getWidth();
        int gridHeight = (int)grid.getHeight();

        int corshairXMax = corshairX + corshairWidth;
        int gridXMax = gridX + gridWidth;
        int corshairYMax = corshairY + corshairHeight;
        int gridYMax = gridY + gridHeight;

        if ((gridX > corshairX && gridXMax < corshairXMax) || (corshairX >= gridX && corshairXMax <= gridXMax)) {
            if ((gridY > corshairY && gridYMax < corshairYMax) || (corshairY >= gridY && corshairYMax <= gridYMax)) {
                return true;
            }
        }
        return false;
    }

    public int mouseOnGridX() {
        return Gdx.input.getX() - (int)grid1.getX();
    }

    public int mouseOnGridY() {
        return (Gdx.input.getY() - (int)(Gdx.graphics.getHeight() - grid1.getY() - grid1.getHeight()));
    }




}
