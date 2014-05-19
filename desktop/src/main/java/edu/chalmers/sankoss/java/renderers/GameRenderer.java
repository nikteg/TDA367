package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    private TextureRegionDrawable greenTextureBackground;
    private TextureRegionDrawable redTextureBackground;
    private int textureXOffset;
    private int textureYOffset;

    public GameRenderer(Observable observable) {
        super(observable);

        Pixmap pix = new Pixmap(32, 32, Pixmap.Format.RGBA8888);


        pix.setColor(0, 1f,0.2f,0.5f);
        pix.fill();
        greenTextureBackground = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));


        pix.setColor(0.8f, 0, 0f, 0.5f);
        pix.fill();
        redTextureBackground = new TextureRegionDrawable(new TextureRegion(new Texture(pix)));

        container.setBackground(greenTextureBackground);
        container.setWidth(1*32f);
        container.setHeight(3*32f);
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
        
        if(isOverlapping(container, grid1))
            container.setBackground(greenTextureBackground);
        else
            container.setBackground(redTextureBackground);

        textureXOffset = ((int)container.getWidth()/32) / 2 * 32;
        textureYOffset = ((int)container.getHeight()/32) / 2 * 32;

        container.setX(((mouseOnGridX()) / 32) * 32 + grid1.getX() - textureXOffset);
        container.setY((((int)grid1.getHeight()-mouseOnGridY()) / 32) * 32 + grid1.getY() - textureYOffset);
    }

    @Override
    public void update(Observable object, Object arg) {

    }

    public int mouseOnGridX() {
        return Gdx.input.getX() - (int)grid1.getX();
    }

    public int mouseOnGridY() {
        return (Gdx.input.getY() - (int)(Gdx.graphics.getHeight() - grid1.getY() - grid1.getHeight()));
    }

    public boolean isOverlapping(Actor act1, Actor act2) {
        int x1 = (int)act1.getX();
        int y1 = (int)act1.getY();
        int w1 = (int)act1.getWidth();
        int h1 = (int)act1.getHeight();

        int x2 = (int)act2.getX();
        int y2 = (int)act2.getY();
        int w2 = (int)act2.getWidth();
        int h2 = (int)act2.getHeight();

        int x1Max = x1 + w1;
        int x2Max = x2 + w2;
        int y1Max = y1 + h1;
        int y2Max = y2 + h2;
        if ((x2 >= x1 && x2Max <= x1Max) || (x1 >= x2 && x1Max <= x2Max)) {
          if ((y2 >= y1 && y2Max <= y1Max) || (y1 >= y2 && y1Max <= y2Max)) {
              return true;
          }
        }
        return false;
    }


}
