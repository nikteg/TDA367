package edu.chalmers.sankoss.desktop.mvc.placement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.misc.ShipImage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikteg on 2014-05-21.
 */
public class PlacementGrid extends Table {

    private Map<Ship, Image> flags = new HashMap<Ship, Image>();
    private Texture shipTexture = new Texture(Gdx.files.internal("textures/ship_small.png"));
    private ShipImage follower;

    public PlacementGrid() {
        super();

        this.setSize(320f, 320f);
        this.setTouchable(Touchable.enabled);

        // Sets background
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/grid.png")))));
    }

    public void addShip(Ship ship) {
        Image shipImage = new ShipImage(ship);
        shipImage.setX((ship.getRear().getX() - 1) * 32);
        shipImage.setY(this.getHeight() - ((ship.getRear().getY()) * 32));
        addActor(shipImage);
    }

    public void removeShip(Ship ship) {

    }

    public void setFollower(ShipImage follower) {
        this.follower = follower;
        //this.follower.setTouchable(Touchable.disabled);

        addActor(this.follower);
    }

    public ShipImage getFollower() {
        return follower;
    }

    public void clearFollower() {
        follower = null;
    }

    public void rotateFollower() {
        follower.rotateShip();
    }

    public boolean hasFollower() {
        return follower != null;
    }
}
