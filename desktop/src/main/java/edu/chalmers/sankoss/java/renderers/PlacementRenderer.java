package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.java.client.SankossClientPlayer;
import edu.chalmers.sankoss.java.misc.ShipButton;
import edu.chalmers.sankoss.java.models.Placement;
import edu.chalmers.sankoss.java.models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;
import edu.chalmers.sankoss.java.screens.PlacementScreen;

/**
 * Renderer for the placement of ships.
 * This class will handle all rendering
 * called by the PlacementScreen.
 *
 * @author Mikael Malmqvist
 */
public class PlacementRenderer extends Renderer{

    private int windowWidth;
	private final int GRID_SIDE=10;
    private java.awt.Color color = java.awt.Color.WHITE;
    private String land = "USA";
    private BasePlayer.Nationality nationality = BasePlayer.Nationality.USA;
    private final int WIDTH_OF_SQUARE = 50;
    private final int HEIGHT_OF_SQUARE = 50;
    private Table[] grid = new Table[100];
    private ShipButton follow = null;
	
	private SpriteBatch batch = new SpriteBatch();
    private Skin skin = new Skin();
    private TextButton.TextButtonStyle btnStyle2;

    // Containers
    private Table playerTable;
    private Table topTable;
    private Table middlePanel;

	// controllers
    private Table flag;
    private Label landLabel;
    private Label placeShipsLabel;
    private TextButton nextBtn;
    private TextButton backBtn;
    private TextButton readyBtn;
    private TextButton rotateBtn;

    // ships
    private ShipButton twoShip;
    private ShipButton threeShip1;
    private ShipButton threeShip2;
    private ShipButton fourShip;
    private ShipButton fiveShip;
	
	
    /**
     * @inheritdoc
     */
    public PlacementRenderer(ScreenModel currentModel) {
        super(currentModel);
    }


    public void switchNationality(SankossClientPlayer player, Boolean next) {

        BasePlayer.Nationality nationality = next ? player.getNationality().getNext() : player.getNationality().getLast();

        player.setNationality(nationality);
        color = nationality.getColor();
        land = nationality.getLandName();

    }

    @Override
    public void resize(int width, int height) {
        windowWidth = width;
        playerTable.setWidth(width);
        topTable.setWidth(width);
        topTable.setY(height - 100);
        middlePanel.setWidth(width);
        middlePanel.setHeight(height - topTable.getHeight() - playerTable.getHeight());

        rotateBtn.setX(topTable.getWidth() - rotateBtn.getWidth());
        readyBtn.setX(width - readyBtn.getWidth());


        fiveShip.setY(50 + twoShip.getWidth() + 5);
        fiveShip.setX(Gdx.graphics.getWidth() / 2 + 250);

        twoShip.setY(50);
        twoShip.setX(Gdx.graphics.getWidth() / 2 + 250);
    }

    public Table[] getGrid() {
        return grid;
    }

    /**
     * Method to set flag depending on nationality.
     */
    public void setFlag() {

        Pixmap flagPixmap = new Pixmap(200, 120, Pixmap.Format.RGBA8888);
        flagPixmap.setColor(Color.WHITE);
        flagPixmap.fill();

        skin.add(land, new Texture(flagPixmap));
        flag.setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(nationality.getPath())))));

        landLabel.setText(land);

        playerTable.addActor(landLabel);
        playerTable.addActor(flag);


    }

    /**
     * Method for drawing controllers.
     * @param screen is the active Screen.
     */
    public void drawControllers(AbstractScreen screen) {

        actorPanel = new WidgetGroup();
        btnStyle2 = new TextButton.TextButtonStyle();

        // skin for playerTable
        Pixmap tablePixmap = new Pixmap(800, 150, Pixmap.Format.RGBA8888);
        tablePixmap.setColor(Color.DARK_GRAY);
        tablePixmap.fill();
        skin.add("tableBack", new Texture(tablePixmap));

        Pixmap tablePixmap2 = new Pixmap(800, 150, Pixmap.Format.RGBA8888);
        tablePixmap2.setColor(Color.LIGHT_GRAY);
        tablePixmap2.fill();
        skin.add("tableBack2", new Texture(tablePixmap2));

        Pixmap tablePixmap3 = new Pixmap(800, 150, Pixmap.Format.RGBA8888);
        tablePixmap3.setColor(Color.GRAY);
        tablePixmap3.fill();
        skin.add("tableBack3", new Texture(tablePixmap3));

        // Panel with players info
        playerTable = new Table();
        playerTable.setHeight(150f);
        playerTable.setWidth(800f);
        playerTable.setBackground(skin.newDrawable("tableBack"));
        playerTable.setPosition(0, 0);

        // Panel with ships to be placed
        topTable = new Table();
        topTable.setHeight(100f);
        topTable.setWidth(800f);
        topTable.setBackground(skin.newDrawable("tableBack"));
        topTable.setPosition(0, 500);

        middlePanel = new Table();
        middlePanel.setWidth(800);
        middlePanel.setHeight(600 - topTable.getHeight() - playerTable.getHeight());
        middlePanel.setBackground(skin.newDrawable("tableBack"));
        middlePanel.setPosition(0, playerTable.getHeight());


        flag = new Table();
        flag.setHeight(80f);
        flag.setWidth(170f);
        flag.setX(10);
        flag.setY(35);


        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        // Adds font to skin
        skin.add("default", font);
        labelStyle.font = skin.getFont("default");
        landLabel = new Label(land, labelStyle);
        landLabel.setX(60);
        landLabel.setY(0);

        placeShipsLabel = new Label("Place ships", labelStyle);
        placeShipsLabel.setX(10);
        placeShipsLabel.setY(55);

        setFlag();

        btnStyle = new TextButton.TextButtonStyle();

        // Configures necessary attributes for buttons
        setButtons();


        // Makes the default styles for buttons and labels
        btnStyle2.font = skin.getFont("default");
        btnStyle.font = skin.getFont("default");

        int n = 0;

        // Creates 10x10 grid
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                grid[(i*10)+j] = new Table();

                n++;

                // Adds button to be clicked when placing ships in the placement grid
                if(n % 2 == 0) {
                    grid[(i*10)+j].addActor(new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/LIGHT_water.png"))))));
                    grid[(i*10)+j].setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/LIGHT_water.png")))));

                } else {
                    grid[(i*10)+j].addActor(new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/DARK_water.png"))))));
                    grid[(i*10)+j].setBackground(new SpriteDrawable(new Sprite(new Texture(Gdx.files.classpath("assets/textures/DARK_water.png")))));

                }

                // Adds grid to middlePanel and add a textButton to it
                // This is needed to make it click-able
                middlePanel.add(grid[(i*10)+j]).width(WIDTH_OF_SQUARE).height(HEIGHT_OF_SQUARE);

                grid[(i*10)+j].getChildren().get(0).setSize(50, 50);
                grid[(i*10)+j].addListener(((PlacementScreen) screen).getShipBtnListener());
            }
            n++;
            middlePanel.row();
        }

        nextBtn = new TextButton(">", btnStyle);
        nextBtn.setX(160);
        nextBtn.setY(0);
        nextBtn.setHeight(30);
        backBtn = new TextButton("<", btnStyle);
        backBtn.setX(10);
        backBtn.setY(0);
        backBtn.setHeight(30);
        readyBtn = new TextButton("Ready", btnStyle);
        readyBtn.setHeight(50);
        readyBtn.setWidth(150);
        readyBtn.setX(800 - readyBtn.getWidth());
        readyBtn.setY(0);
        rotateBtn = new TextButton("R", btnStyle);
        rotateBtn.setHeight(30);
        rotateBtn.setWidth(30);
        rotateBtn.setX(600 - rotateBtn.getWidth());
        rotateBtn.setY(100 - rotateBtn.getHeight());

        // Ships to be placed in grid
        twoShip = new ShipButton(2);
        twoShip.addListener(((PlacementScreen) screen).getShip2Listener());
        twoShip.setY(50);
        twoShip.setX(Gdx.graphics.getWidth() / 2 + 250);

        threeShip1 = new ShipButton(3);
        threeShip1.addListener(((PlacementScreen) screen).getShip2Listener());
        threeShip1.setY(50);
        threeShip1.setX(25);
        
        threeShip2 = new ShipButton(3);
        threeShip2.addListener(((PlacementScreen) screen).getShip2Listener());
        threeShip2.setY(50 + threeShip1.getWidth() + 5);
        threeShip2.setX(threeShip1.getWidth() + 5);

        fourShip = new ShipButton(4);
        fourShip.addListener(((PlacementScreen) screen).getShip2Listener());
        fourShip.setY(50 + threeShip1.getWidth()*2 + 5);
        fourShip.setX(25);

        fiveShip = new ShipButton(5);
        fiveShip.addListener(((PlacementScreen) screen).getShip2Listener());
        fiveShip.setY(50 + twoShip.getWidth() + 5);
        fiveShip.setX(Gdx.graphics.getWidth() / 2 + 250);

        topTable.addActor(rotateBtn);
        middlePanel.addActor(twoShip);
        middlePanel.addActor(threeShip1);
        middlePanel.addActor(threeShip2);
        middlePanel.addActor(fourShip);
        middlePanel.addActor(fiveShip);

        playerTable.addActor(nextBtn);
        playerTable.addActor(backBtn);
        playerTable.addActor(readyBtn);

        topTable.addActor(placeShipsLabel);

        nextBtn.addListener(((PlacementScreen) screen).getNextBtnListener());
        backBtn.addListener(((PlacementScreen) screen).getBackBtnListener());
        readyBtn.addListener(((PlacementScreen) screen).getReadyBtnListener());
        rotateBtn.addListener(((PlacementScreen) screen).getRotateBtnListener());

        actorPanel.addActor(playerTable);
        actorPanel.addActor(middlePanel);
        actorPanel.addActor(topTable);
    }

    /**
     * Makes default configuration for a menu button.
     * Sets Pixmap, Skin, BitmapFont and btnStyle.
     */
    public void setButtons() {
        Pixmap pixmap = new Pixmap(20, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();

        // Adds Texture with pixmap to skin
        skin.add("white", new Texture(pixmap));
        skin.add("gray", new Texture(pixmap));

        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        // Adds font to skin
        skin.add("default", font);

        // Configures how the Style of a button should behave and
        // names is "white"
        btnStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
        btnStyle.over = skin.newDrawable("white", Color.WHITE);

        // Configures how the Style of a button should behave and
        // names is "white"
        btnStyle2.up = skin.newDrawable("gray", Color.LIGHT_GRAY);
        btnStyle2.down = skin.newDrawable("gray", Color.LIGHT_GRAY);
        btnStyle2.checked = skin.newDrawable("gray", Color.LIGHT_GRAY);
        btnStyle2.over = skin.newDrawable("gray", Color.WHITE);

        skin.add("default", btnStyle);
        skin.add("default", btnStyle2);

    }

    /**
     * Method for updating all ships in game.
     * Will update textures and Dimensions of all ships.
     */
    public void updateShips() {
        twoShip.update();
        threeShip1.update();
        threeShip2.update();
        fourShip.update();
        fiveShip.update();
    }

    public void setNationality(BasePlayer.Nationality nationality) {
        this.nationality = nationality;
    }

    public WidgetGroup getActorPanel() {
        return actorPanel;
    }

    public void setReadyBtn(Placement.ReadyBtnState state) {
        readyBtn.setText(state.getText());

    }

    public Table getPlayerTable() {
        return playerTable;
    }

    public Table getMiddlePanel() {
        return middlePanel;
    }

    public Table getTopTable() {
        return topTable;
    }

    public Table getFlag() {
        return flag;
    }

    public Label getLandLabel() {
        return landLabel;
    }

    public void setFollow(ShipButton button) {
        follow = button;
    }

    public ShipButton getFollow() {
        return follow;
    }

    public TextButton getReadyBtn() {
        return readyBtn;
    }

    /**
     * Method for switching between Horizontal and Vertical ships.
     */
    public void rotateShips() {
        if(twoShip.getDirection() == ShipButton.Direction.HORIZONTAL) {
            twoShip.setDirection(ShipButton.Direction.VERTICAL);
            threeShip1.setDirection(ShipButton.Direction.VERTICAL);
            threeShip2.setDirection(ShipButton.Direction.VERTICAL);
            fourShip.setDirection(ShipButton.Direction.VERTICAL);
            fiveShip.setDirection(ShipButton.Direction.VERTICAL);
        } else {
            twoShip.setDirection(ShipButton.Direction.HORIZONTAL);
            threeShip1.setDirection(ShipButton.Direction.HORIZONTAL);
            threeShip2.setDirection(ShipButton.Direction.HORIZONTAL);
            fourShip.setDirection(ShipButton.Direction.HORIZONTAL);
            fiveShip.setDirection(ShipButton.Direction.HORIZONTAL);

        }

        updateShips();

    }

    /**
     * Render method which is run continuously throughout
     * the application.
     * This method takes a lot of resources.
     */
    @Override
    public void render() {

        // ShipButton to follow cursor
        if(follow != null) {
            follow.setX(Gdx.input.getX() + 1);
            follow.setY(Gdx.input.getY()*(-1) + playerTable.getHeight() + 414);
        }

    }
}
