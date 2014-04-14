package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.java.Models.Placement;
import edu.chalmers.sankoss.java.Models.ScreenModel;
import edu.chalmers.sankoss.java.misc.ShipButton;
import edu.chalmers.sankoss.java.screens.AbstractScreen;
import edu.chalmers.sankoss.java.screens.PlacementScreen;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class PlacementRenderer extends Renderer{

    // private Sprite box= new Sprite(new Texture("src/main/java/edu/chalmers/sankoss/java/texures/testSquare.png"));

    private int windowWidth;
	private final int GRID_SIDE=10;
    private java.awt.Color color = java.awt.Color.WHITE;
    private String land = "USA";
    private final int WIDTH_OF_SQUARE = 50;
    private final int HEIGHT_OF_SQUARE = 50;
    private Table[] grid = new Table[100];
    private ShipButton follow = null;
	
	private SpriteBatch batch = new SpriteBatch();
    private Skin skin = new Skin();

    // Containers
    private Table playerTable;
    private Table topTable;
    private Table middlePanel;
    private WidgetGroup ships;

	// controllers
    private Table flag;
	private Label headerLabel;
    private Label landLabel;
    private Label placeShipsLabel;
    private TextButton nextBtn;
    private TextButton backBtn;
    private TextButton readyBtn;
    private TextButton rotateBtn;

    // Less temporary ships
    private ShipButton twoShip;
    private ShipButton threeShip;
    private ShipButton fourShip;
    private ShipButton fiveShip;
	
	
    /**
     * @inheritdoc
     */
    public PlacementRenderer(ScreenModel currentModel) {
        super(currentModel);
    }


    public void switchNationality(Player player, Boolean next) {

        Player.Nationality nationality = next ? player.getNationality().getNext() : player.getNationality().getLast();

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
        ships.setWidth(width - 200);

        rotateBtn.setX(topTable.getWidth() - rotateBtn.getWidth());
        readyBtn.setX(width - readyBtn.getWidth());


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
        flag.setBackground(skin.newDrawable(land));

        landLabel.setText(land);

        playerTable.addActor(landLabel);
        playerTable.addActor(flag);


    }

    public void drawControllers(AbstractScreen screen) {
        //skin = new Skin();

        actorPanel = new WidgetGroup();

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
        middlePanel.setBackground(skin.newDrawable("tableBack2"));
        middlePanel.setPosition(0, playerTable.getHeight());


        // This is where ships to pick from will be
        // TODO: REMOVE THIS?!
        ships = new WidgetGroup();
        ships.setHeight(100f);
        ships.setWidth(600f);
        ships.setPosition(200, 0);

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

        btnStyle.font = skin.getFont("default");

        int n = 0;

        // Creates 10x10 grid
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                grid[(i*10)+j] = new Table();

                n++;

                // Colorizes every second square with a different gray based on the tableBack
                if(n % 2 == 0) {
                    grid[(i*10)+j].setBackground(skin.newDrawable("tableBack"));
                } else {
                    grid[(i*10)+j].setBackground(skin.newDrawable("tableBack3"));
                }

                // Adds grid to middlePanel and add a textButton to it
                // This is needed to make it click-able
                middlePanel.add(grid[(i*10)+j]).width(WIDTH_OF_SQUARE).height(HEIGHT_OF_SQUARE);
                grid[(i*10)+j].addActor(new TextButton(i + "," + j, btnStyle));
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
        // These should be ImageButtons later on..
        twoShip = new ShipButton("TWOT", btnStyle, 2);
        twoShip.setX(175);
        twoShip.setY(50);
        twoShip.addListener(((PlacementScreen) screen).getShip2Listener());
        threeShip = new ShipButton("THREETH", btnStyle, 3);
        threeShip.setX(450);
        threeShip.setY(50);
        threeShip.addListener(((PlacementScreen) screen).getShip2Listener());
        fourShip = new ShipButton("FOURFOURFO", btnStyle, 4);
        fourShip.setX(175);
        fourShip.addListener(((PlacementScreen) screen).getShip2Listener());
        fiveShip = new ShipButton("FIVEFIVEFIVEFIVE", btnStyle, 5);
        fiveShip.setX(450);
        fiveShip.addListener(((PlacementScreen) screen).getShip2Listener());

        topTable.addActor(rotateBtn);
        topTable.addActor(twoShip);
        topTable.addActor(threeShip);
        topTable.addActor(fourShip);
        topTable.addActor(fiveShip);

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

    public WidgetGroup getActorPanel() {
        return actorPanel;
    }

    public void setReadyBtn(Placement.ReadyBtnState state) {
        switch(state) {
            case READY:
                readyBtn.setText("Ready");
                break;

            case WAITING:
                readyBtn.setText("Waiting..");
                break;

            case ENTER:
                readyBtn.setText("Enter");
                break;
        }
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

    /**
     * Method for switching between Horizontal and Vertical ships
     */
    public void rotateShips() {
        if(twoShip.getDirection() == ShipButton.Direction.HORIZONTAL) {
            twoShip.setDirection(ShipButton.Direction.VERTICAL);
            twoShip.setText(twoShip.getText() + " -R");
            threeShip.setDirection(ShipButton.Direction.VERTICAL);
            threeShip.setText(threeShip.getText() + " -R");
            fourShip.setDirection(ShipButton.Direction.VERTICAL);
            fourShip.setText(fourShip.getText() + " -R");
            fiveShip.setDirection(ShipButton.Direction.VERTICAL);
            fiveShip.setText(fiveShip.getText() + " -R");
        } else {
            twoShip.setDirection(ShipButton.Direction.HORIZONTAL);
            twoShip.setText("TWOT");
            threeShip.setDirection(ShipButton.Direction.HORIZONTAL);
            threeShip.setText("THREETH");
            fourShip.setDirection(ShipButton.Direction.HORIZONTAL);
            fourShip.setText("FOURFOURFO");
            fiveShip.setDirection(ShipButton.Direction.HORIZONTAL);
            fiveShip.setText("FIVEFIVEFIVEFIVE");
        }
    }


    // TODO: Put this code somewhere else! Method is a loop - it's a trap!
    @Override
    public void render() {
    	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.5f, 1);
        Skin skin = new Skin();
        
        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        // Adds font to skin
        skin.add("default", font);
        
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = skin.getFont("default");
        
        headerLabel = new Label("BATTLURUSHIPURU!!!", lblStyle);

        batch.begin();
        headerLabel.setPosition(0, 510);
        headerLabel.draw(batch, 1);
        batch.end();

        // ShipButton to follow cursor
        if(follow != null) {
            follow.setX(Gdx.input.getX());
            follow.setY(follow.getY()-Gdx.input.getDeltaY());
        }
        //Writes the grid as texures

        /*batch.begin();
        for(int y = 0; y < GRID_SIDE; y++){
            for(int x = 0; x < GRID_SIDE; x++){
                box.setX(windowWidth/2 - box.getWidth()*5 + box.getWidth()*x);
                box.setY(y * 50);
                box.draw(batch);
             }
         }
         batch.end();*/

      
    }
}
