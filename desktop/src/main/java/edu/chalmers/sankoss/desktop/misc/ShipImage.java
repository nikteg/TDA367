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

    public ShipImage(Ship ship) {
        super(TextureManager.getInstance().getShipTextureMap().get(ship.getSize()));
        System.out.println("EN SHIPimage har skapats!" + ship.getRear() + ship.getFront());
        this.ship = ship;
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
