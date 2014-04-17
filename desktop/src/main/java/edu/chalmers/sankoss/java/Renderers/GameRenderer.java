package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import edu.chalmers.sankoss.java.Models.ScreenModel;
import edu.chalmers.sankoss.java.misc.ShipButton;
import edu.chalmers.sankoss.java.screens.AbstractScreen;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class GameRenderer extends Renderer{

    private java.awt.Color color = java.awt.Color.WHITE;
    private String land;
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
    private Label opponentNameLabel;


    /**
     * @inheritdoc
     */
    public GameRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    public Label getOpponentNameLabel() {
        return opponentNameLabel;
    }

    @Override
    public void resize(int width, int height) {
        playerTable.setWidth(width);
        topTable.setWidth(width);
        topTable.setY(height - 100);
        middlePanel.setWidth(width);
        middlePanel.setHeight(height - topTable.getHeight() - playerTable.getHeight());
        ships.setWidth(width - 200);


    }

    public Table[] getGrid() {
        return grid;
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

        opponentNameLabel = new Label("Opponent's name", labelStyle);
        opponentNameLabel.setX(10);
        opponentNameLabel.setY(55);

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
                // grid[(i*10)+j].addListener(((GameScreen) screen).getShipBtnListener());
            }
            n++;
            middlePanel.row();
        }


        topTable.addActor(opponentNameLabel);


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

      
    }
}
