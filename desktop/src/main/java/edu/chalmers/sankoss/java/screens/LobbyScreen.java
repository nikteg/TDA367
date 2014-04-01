package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import edu.chalmers.sankoss.core.Room;
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
public class LobbyScreen extends AbstractScreen {

    // Controllers
    private TextButton joinBtn;
    private TextButton cancelBtn;
    private Label lobbyLabel;
    private Label infoLabel;
    private List roomList;

    protected List.ListStyle listStyle;

    // Containers
    private WidgetGroup topPanel;
    private WidgetGroup bottomPanel;
    private WidgetGroup middlePanel;

    // Graphics of buttons
    private static final int WIDTH_OF_BUTTON = 150;
    private static final int HEIGHT_OF_BUTTON = 50;

    /**
     * @inheritdoc
     */
    public LobbyScreen(SankossController controller, SankossGame game) {
        super(controller, game);

        create();

    }


    /**
     * @inheritdoc
     */
    @Override
    public void show() {
        // System.out.println("Welcome to the Lobby!");
        model = new Lobby();
        renderer = new LobbyRenderer(model);
    }

    /**
     * @inheritdoc
     */
    @Override
    public void hide() {

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
     * @inheritdoc
     */
    @Override
    public void create() {

        // Defines variables for visuals
        super.create();
        bottomPanel = new WidgetGroup();
        topPanel = new WidgetGroup();
        middlePanel = new WidgetGroup();

        btnStyle = new TextButton.TextButtonStyle();
        labelStyle = new Label.LabelStyle();
        listStyle = new List.ListStyle();

        // Configures necessary attributes for buttons
        setButtons();

        // Sets the stage as input source
        controller.changeInput(stage);

        // Makes the default styles for buttons and labels
        btnStyle.font = skin.getFont("default");
        labelStyle.font = skin.getFont("default");
        listStyle.font = skin.getFont("default");
        listStyle.selection = skin.newDrawable("white", Color.DARK_GRAY);
        //listStyle.selection = skin.getDrawable("default"); // Important else nullPointerException is thrown

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


        bottomPanel.setWidth(800);
        bottomPanel.setHeight(50);
        bottomPanel.setX(0);
        bottomPanel.setY(0);
        bottomPanel.addActor(joinBtn);
        bottomPanel.addActor(cancelBtn);

        topPanel.setWidth(800);
        topPanel.setHeight(30);
        topPanel.setX(0);
        topPanel.setY(600 - 150);
        topPanel.addActor(infoLabel);
        topPanel.addActor(lobbyLabel);

        Lobby lobby = (Lobby)controller.getModel();

        final Object[] keys = lobby.getKeys();
        final Room[] rooms = lobby.getRooms();
        final String[] roomNames = lobby.getRoomNames(rooms);
        Object[] tempRooms = {"Hubben","Laxens Hideout","Open Oed","Dracos Lair","DunderPatrullen","Johan Korv Horv"};

        roomList = new List(tempRooms, listStyle);

        middlePanel.setWidth(800);
        middlePanel.setHeight(800 - topPanel.getHeight() - bottomPanel.getHeight());
        middlePanel.setX(0);
        middlePanel.setY(bottomPanel.getHeight());
        middlePanel.addActor(roomList);

        roomList.setX(50);
        roomList.setY(middlePanel.getHeight() - roomList.getHeight() - 20);

        // Adds the panels to stage
        stage.addActor(topPanel);
        stage.addActor(bottomPanel);
        stage.addActor(middlePanel);


        // Adds listener to join button. When clicked Sceen will be changed back to MainMenu.
        joinBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {

                // Retrives selected name and matches with room
                String roomName = roomList.getSelection();
                Room roomToJoin = findRoom(rooms, roomName);

                // TODO join roomToJoin

                controller.setPlacementScreen();

            }
        });

        // Adds listener to cancel button. When clicked Sceen will be changed back to MainMenu.
        cancelBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent evt, Actor actor) {

                controller.setMainMenuScreen();

            }
        });

    }

    /**
     * @inheritdoc
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        joinBtn.setPosition(width - WIDTH_OF_BUTTON, 0);
        infoLabel.setX(width - infoLabel.getWidth() - 10);

        topPanel.setY(height - 150);
        middlePanel.setY(bottomPanel.getHeight());
        middlePanel.setHeight(height - bottomPanel.getHeight() - topPanel.getHeight());
        roomList.setY(middlePanel.getHeight() - roomList.getHeight() - 20);

    }

    /**
     * Method for matching a Room with a name.
     * @param rooms array of Rooms to search trough.
     * @param roomName name of Room.
     * @return
     */
    public Room findRoom(Room[] rooms, String roomName) {
        for(Room room : rooms) {
            if(room.getName().equals(roomName)) {
                return room;
            }
        }

        return null;
    }

}
