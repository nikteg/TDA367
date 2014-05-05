package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;
import edu.chalmers.sankoss.java.screens.LobbyScreen;

import java.util.Map;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class LobbyRenderer extends Renderer {

    // Containers
    private WidgetGroup actorPanel;

    // Controllers
    private TextButton joinBtn;
    private TextButton hostBtn;
    private TextButton cancelBtn;
    private TextButton editBtn;
    private Label nameLabel;
    private Label infoLabel;
    private List roomList;
    private Skin skin;

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
    public LobbyRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    public TextButton getJoinBtn() {
        return joinBtn;
    }

    @Override
    public void resize(int width, int height) {
        joinBtn.setPosition(width - WIDTH_OF_BUTTON, 0);
        hostBtn.setPosition(width - WIDTH_OF_BUTTON*2 - 10, 0);
        infoLabel.setX(width - infoLabel.getWidth() - 10);

        topPanel.setY(height - 150);
        middlePanel.setY(bottomPanel.getHeight());
        middlePanel.setHeight(height - bottomPanel.getHeight() - topPanel.getHeight());

        if(roomList != null) {
            roomList.setY(middlePanel.getHeight() - roomList.getHeight() - 20);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);

    }


    @Override
    public void drawControllers(AbstractScreen screen) {

        // Defines variables for visuals
        skin = new Skin();
        actorPanel = new WidgetGroup();

        bottomPanel = new WidgetGroup();
        topPanel = new WidgetGroup();
        middlePanel = new WidgetGroup();

        btnStyle = new TextButton.TextButtonStyle();

        // Configures necessary attributes for buttons
        setButtons();

        btnStyle.font = skin.getFont("default");
        labelStyle.font = skin.getFont("default");

        // Makes buttons and labels with default style of button
        joinBtn = new TextButton("Join", btnStyle);
        joinBtn.setX(600 - WIDTH_OF_BUTTON);
        joinBtn.setY(0);

        hostBtn = new TextButton("Host", btnStyle);
        hostBtn.setX(600 - WIDTH_OF_BUTTON*2 - 10);
        hostBtn.setY(0);

        cancelBtn = new TextButton("Cancel", btnStyle);
        cancelBtn.setX(0);
        cancelBtn.setY(0);

        editBtn = new TextButton("Edit name", btnStyle);
        editBtn.setX(0);
        editBtn.setY(60);

        nameLabel = new Label(currentModel.getClient().getPlayer().getName(), labelStyle);
        nameLabel.setX(10);
        nameLabel.setY(110);

        infoLabel = new Label("Join or host a game", labelStyle);
        infoLabel.setX(600 - 50);
        infoLabel.setY(110);

        bottomPanel.setWidth(800);
        bottomPanel.setHeight(50);
        bottomPanel.setX(0);
        bottomPanel.setY(0);
        bottomPanel.addActor(joinBtn);
        bottomPanel.addActor(hostBtn);
        bottomPanel.addActor(cancelBtn);

        topPanel.setWidth(800);
        topPanel.setHeight(30);
        topPanel.setX(0);
        topPanel.setY(Gdx.graphics.getHeight()-150);
        topPanel.addActor(infoLabel);
        topPanel.addActor(nameLabel);
        topPanel.addActor(editBtn);

        middlePanel.setWidth(800);
        middlePanel.setHeight(800 - topPanel.getHeight() - bottomPanel.getHeight());
        middlePanel.setX(0);
        middlePanel.setY(bottomPanel.getHeight());

        actorPanel.addActor(bottomPanel);
        actorPanel.addActor(middlePanel);
        actorPanel.addActor(topPanel);

        joinBtn.addListener(((LobbyScreen)screen).getJoinButtonListener());
        hostBtn.addListener(((LobbyScreen)screen).getHostButtonListener());
        cancelBtn.addListener(((LobbyScreen)screen).getCancelButtonListener());
        editBtn.addListener(((LobbyScreen)screen).getEditButtonListener());

    }

    public WidgetGroup getActorPanel() {
        return actorPanel;
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

        listStyle = new List.ListStyle();
        listStyle.font = skin.getFont("default");
        listStyle.selection = skin.newDrawable("white", Color.DARK_GRAY);
        listStyle.fontColorSelected = Color.WHITE;
        listStyle.fontColorUnselected = Color.GRAY;

    }

    public void setList(Map<Long, Room> rooms) {
        roomList = new List(rooms.values().toArray(new Room[rooms.size()]), listStyle);

        roomList.setX(50);
        roomList.setY(middlePanel.getHeight() - roomList.getHeight() - 75);

        middlePanel.addActor(roomList);
    }

    public List getRoomList() {
        return roomList;
    }

    public void setNameLabel(String name) {
        nameLabel.setText(name);
    }
}
