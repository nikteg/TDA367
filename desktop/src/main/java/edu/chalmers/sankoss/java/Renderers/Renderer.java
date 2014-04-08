package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import edu.chalmers.sankoss.java.Models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;

/**
 * Abstraction for drawing/rendering.
 * Renders the current Screenmodel
 * (MainMenu/Lobby/Placement/Ingame) on the Screen.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public abstract class Renderer {
    protected OrthographicCamera gameCamera;
    protected ScreenModel currentModel;
    protected int width, height;
    protected static Label.LabelStyle labelStyle;
    protected static TextButton.TextButtonStyle btnStyle;
    protected WidgetGroup actorPanel;

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
        labelStyle = new Label.LabelStyle();
        // actorPanel = new WidgetGroup();
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void resize(int width, int height);

    /**
     * Render method to be implemented by each
     * Renderer.
     * Will be called on, when it's time to update
     * the view of the application.
     */
    public abstract void render();

    public void drawActors(Stage stage, Actor actor) {
        stage.addActor(actor);
    }

    /**
     * Method for drawing controllers that wont be interactable.
     */
    public abstract void drawControllers(AbstractScreen screen);

    public WidgetGroup getActorPanel() {
        return actorPanel;
    }

}
