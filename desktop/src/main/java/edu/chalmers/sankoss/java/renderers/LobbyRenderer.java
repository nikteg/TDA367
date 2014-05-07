package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
    private ImageButton editBtn;
    private TextField nameField;
    private Label infoLabel;
    private List roomList;
    private Skin skin;

    //Image for edit button
    SpriteDrawable imagePen = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("desktop/src/main/java/assets/textures/pen.png"))));
    SpriteDrawable imageCheck = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("desktop/src/main/java/assets/textures/check.png"))));

    protected List.ListStyle listStyle;

    // Containers
    private WidgetGroup topPanel;
    private WidgetGroup bottomPanel;
    private WidgetGroup middlePanel;

    // Dimensions of buttons
    private final int WIDTH_OF_BUTTON = 150;
    private final int HEIGHT_OF_BUTTON = 50;

    //Table
    private Table table = new Table();

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
        table.setWidth(width);
        table.setHeight(height);
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

        btnStyle = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();

        // Configures necessary attributes for buttons
        setButtons();

        roomList = new List(new String[]{}, listStyle);

        textFieldStyle.font = skin.getFont("textField");
        btnStyle.font = skin.getFont("default");
        labelStyle.font = skin.getFont("default");

        // Makes buttons and labels with default style of button
        joinBtn = new TextButton("Join", btnStyle);
        hostBtn = new TextButton("Host", btnStyle);
        cancelBtn = new TextButton("Cancel", btnStyle);



        editBtn = new ImageButton(imagePen, null, imageCheck);

        nameField = new TextField(currentModel.getClient().getPlayer().getName(), textFieldStyle);
        nameField.setDisabled(true);
        nameField.setMaxLength(16);
        nameField.setRightAligned(true);

        System.out.println(nameField.getHeight());
        infoLabel = new Label("Join or host a game", labelStyle);

        table.add(infoLabel).expandX().left().fillX().top();
        table.add(nameField).expandX().right().top().width(250);
        table.add(editBtn).right().fillX().top().width(44f).height(44f);

        table.row();

        table.add(roomList).colspan(3).expand().fill().padTop(8).padBottom(8);

        table.row();


        table.add(cancelBtn).expandX().left().bottom();
        Table joinHostTable = new Table();

        joinHostTable.add(hostBtn);
        joinHostTable.add(joinBtn).padLeft(8);

        table.add(joinHostTable).expandX().right().bottom().colspan(2);

        table.setHeight(800);
        table.setWidth(1200);
        table.pad(8f);

        //table.debug();


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




        // Adds font to skin
        skin.add("textField", font);

        // Configures how the Style of a textField should behave and
        // names is "white"
        textFieldStyle.cursor = skin.newDrawable("white", Color.LIGHT_GRAY);
        textFieldStyle.cursor.setMinWidth(2f);
        textFieldStyle.selection = skin.newDrawable("white", Color.DARK_GRAY);
        textFieldStyle.fontColor =  Color.WHITE;
        textFieldStyle.focusedBackground = skin.newDrawable("white", Color.GREEN);




        skin.add("textField", textFieldStyle);


    }

    public void setList(Map<Long, Room> rooms) {
        roomList.setItems(rooms.values().toArray(new Room[rooms.size()]));
    }

    public List getRoomList() {
        return roomList;
    }

    public void setNameLabel(String name) {
        nameField.setText(name);
    }

    public TextField getNameField() {
        return nameField;
    }

    public Table getTable() {
        return table;
    }

    public void toggleEditBtn() {
        boolean isChecked = editBtn.isChecked();
        editBtn.setChecked(!isChecked);
    }
}
