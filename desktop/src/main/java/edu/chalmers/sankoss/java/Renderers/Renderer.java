package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import edu.chalmers.sankoss.java.Models.ScreenModel;

/**
 * Abstraction for drawing/rendering.
 * Renders the current Screenmodel
 * (MainMenu/Lobby/Placement/Ingame) on the Screen.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public abstract class Renderer {
    private OrthographicCamera gameCamera;
    private ScreenModel currentModel;
    private int width, height;

    /**
     * Sets initial variables.
     * Sets the current Model for the Screen the
     * Renderer belongs to and the gameCamera.
     * @param currentModel is the current Model of
     *                     the Screen.
     */
    public Renderer(ScreenModel currentModel) {
        this.currentModel = currentModel;
        this.gameCamera = new OrthographicCamera();
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Render method to be implemented by each
     * Renderer.
     * Will be called on, when it's time to update
     * the view of the application.
     */
    public abstract void render();
}
