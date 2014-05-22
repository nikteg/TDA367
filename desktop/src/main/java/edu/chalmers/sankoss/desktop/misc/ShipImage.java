package edu.chalmers.sankoss.desktop.misc;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.Ship;

/**
 * Button representing a ship visually.
 *
 * @author Mikael Malmqvist
 */
public class ShipImage extends Image {
    private int length;

    public ShipImage(int length) {
        super(TextureManager.getInstance().getShipTextureMap().get(length));
        this.length = length;

        setOrigin(16, 16);
    }

    public int getLength() {
        return length;
    }

    public Ship getShip() {
        System.out.println(getRotation());
        return new Ship(new Coordinate((int)getX(), (int)getY()), new Coordinate(1,1));
    }

    public void rotateShip() {
        rotateBy(90f);
    }
}
