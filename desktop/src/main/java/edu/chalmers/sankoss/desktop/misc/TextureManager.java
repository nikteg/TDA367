package edu.chalmers.sankoss.desktop.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling textures.
 * Other classes will call this to handle their textures.
 *
 * @author Mikael Malmqvist
 */
public class TextureManager {
    private static TextureManager instance;

    private Map<Integer, TextureRegionDrawable> shipTextureMap = new HashMap<Integer, TextureRegionDrawable>();

    public TextureManager() {
        shipTextureMap.put(2, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/ship_small.png")))));
        shipTextureMap.put(3, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/ship_medium.png")))));
        shipTextureMap.put(4, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/ship_large.png")))));
        shipTextureMap.put(5, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/ship_biggest.png")))));

    }

    public static TextureManager getInstance(){
        // Checks so instance isn't null
        if(instance == null) {
            instance = new TextureManager();
        }

        return instance;
    }

    public Map<Integer, TextureRegionDrawable> getShipTextureMap() {
        return shipTextureMap;
    }

}
