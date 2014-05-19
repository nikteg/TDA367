package edu.chalmers.sankoss.java.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chalmers.sankoss.core.Coordinate;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class GridImage extends Container {

    //private Table imageTable = new Table();

    public GridImage() {
        super();

        this.prefWidth(320f);
        this.prefHeight(320f);
        this.minHeight(320f);
        this.minWidth(320f);
        this.maxHeight(320f);
        this.maxWidth(320f);

        this.setSize(320f, 320f);

        // Sets background
        // this.background(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("textures/grid.png")))));
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/grid.png")))));
    }



    public void add(Actor actor, Coordinate coordinate) {
        this.addActor(actor);
        actor.setWidth(32);
        actor.setHeight(32);

        //imageTable.addActor(actor);

        actor.setX(this.getX() + coordinate.getX()*32);
        actor.setY(this.getY() + this.getHeight() - coordinate.getY()*32);

        System.out.println("actor y: " + actor.getY());
    }
}
