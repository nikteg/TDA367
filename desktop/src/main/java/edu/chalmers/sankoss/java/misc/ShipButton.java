package edu.chalmers.sankoss.java.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Button representing a ship visually.
 *
 * @author Mikael Malmqvist
 */
public class ShipButton extends ImageButton {
    private Sprite buttonSprite;
    private int length;
    private Direction direction;
    private static float lastPosition = 100;

    public ShipButton() {
        super(new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/HORIZONTAL_ship_large_body.png")))));

        direction = Direction.HORIZONTAL;
        length = 2;

        update();

    }

    public ShipButton(int length) {
        super(new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/HORIZONTAL_ship_large_body.png")))));

        direction = Direction.HORIZONTAL;
        setLength(length);

        update();
    }

    public enum Direction {
        HORIZONTAL, VERTICAL
    }

    /**
     * Calls all updaters for ship.
     */
    public void update() {
        updateTexture();
        updateDimensions();
        updatePosition();

    }

    /**
     * Updates ship with correct texture based on direction.
     */
    public void updateTexture() {
        if (length == 3 || length == 4) {
            setStyle(new ImageButtonStyle(null, null, null, new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/" + direction + "_ship_medium_body.png")))), null, null));

        } else if (length == 2) {
            setStyle(new ImageButtonStyle(null, null, null, new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/" + direction + "_ship_small_body.png")))), null, null));

        } else {
            setStyle(new ImageButtonStyle(null, null, null, new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/" + direction + "_ship_large_body.png")))), null, null));

        }
    }

    /**
     * Updates ship with correct width/height based on direction.
     */
    public void updateDimensions(){
        if(direction == Direction.HORIZONTAL) {
            setWidth(50 * length);

        } else {
            setHeight(50 * length);
        }

    }

    /**
     * Updates ships with correct position based on direction.
     */
    public void updatePosition() {
        setY(0);

        if(direction == Direction.HORIZONTAL) {
            setX(lastPosition + getWidth() + 50);
            lastPosition = getX();
        } else {
            setX(50 * (length + 1));
            lastPosition = 100;
        }

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
