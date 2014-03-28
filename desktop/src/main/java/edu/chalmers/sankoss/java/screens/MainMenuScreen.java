package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.java.Models.MainMenu;
import edu.chalmers.sankoss.java.Renderers.MainMenuRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

/**
 * Screen used at the main menu.
 * Handles game logic at the main menu, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class MainMenuScreen implements Screen, ApplicationListener {

    private MainMenu mainMenu;
    private MainMenuRenderer mainMenuRenderer;
    private SankossGame game;
    private SankossController controller;

    private Stage stage;
    private Skin skin;
    private Skin labelSkin;
    private SpriteBatch batch;
    private TextButton.TextButtonStyle btnStyle;
    private Label.LabelStyle labelStyle;
    private String roomName;

    // Controllers
    private TextButton joinBtn;
    private TextButton hostBtn;
    private TextButton optionBtn;
    private TextButton helpBtn;
    private Label battleLabel;
    private Label statusLabel;

    // Graphics of buttons
    private static final int WIDTH_OF_BUTTON = 600;
    private static final int HEIGHT_OF_BUTTON = 100;

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public MainMenuScreen(SankossController controller, SankossGame game) {
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
        /* New stuff */
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        Table.drawDebug(stage);

        //statusLabel.act(0.5f);
        stage.draw();

        // Test for changing from Main menu to Lobby
        /*if(controller.updateNow()) {
            game.setScreen(controller.getNextScreen(SankossController.ScreenState.MAINMENU, SankossController.ScreenState.LOBBY));

        }*/

        //mainMenuRenderer.render();
    }

    /**
     * Method for when this Screen is set.
     * This method is called automatically when the game sets
     * this Screen as its active Screen. It instantiates its
     * MainMenu and Renderer.
     */
    @Override
    public void show() {
        System.out.println("Welcome to the MainMenu!");
        mainMenu = new MainMenu();
        mainMenuRenderer = new MainMenuRenderer(mainMenu);


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

    // Below is we implement methods for ApplicationListener interface
    @Override
    public void create() {

        // Defines variables for visuals
        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin();
        btnStyle = new TextButton.TextButtonStyle();
        labelStyle = new Label.LabelStyle();

        // Configures necessary attributes for buttons
        setButtons();

        // Sets the stage as input source
        controller.changeInput(stage);

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

        setButtonPos(joinBtn, 100, 420);
        setButtonPos(hostBtn, 100, 300);
        setButtonPos(optionBtn, 100, 180);
        setButtonPos(helpBtn, 100, 60);

        statusLabel.addAction(Actions.forever(
                Actions.sequence(
                        Actions.alpha(0.1f, 0.1f),
                        Actions.fadeOut(0.1f)
                )
        ));

        // Adds actors to stage
        stage.addActor(joinBtn);
        stage.addActor(hostBtn);
        stage.addActor(optionBtn);
        stage.addActor(helpBtn);
        stage.addActor(battleLabel);
        stage.addActor(statusLabel);
        statusLabel.act(20f);
        
        // Adds listener to join button. When clicked Sceen will be changed to LobbyScreen.
        joinBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
               // Changes sceen from this
               game.setScreen(controller.getNextScreen(SankossController.ScreenState.MAINMENU,
                       SankossController.ScreenState.LOBBY));
            }
        });

        // Adds listener to host button. When clicked an input dialog with room name will be
        // prompted.
        hostBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String s) {
                        // TODO: Create room, disable join game
                        roomName = s;
                        statusLabel.setText("Waiting for opponent to join " + s + "..");

                    }

                    @Override
                    public void canceled() {
                        // nothing..
                    }
                }, "Enter room name:", "");

            }
        });

    }

    /**
     * Sets position of button.
     * @param btn Button to place.
     * @param x x-coordinate for button.
     * @param y y-coordinate for button.
     */
    public void setButtonPos(TextButton btn, int x, int y) {
        btn.setPosition(x, y);
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

    /**
     * Resizes necessary variables to fit
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        stage.setViewport( width, height, true );

    }

    @Override
    public void render() {

    }
}
