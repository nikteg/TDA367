package edu.chalmers.sankoss.java.misc;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Button representing a ship.
 * This class will later on extend ImageButton
 * instead for a visual ship.
 *
 * @author Mikael Malmqvist
 */
public class ShipButton extends TextButton {
    private Sprite buttonSprite;
    private int length;
    private Direction direction;

    public ShipButton(String text, Skin skin) {
        super(text, skin);
        direction = Direction.HORIZONTAL;
       // setLength(length);
    }

    public ShipButton(String text, TextButtonStyle style, int length) {
        super(text, style);
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
