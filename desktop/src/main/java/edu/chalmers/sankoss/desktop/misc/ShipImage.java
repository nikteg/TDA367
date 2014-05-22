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
    private Ship ship;

    public ShipImage(int length) {
        super(TextureManager.getInstance().getShipTextureMap().get(length));
        ship = new Ship(new Coordinate(1,1), new Coordinate(length, 1));
        setOrigin(16, 16);
    }

    public Ship getShip() {
       ship.setLocation(new Coordinate((int)getX()/32 + 1, 10 - (int)getY()/32));
       return ship;
    }

    public void rotateShip() {
        ship.rotateLeft();
        setRotation(ship.getRotation().asFloat());
    }

    public int getLength() {
        return ship.getSize();
    }
}
