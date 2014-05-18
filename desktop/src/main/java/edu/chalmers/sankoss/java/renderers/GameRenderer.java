package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private Image grid1 = new Image(gridTexture);
    private Image grid2 = new Image(gridTexture);
    private Actor opponentPanel = new PlayerPanel("Kalle", CorePlayer.Nationality.GERMANY, PlayerPanel.Alignment.LEFT);
    private Actor playerPanel = new PlayerPanel("TOng", CorePlayer.Nationality.JAPAN, PlayerPanel.Alignment.RIGHT);
    private Table container = new Table();

    public GameRenderer(Observable observable) {
        super(observable);

        Pixmap pix = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
        pix.setColor(0, 1f,0.2f,0.5f);
        pix.fill();
        container.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pix))));
        container.setWidth(32f);
        container.setHeight(32f);
        container.setX(50f);
        container.setY(50f);

        getTable().add(opponentPanel).colspan(2).expandX().fill().height(100f);
        getTable().row();
        getTable().add(grid1).pad(16f).expand();
        getTable().add(grid2).pad(16f).expand();
        getTable().row();
        getTable().add(playerPanel).colspan(2).expandX().fill().height(100f);

        getStage().addActor(getTable());

        getStage().addActor(container);
        //getTable().debug();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

        getStage().act(delta);
        getStage().draw();
        Table.drawDebug(getStage());
        //TODO Musen ska vara i mitten av blocket /3 är fel
        //if (container.getX() < grid1.getX() || container.getY() > grid1.getY()+grid1.getHeight() || )
        container.setX(((Gdx.input.getX() - (int)grid1.getX() - (int)container.getWidth()/3 / 32) * 32 + grid1.getX());
        container.setY(((Gdx.graphics.getHeight() - Gdx.input.getY() - (int)grid1.getY()) / 32) * 32 + grid1.getY());
    }

    @Override
    public void update(Observable object, Object arg) {

    }


}
