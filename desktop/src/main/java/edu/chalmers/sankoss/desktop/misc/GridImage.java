package edu.chalmers.sankoss.desktop.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.utils.Scaling;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.mvc.game.Shot;

import java.util.HashMap;
import java.util.Map;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class GridImage extends Table {

    private Map<Coordinate, Image> flags = new HashMap<Coordinate, Image>();
    private Texture flagTexture = new Texture(Gdx.files.internal("textures/flag.png"));
    private Texture hitTexture = new Texture(Gdx.files.internal("textures/explosion.png"));
    private Texture missTexture = new Texture(Gdx.files.internal("textures/miss.png"));
    private Texture shipTexture = new Texture(Gdx.files.internal("textures/ship_small.png"));

    private Image crosshair;

    public GridImage() {
        super();

        this.setSize(320f, 320f);
        this.setTouchable(Touchable.enabled);

        // Sets background
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/grid.png")))));
    }

    public void addCrosshair(Image crosshair) {
        this.crosshair = crosshair;

        /**
         * Disable crosshair clicking. Needs to be done to be able to click the grid.
         */
        this.crosshair.setTouchable(Touchable.disabled);

        addActor(crosshair);
    }

    public Image getCrosshair() {
        return crosshair;
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

        if (crosshair != null)
            crosshair.toFront();
    }

    public void toggleFlag(Coordinate coordinate) {
        if (flags.containsKey(coordinate)) {
            removeFlag(coordinate);
        } else {
            addFlag(coordinate);
        }
    }

    private void addFlag(Coordinate coordinate) {
        Image flag = new Image(flagTexture);
        flags.put(coordinate, flag);
        add(flags.get(coordinate), coordinate);
    }

    private void removeFlag(Coordinate coordinate) {
        removeActor(flags.get(coordinate));
        flags.remove(coordinate);
    }

    public void addShot(Shot shot) {
        add(new Image(shot.getState() == Shot.State.HIT ? hitTexture : missTexture), shot.getCoordinate());
    }

    public void addShip(Ship ship) {
        Image shipImage = new Image(shipTexture);
        shipImage.setX((ship.getRear().getX() - 1) * 32);
        shipImage.setY(this.getHeight() - ((ship.getRear().getY()) * 32));
        //shipImage.setScaling(Scaling.stretch);
        //shipImage.setScaleX(ship.getSize());
        //shipImage.setSize(32f * ship.getSize(), 32f);
        addActor(shipImage);

        if (crosshair != null)
            crosshair.toFront();
    }
}
