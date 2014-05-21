package edu.chalmers.sankoss.desktop.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.chalmers.sankoss.core.core.Coordinate;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class GridImage extends Table {

    public GridImage() {
        super();

        this.setSize(320f, 320f);
        this.setTouchable(Touchable.enabled);

        // Sets background
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/grid.png")))));
    }


    /**
     * Method for adding an actor in the grid.
     * Intended to be a hit or miss image.
     * @param actor Actor to be added to grid.
     * @param coordinate position to add actor.
     */
    public void add(Actor actor, Coordinate coordinate) {
        this.addActor(actor);
        actor.setWidth(32);
        actor.setHeight(32);

        actor.setX((coordinate.getX() - 1) * 32);
        actor.setY(this.getHeight() - ((coordinate.getY()) * 32));

    }

    public void remove(Coordinate coordinate) {

    }

}
