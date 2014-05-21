package edu.chalmers.sankoss.desktop.mvc.placement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.mvc.game.Shot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikteg on 2014-05-21.
 */
public class PlacementGrid extends Table {

    private Map<Ship, Image> flags = new HashMap<Ship, Image>();
    private Texture shipTexture = new Texture(Gdx.files.internal("textures/ship_small.png"));
    private Actor follower = new Image();

    public PlacementGrid() {
        super();

        this.setSize(320f, 320f);
        this.setTouchable(Touchable.enabled);

        // Sets background
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/grid.png")))));
    }

    public void addShip(Ship ship) {
        Image shipImage = new Image(shipTexture);
        shipImage.setX((ship.getRear().getX() - 1) * 32);
        shipImage.setY(this.getHeight() - ((ship.getRear().getY()) * 32));
        //shipImage.setScaling(Scaling.stretch);
        //shipImage.setScaleX(ship.getSize());
        //shipImage.setSize(32f * ship.getSize(), 32f);
        addActor(shipImage);
    }

    public void setFollower(Actor follower) {
        this.follower = follower;
        this.follower.setTouchable(Touchable.disabled);

        addActor(this.follower);
    }

    public Actor getFollower() {
        return follower;
    }

    public void clearFollower() {
        follower = null;
    }

    public void rotateFollower() {
        System.out.println("JAG SNURRAR");
        follower.rotateBy(90f);
    }

    public boolean hasFollower() {
        return follower != null;
    }
}
