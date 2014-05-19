package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.java.misc.GridImage;
import edu.chalmers.sankoss.java.misc.PlayerPanel;
import edu.chalmers.sankoss.java.models.GameModel;

import java.util.Observable;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @modified Daniel Eineving, Fredrik Thune
 */
public class GameRenderer extends AbstractRenderer {
    // Controllers
    private Texture gridTexture = new Texture(Gdx.files.internal("textures/grid.png"));
    private Texture crosshairTexture = new Texture(Gdx.files.internal("textures/corshair.png"));
    private Texture hitTexture = new Texture(Gdx.files.internal("textures/explosion.png"));
    private Texture missTexture = new Texture(Gdx.files.internal("textures/miss.png"));
    private GridImage grid1 = new GridImage();
    private GridImage grid2 = new GridImage();
    private Image crosshair = new Image(crosshairTexture);
    private Actor opponentPanel = new PlayerPanel("Hans Gunter", CorePlayer.Nationality.GERMANY, PlayerPanel.Alignment.LEFT);
    private Actor playerPanel = new PlayerPanel("T0ng", CorePlayer.Nationality.JAPAN, PlayerPanel.Alignment.RIGHT);
    private Table container = new Table();

    private GameModel model;

    private int textureXOffset;
    private int textureYOffset;

    public GameRenderer(Observable observable) {
        super(observable);

        model = (GameModel) observable;
        crosshair.setTouchable(Touchable.disabled);
        crosshair.setWidth(32);
        crosshair.setHeight(32);

        Gdx.input.setInputProcessor(getStage());

        getTable().add(opponentPanel).colspan(2).expandX().fill().height(100f);
        getTable().row();
        getTable().add(grid1).pad(16f).expand();
        getTable().add(grid2).pad(16f).expand();
        getTable().row();
        getTable().add(playerPanel).colspan(2).expandX().fill().height(100f);

        getStage().addActor(getTable());

        getStage().addActor(crosshair);
        //getTable().debug();

        grid1.addListener(new InputListener() {

            @Override
            public boolean touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y, int pointer, int button) {  // If left mouse button was pressed

                if (button == 0) {
                    shot();
                }

                return false;

            }

            public void shot() {
                int gridX = (int)((crosshair.getX() - grid1.getX()) / 32) + 1;
                int gridY = (int)((crosshair.getY() - (grid1.getY() + grid1.getHeight())) / 32) * (-1);


                // KEEP BELOW
                // sends shooting message and disables clicking
                //SankossGame.getInstance().getClient().fire(SankossGame.getInstance().getClient().getOpponents().get(0), new Coordinate(gridX, gridY));
                //grid1.setTouchable(Touchable.disabled);

                model.addToList(new Coordinate(gridX, gridY));

                // REMOVE BELOW
                //Image miss = new Image(getMissTexture());
                //((GridImage)getGrid1()).add(miss, new Coordinate(gridX, gridY));

                System.out.println("shot at " + gridX + ", " + gridY);

            }


        });

    }

    public void updateYourTurn(GameModel model) {
        // If your turn
        ((PlayerPanel)opponentPanel).setTurnLabelText("");
        ((PlayerPanel)playerPanel).setTurnLabelText("Your Turn!");

        enableShooting();
    }

    public void enableShooting() {
        grid1.setTouchable(Touchable.enabled);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());

        if(isInside(crosshair, grid1))
            crosshair.setVisible(true);
        else
            crosshair.setVisible(false);

        textureXOffset = ((int)container.getWidth()/32) / 2 * 32;
        textureYOffset = ((int)container.getHeight()/32) / 2 * 32;

        crosshair.setX(((mouseOnGridX()) / 32) * 32 + grid1.getX() - textureXOffset);
        crosshair.setY((((int)grid1.getHeight()-mouseOnGridY()) / 32) * 32 + grid1.getY() - textureYOffset);
    }

    @Override
    public void update(Observable object, Object arg) {
        // Changed to your turn
        if(arg.equals("turn")) {
            updateYourTurn((GameModel)object);

        }

        // If you've shot at enemy
        if(arg.equals("yourShots")) {
            grid1.add(new Image(new Texture("textures/explosion.png")), model.getYourShots().get(model.getYourShots().size() - 1));
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

    public Table getGrid1() {
        return grid1;
    }

    public Table getGrid2() {
        return grid2;
    }

    public Texture getHitTexture() {
        return hitTexture;
    }

    public Texture getMissTexture() {
        return  missTexture;
    }


}
