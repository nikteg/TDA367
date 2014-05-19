package edu.chalmers.sankoss.java.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class FlagImage extends Image {

    public FlagImage() {
        super(new Texture(Gdx.files.internal("textures/flag.png")));

    }
}
