package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.java.Models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;
import edu.chalmers.sankoss.java.screens.PlacementScreen;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class PlacementRenderer extends Renderer{

	// private Sprite box= new Sprite(new Texture("src/main/java/edu/chalmers/sankoss/java/texures/testSquare.png"));
	
	private final int GRID_SIDE=10;
    private Color color = Color.WHITE;
    private String land = "USA";
	
	private SpriteBatch batch = new SpriteBatch();
    private Skin skin;

	//controllers
    private Table flag;
    private Table playerTable;
	private Label headerLabel;
    private Label landLabel;
    private TextButton nextBtn;
    private TextButton backBtn;
	
	
    /**
     * @inheritdoc
     */
    public PlacementRenderer(ScreenModel currentModel) {
        super(currentModel);
    }


    public void switchNationality(Player player, Boolean next) {

        if(next) {

            switch(player.getNationality()) {
                case JAPAN:
                    // Switch to USA
                    color = Color.WHITE;
                    land = "USA";
                    player.setNationality(Player.Nationality.USA);
                    break;

                case USA:
                    // Switch to ENGLAND
                    color = Color.BLUE;
                    land = "ENG";
                    player.setNationality(Player.Nationality.ENGLAND);
                    break;

                case ENGLAND:
                    // Switch to GERMANY
                    color = Color.RED;
                    land = "GER";
                    player.setNationality(Player.Nationality.GERMANY);
                    break;

                case GERMANY:
                    // Switch to JAPAN
                    color = Color.GREEN;
                    land = "JAP";
                    player.setNationality(Player.Nationality.JAPAN);
                    break;
            }

        } else {

            switch(player.getNationality()) {
                case ENGLAND:
                    // Switch to USA
                    color = Color.WHITE;
                    land = "USA";
                    player.setNationality(Player.Nationality.USA);
                    break;

                case JAPAN:
                    // Switch to ENGLAND
                    color = Color.RED;
                    land = "GER";
                    player.setNationality(Player.Nationality.GERMANY);
                    break;

                case GERMANY:
                    // Switch to GERMANY
                    color = Color.BLUE;
                    land = "ENG";
                    player.setNationality(Player.Nationality.ENGLAND);
                    break;

                case USA:
                    // Switch to JAPAN
                    color = Color.GREEN;
                    land = "JAP";
                    player.setNationality(Player.Nationality.JAPAN);
                    break;

            }
        }

    }

    public void resize(int width, int height) {

    }

    /**
     * Method to set flag depending on nationality.
     */
    public void setFlag() {

        Pixmap flagPixmap = new Pixmap(200, 120, Pixmap.Format.RGBA8888);
        flagPixmap.setColor(color);
        flagPixmap.fill();

        skin.add(land, new Texture(flagPixmap));
        flag.setBackground(skin.newDrawable(land));

        landLabel.setText(land);

        playerTable.addActor(landLabel);
        playerTable.addActor(flag);


    }

    public void drawControllers(AbstractScreen screen) {
        skin = new Skin();

        // skin for playerTable
        Pixmap tablePixmap = new Pixmap(800, 150, Pixmap.Format.RGBA8888);
        tablePixmap.setColor(Color.DARK_GRAY);
        tablePixmap.fill();
        skin.add("tableBack", new Texture(tablePixmap));

        // Panel with players info
        playerTable = new Table();
        playerTable.setHeight(150f);
        playerTable.setWidth(800f);
        playerTable.setBackground(skin.newDrawable("tableBack"));

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

        setFlag();

        btnStyle = new TextButton.TextButtonStyle();

        // Configures necessary attributes for buttons
        setButtons();

        btnStyle.font = skin.getFont("default");

        nextBtn = new TextButton(">", btnStyle);
        nextBtn.setX(160);
        nextBtn.setY(0);
        nextBtn.setHeight(30);
        backBtn = new TextButton("<", btnStyle);
        backBtn.setX(10);
        backBtn.setY(0);
        backBtn.setHeight(30);

        playerTable.addActor(nextBtn);
        playerTable.addActor(backBtn);

        nextBtn.addListener(((PlacementScreen)screen).getNextBtnListener());
        backBtn.addListener(((PlacementScreen) screen).getBackBtnListener());
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

    public Table getPlayerTable() {
        return playerTable;
    }

    public Table getFlag() {
        return flag;
    }

    public Label getLandLabel() {
        return landLabel;
    }

    // TODO: Put this code somewhere else! Method is a loop!
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
        
        //Writes the grid as texures
       
        /*
        batch.begin();
        for(int y = 0; y < GRID_SIDE; y++){
            for(int x = 0; x < GRID_SIDE; x++){
                box.setX(x * 50);
                box.setY(y * 50);
                box.draw(batch);
             }
         }
         batch.end();
        */
      
    }
}
