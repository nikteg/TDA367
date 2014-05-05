package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import edu.chalmers.sankoss.java.models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;
import edu.chalmers.sankoss.java.screens.WaitingScreen;

/**
 * Renderer for WaitingScreen.
 * Will only draw a label and two buttons.
 *
 * @author Mikael Malmqvist
 * @date 5/5/14
 */
public class WaitingRenderer extends Renderer {

    // Instance of screen to be used when setting listener
    // to start game button
    private AbstractScreen screen;

    // Controllers
    private TextButton startGameBtn;
    private TextButton cancelBtn;
    private Label waitingLabel;
    private Skin skin;

    // Dimensions of buttons
    private final int WIDTH_OF_BUTTON = 150;
    private final int HEIGHT_OF_BUTTON = 50;

    /**
     * @inherticdoc
     */
    public WaitingRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    public Label getWaitingLabel() {
        return waitingLabel;
    }

    public void createStartBtn() {
        startGameBtn = new TextButton("Start Game", btnStyle);
        startGameBtn.setWidth(175);
        startGameBtn.setHeight(50);
        startGameBtn.setPosition(Gdx.graphics.getWidth() - startGameBtn.getWidth(), 0);
        startGameBtn.setX(Gdx.graphics.getWidth() - WIDTH_OF_BUTTON);
        startGameBtn.setY(0);

        startGameBtn.addListener(((WaitingScreen)screen).getStartButtonListener());

        actorPanel.addActor(startGameBtn);
    }

    @Override
    public void resize(int width, int height) {
        waitingLabel.setX(width/2);
        waitingLabel.setY(height/2);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);

    }

    @Override
    public void drawControllers(AbstractScreen screen) {
        this.screen = screen;

        // Defines variables for visuals
        skin = new Skin();
        actorPanel = new WidgetGroup();

        btnStyle = new TextButton.TextButtonStyle();

        // Configures necessary attributes for buttons
        setButtons();

        btnStyle.font = skin.getFont("default");
        labelStyle.font = skin.getFont("default");

        cancelBtn = new TextButton("Cancel", btnStyle);
        cancelBtn.setX(0);
        cancelBtn.setY(0);

        waitingLabel = new Label("Waiting for opponent to join!", labelStyle);
        waitingLabel.setX((Gdx.graphics.getWidth() - WIDTH_OF_BUTTON)/2);
        waitingLabel.setY((Gdx.graphics.getHeight() - HEIGHT_OF_BUTTON)/2);

        waitingLabel.addAction(Actions.forever(
                Actions.sequence(
                        Actions.alpha(0.1f, 0.1f),
                        Actions.fadeIn(2f), Actions.fadeOut(2f)
                )
        ));

        actorPanel.addActor(cancelBtn);
        actorPanel.addActor(waitingLabel);

        cancelBtn.addListener(((WaitingScreen)screen).getCancelButtonListener());
    }

    /**
     * Makes default configuration for a menu button.
     * Sets Pixmap, Skin, BitmapFont and btnStyle.
     */
    public void setButtons() {
        Pixmap pixmap = new Pixmap(WIDTH_OF_BUTTON, HEIGHT_OF_BUTTON, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();

        // Adds Texture with pixmap to skin
        skin.add("white", new Texture(pixmap));


        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        // Adds font to skin
        skin.add("default", font);


        // Configures how the Style of a button should behave and
        // names is "white"
        btnStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        skin.add("default", btnStyle);

    }
}
