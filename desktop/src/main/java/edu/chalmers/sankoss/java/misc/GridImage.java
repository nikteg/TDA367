package edu.chalmers.sankoss.java.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chalmers.sankoss.core.Coordinate;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class GridImage extends Table {

    //private Table imageTable = new Table();

    public GridImage() {
        super();

        this.setSize(320f, 320f);
        this.setTouchable(Touchable.enabled);

        // Sets background
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/grid.png")))));
    }



    public void add(Actor actor, Coordinate coordinate) {
        this.addActor(actor);
        actor.setWidth(32);
        actor.setHeight(32);

        //imageTable.addActor(actor);

        /*
        int gridX = (int)((crosshair.getX() - grid1.getX()) / 32) + 1;
        int gridY = (int)((crosshair.getY() - (grid1.getY() + grid1.getHeight())) / 32) * (-1);
         */

        actor.setX((coordinate.getX() - 1) * 32);
        actor.setY(this.getHeight() - ((coordinate.getY()) * 32));

        System.out.println(Gdx.input.getX() + " - " + actor.getX());


    }
}
