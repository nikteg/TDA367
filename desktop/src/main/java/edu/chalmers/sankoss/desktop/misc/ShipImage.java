package edu.chalmers.sankoss.desktop.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Button representing a ship visually.
 *
 * @author Mikael Malmqvist
 */
public class ShipImage extends Image {
    private int length;
    Texture texture;
    // private Direction direction;
    // private static float lastPosition = 100;

    public ShipImage() {
        // direction = Direction.HORIZONTAL;


    }

    public ShipImage(int length) {
        super();
        this.length = length;

        setDrawable(getTextureFromLength());

        // update();
    }

    /**
     * Gets texture based on length.
     * @return texture of ship.
     */
    public TextureRegionDrawable getTextureFromLength() {

        return TextureManager.getInstance().getShipTextureMap().get(length);
    }

    public enum Direction {
        HORIZONTAL, VERTICAL
    }

    public void setLength(int length) {
        this.length = length;

    }

    public int getLength() {
        return length;
    }
}
