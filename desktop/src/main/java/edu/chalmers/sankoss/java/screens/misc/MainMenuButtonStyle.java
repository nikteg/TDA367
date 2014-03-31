package edu.chalmers.sankoss.java.screens.misc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Default style of button for Main menu.
 *
 * @author Mikael Malmqvist
 * @date 3/30/14
 */
public class MainMenuButtonStyle extends TextButton.TextButtonStyle {

    public MainMenuButtonStyle() {
        Skin skin = new Skin();
        this.font = skin.getFont("default");
        this.up = skin.newDrawable("white", Color.DARK_GRAY);
        this.down = skin.newDrawable("white", Color.DARK_GRAY);
        this.checked = skin.newDrawable("white", Color.BLUE);
        this.over = skin.newDrawable("white", Color.LIGHT_GRAY);
    }
}
