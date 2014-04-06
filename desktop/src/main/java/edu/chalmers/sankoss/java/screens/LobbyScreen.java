package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.Models.Lobby;
import edu.chalmers.sankoss.java.Renderers.LobbyRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;

import java.util.*;

/**
 * Screen used at the game lobby when finding a game/room to join.
 * Handles game logic in lobby, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class LobbyScreen extends AbstractScreen implements SankossClientListener{

    // private Lobby lobby;
    private Object[] keys;
    private Room[] rooms;
    private String[] roomNames;
    private Map<Long, Room> gameRooms;

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

    // Dimensions of buttons
    private final int WIDTH_OF_BUTTON = 150;
    private final int HEIGHT_OF_BUTTON = 50;

    /**
     * @inheritdoc
     */
    public LobbyScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new Lobby();
        model.getClient().addListener(this);
        renderer = new LobbyRenderer(model);
        create();

    }


    /**
     * @inheritdoc
     */
    @Override
    public void show() {

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


        // Configures how the Style of a button should behave and
        // names is "white"
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

        //this.lobby = (Lobby)controller.getModel();

        model.getClient().fetchRooms();

        //Object[] tempRooms = {"                                                              "};

        //roomList = new List(tempRooms, listStyle);

        middlePanel.setWidth(800);
        middlePanel.setHeight(800 - topPanel.getHeight() - bottomPanel.getHeight());
        middlePanel.setX(0);
        middlePanel.setY(bottomPanel.getHeight());
        //middlePanel.addActor(roomList);

        // Adds the panels to stage
        renderer.drawActors(stage, topPanel);
        renderer.drawActors(stage, bottomPanel);
        renderer.drawActors(stage, middlePanel);

        // Adds listener to buttons
        joinBtn.addListener(new JoinButtonListener());
        cancelBtn.addListener(new CancelButtonListener());

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

        if(roomList != null) {
            roomList.setY(middlePanel.getHeight() - roomList.getHeight() - 20);
        }

    }

    private class JoinButtonListener extends ChangeListener{
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            // Retrives selected name and matches with room
            String roomName = roomList.getSelection();
            Room roomToJoin = model.getRoomByName(roomName, gameRooms);
            System.out.println("Selected room: #" + roomToJoin.getName());

            model.getClient().joinRoom(roomToJoin.getID());
            controller.setPlacementScreen();
        }
    }

    private class CancelButtonListener extends ChangeListener{
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            controller.setMainMenuScreen();
        }
    }


    public void connected(Long playerID) {
        System.out.print("You are connected");
    }

    /**
     * Method for getting active rooms on server.
     * @param rooms Rooms on Server to join.
     */
    public void fetchedRooms(Map<Long, Room> rooms) {
        this.gameRooms = rooms;

        for(Room room : rooms.values()) {
            System.out.println("Fetched room #" + room.getName());

        }

        // lobby = new Lobby(this.gameRooms);
        model.setRoomMap(gameRooms);

        // Fills visual list with rooms
        this.roomList = new List(rooms.values().toArray(new Room[rooms.size()]), listStyle);

        this.roomList.setX(50);
        this.roomList.setY(middlePanel.getHeight() - roomList.getHeight() - 20);

        this.middlePanel.addActor(roomList);
    }

    public void createdRoom(Long roomID) {

    }

    public void joinedRoom(Player player) {

    }

    public void startedGame(Long gameID, java.util.List<Player> players) {

    }

    public void gameReady() {

    }

    public void playerIsReady(Player player) {

    }

    public void turn() {

    }

    public void fireResult(Long gameID, Player target, Coordinate coordinate, boolean hit) {

    }

    public void destroyedShip(Player player, Ship ship) {

    }

    public void disconnected() {

    }
}
