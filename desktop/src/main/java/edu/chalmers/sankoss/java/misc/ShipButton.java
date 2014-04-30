package edu.chalmers.sankoss.java.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Button representing a ship.
 * This class will later on extend ImageButton
 * instead for a visual ship.
 *
 * @author Mikael Malmqvist
 */
public class ShipButton extends ImageButton {
    private Sprite buttonSprite;
    private int length;
    private Direction direction;

    public ShipButton(String path) {
        super(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("desktop/src/main/java/assets/textures/ship_large_body.png")))));
        direction = Direction.HORIZONTAL;
       // setLength(length);
    }

    public ShipButton(String path, int length) {
        super(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("desktop/src/main/java/assets/textures/ship_large_body.png")))));
        direction = Direction.HORIZONTAL;
        setLength(length);
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

    public void setButtonSprite(Sprite buttonSprite) {
        this.buttonSprite = buttonSprite;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
