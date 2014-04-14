package edu.chalmers.sankoss.java.Renderers;

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
import edu.chalmers.sankoss.java.Models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;
import edu.chalmers.sankoss.java.screens.MainMenuScreen;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class MainMenuRenderer extends Renderer{

    // Containers
    // private WidgetGroup actorPanel;

    private AbstractScreen screen;

    // Controllers
    private TextButton joinBtn;
    private TextButton hostBtn;
    private TextButton optionBtn;
    private TextButton helpBtn;
    private TextButton startGameBtn;
    private Label battleLabel;
    private Label statusLabel;
    private Skin skin;

    // Dimensions of buttons
    private final int WIDTH_OF_BUTTON = 600;
    private final int HEIGHT_OF_BUTTON = 100;

    /**
     * @inheritdoc
     */
    public MainMenuRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    @Override
    public void resize(int width, int height) {

        // Centers the controllers based on new window size
        float x = (width - WIDTH_OF_BUTTON)/2;
        joinBtn.setPosition(x, height/2 + 120);
        hostBtn.setPosition(x, height/2);
        optionBtn.setPosition(x, height/2 - 120);
        helpBtn.setPosition(x, height/2 - 240);

        battleLabel.setX((width - battleLabel.getWidth()) / 2);
        battleLabel.setY(height/2 + 240);
        statusLabel.setX(0);
        statusLabel.setY(0);

        if(startGameBtn != null) {
            startGameBtn.setPosition(width - startGameBtn.getWidth(), 0);
        }
    }

    /**
     * This method loops.
     */
    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);
    }

    public void createStartButton(){
        startGameBtn = new TextButton("Start Game", btnStyle);
        startGameBtn.setWidth(175);
        startGameBtn.setHeight(50);
        startGameBtn.setPosition(Gdx.graphics.getWidth() - startGameBtn.getWidth(), 0);

        startGameBtn.addListener(((MainMenuScreen)screen).getStartButtonListener());

        actorPanel.addActor(startGameBtn);
    }

    public void drawControllers(AbstractScreen screen) {
        skin = new Skin();

        this.screen = screen;

        btnStyle = new TextButton.TextButtonStyle();
        actorPanel = new WidgetGroup(); // Panel to put actors in

        // Configures necessary attributes for buttons
        setButtons();

        // Makes the default styles for buttons and labels
        btnStyle.font = skin.getFont("default");
        labelStyle.font = skin.getFont("default");

        // Makes buttons and labels with default style of button
        joinBtn = new TextButton("Join Game", btnStyle);
        hostBtn = new TextButton("Host Game", btnStyle);
        optionBtn = new TextButton("Options", btnStyle);
        helpBtn = new TextButton("Help", btnStyle);
        battleLabel = new Label("Battleships", labelStyle);
        statusLabel = new Label("Join or host game room..", labelStyle);

        battleLabel.setX(325);
        battleLabel.setY(550);
        statusLabel.setX(0);
        statusLabel.setY(0);


        float x = (800 - WIDTH_OF_BUTTON)/2;
        joinBtn.setPosition(x, 600/2 + 120);
        hostBtn.setPosition(x, 600/2);
        optionBtn.setPosition(x, 600/2 - 120);
        helpBtn.setPosition(x, 600/2 - 240);

        statusLabel.addAction(Actions.forever(
                Actions.sequence(
                        Actions.alpha(0.1f, 0.1f),
                        Actions.fadeIn(2f), Actions.fadeOut(2f)
                )
        ));

        // Adds actors to table
        actorPanel.addActor(joinBtn);
        actorPanel.addActor(hostBtn);
        actorPanel.addActor(optionBtn);
        actorPanel.addActor(helpBtn);
        actorPanel.addActor(battleLabel);
        actorPanel.addActor(statusLabel);

        joinBtn.addListener(((MainMenuScreen)screen).getJoinButtonListener());
        hostBtn.addListener(((MainMenuScreen)screen).getHostButtonListener());
        helpBtn.addListener(((MainMenuScreen)screen).getHelpButtonListener());

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

    public void setStatusLabel(String str) {
        statusLabel.setText(str);

    }

    /*public WidgetGroup getActorPanel() {
        return actorPanel;
    }*/

}
