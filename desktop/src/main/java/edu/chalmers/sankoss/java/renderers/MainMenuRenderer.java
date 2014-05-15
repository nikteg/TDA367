package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import edu.chalmers.sankoss.java.models.ScreenModel;
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
    private TextButton multiPlayerBtn;
    private TextButton optionBtn;
    private TextButton creditsBtn;
    private TextButton exitBtn;
    private TextButton startGameBtn;
    private Label battleLabel;
    private Label statusLabel;
    private Skin skin;

    private Table table;

    // Dimensions of buttons
    private final int WIDTH_OF_BUTTON = 600;
    private final int HEIGHT_OF_BUTTON = 100;

    /**
     * @inheritdoc
     */
    public MainMenuRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    public TextButton getMultiPlayerBtn() {
        return multiPlayerBtn;
    }

    public TextButton getCreditsBtn() {
        return creditsBtn;
    }

    @Override
    public void resize(int width, int height) {
        table.setWidth(width);
        table.setHeight(height);
    }

    /**
     * This method loops.
     */
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);
    }


    public void drawControllers(AbstractScreen screen) {
    	
        skin = new Skin();

        table = new Table();

        this.screen = screen;

        btnStyle = new TextButton.TextButtonStyle();
        actorPanel = new WidgetGroup(); // Panel to put actors in

        // Configures necessary attributes for buttons
        setButtons();

        // Makes the default styles for buttons and labels
        btnStyle.font = skin.getFont("default");
        labelStyle.font = skin.getFont("default");

        // Makes buttons and labels with default style of button
        multiPlayerBtn = new TextButton("Multiplayer", btnStyle);
        optionBtn = new TextButton("Options", btnStyle);
        creditsBtn = new TextButton("Credits", btnStyle);
        exitBtn = new TextButton("Exit", btnStyle);
        battleLabel = new Label("Battleships", labelStyle);
        statusLabel = new Label("wow, such battle.. Many ship!", labelStyle);

        battleLabel.setX(325);
        battleLabel.setY(550);
        statusLabel.setX(0);
        statusLabel.setY(0);


        //float x = (Gdx.graphics.getWidth() - WIDTH_OF_BUTTON)/2;
        //multiPlayerBtn.setPosition(x, 600/2 + 120);
        multiPlayerBtn.setDisabled(true);
        //optionBtn.setPosition(x, height/2);
        //creditsBtn.setPosition(x, height/2 - 120);
        //exitBtn.setPosition(x, 600/2 - 240);

        statusLabel.addAction(Actions.forever(
                Actions.sequence(
                        Actions.alpha(0.1f, 0.1f),
                        Actions.fadeIn(2f), Actions.fadeOut(2f)
                )
        ));

        table.add(multiPlayerBtn);
        table.row();
        table.add(optionBtn);
        table.row();
        table.add(creditsBtn);
        table.row();
        table.add(exitBtn);

        table.pad(8);
        table.debug();

        multiPlayerBtn.addListener(((MainMenuScreen)screen).getJoinButtonListener());
        optionBtn.addListener(((MainMenuScreen)screen).getOptionsButtonListener());
        creditsBtn.addListener(((MainMenuScreen)screen).getCreditsButtonListener());
        exitBtn.addListener(((MainMenuScreen)screen).getExitButtonListener());

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
        btnStyle.fontColor = Color.WHITE;
        btnStyle.disabledFontColor = Color.GRAY;
        btnStyle.disabled = skin.newDrawable("white", Color.DARK_GRAY);

        skin.add("default", btnStyle);

    }

    public void setStatusLabel(String str) {
        statusLabel.setText(str);

    }


    public Table getTable() {
        return table;
    }
}
