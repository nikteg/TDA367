package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import edu.chalmers.sankoss.java.Models.Lobby;
import edu.chalmers.sankoss.java.Renderers.LobbyRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

/**
 * Screen used at the game lobby when finding a game/room to join.
 * Handles game logic in lobby, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class LobbyScreen implements Screen, ApplicationListener {

    private Lobby lobby;
    private LobbyRenderer lobbyRenderer;
    private SankossGame game;
    private SankossController controller;

    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton.TextButtonStyle btnStyle;
    private Label.LabelStyle labelStyle;

    // Controllers
    private TextButton joinBtn;
    private TextButton cancelBtn;
    private Label lobbyLabel;
    private Label infoLabel;

    // Containers
    private WidgetGroup topPanel;
    private WidgetGroup bottomPanel;

    // Graphics of buttons
    private static final int WIDTH_OF_BUTTON = 150;
    private static final int HEIGHT_OF_BUTTON = 50;

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public LobbyScreen(SankossController controller, SankossGame game) {
        this.controller = controller;
        this.game = game;

        create();

    }

    /**
     * Game loop for the current Screen.
     * This method loops as long this Screen is active.
     * @param delta
     */
    @Override
    public void render(float delta) {
        lobbyRenderer.render();

        /* New stuff */
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        Table.drawDebug(stage);

        stage.draw();
    }

    /**
     * Method for when this Screen is set.
     * This method is called automatically when the game sets
     * this Screen as its active Screen. It instantiates its
     * MainMenu and Renderer.
     */
    @Override
    public void show() {
        // System.out.println("Welcome to the Lobby!");
        lobby = new Lobby();
        lobbyRenderer = new LobbyRenderer(lobby);
    }

    /**
     * Method when this is no longer the active Screen.
     * This method is called automatically when the game sets
     * its active Screen to a different Screen than this.
     */
    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();

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


        // Configure a style for button and name it "default". Skin resources are stored by type,
        // so this doesn't overwrite the font.
        btnStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.checked = skin.newDrawable("white", Color.BLUE);
        btnStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        skin.add("default", btnStyle);

    }


    // Below is we implement methods for ApplicationListener interface
    /**
     * Method to run upon creation of instance.
     * Configs visual controllers and sets listeners.
     */
    @Override
    public void create() {
        // Defines variables for visuals
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin();
        bottomPanel = new WidgetGroup();
        topPanel = new WidgetGroup();

        btnStyle = new TextButton.TextButtonStyle();
        labelStyle = new Label.LabelStyle();

        // Configures necessary attributes for buttons
        setButtons();

        // Sets the stage as input source
        controller.changeInput(stage);

        // Sets the stage as input source
        controller.changeInput(stage);

        // Makes the default styles for buttons and labels
        btnStyle.font = skin.getFont("default");
        labelStyle.font = skin.getFont("default");

        // Makes buttons and labels with default style of button
        joinBtn = new TextButton("Join", btnStyle);
        joinBtn.setX(600 - WIDTH_OF_BUTTON);
        joinBtn.setX(0);
        cancelBtn = new TextButton("Cancel", btnStyle);
        cancelBtn.setX(0);
        cancelBtn.setY(0);
        lobbyLabel = new Label("Game Lobby", labelStyle);
        lobbyLabel.setX(10);
        lobbyLabel.setY(110);
        infoLabel = new Label("Select room to join", labelStyle);
        infoLabel.setX(600 - 50);
        infoLabel.setY(110);

        bottomPanel.addActor(joinBtn);
        bottomPanel.addActor(cancelBtn);
        bottomPanel.setX(0);
        bottomPanel.setY(0);

        topPanel.addActor(infoLabel);
        topPanel.addActor(lobbyLabel);
        topPanel.setX(0);
        topPanel.setY(600 - 150);
        //topPanel.setBounds();

        // Adds the panels to stage
        stage.addActor(topPanel);
        stage.addActor(bottomPanel);

    }

    /**
     * Resizes necessary variables to fit
     * @param width is the new width of the window
     * @param height is the new height of the window
     */
    @Override
    public void resize(int width, int height) {
        stage.setViewport( width, height, true );

        joinBtn.setPosition(width - WIDTH_OF_BUTTON, 0);
        infoLabel.setX(width - infoLabel.getWidth() - 10);

        topPanel.setX(0);
        topPanel.setY(height - 150);


    }

    @Override
    public void render() {

    }

}
