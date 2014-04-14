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
import edu.chalmers.sankoss.java.screens.AbstractScreen;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class InGameRenderer extends Renderer {

    private Skin skin;
    private SpriteBatch batch = new SpriteBatch();

    // Controllers
    private Label opponentLabel;
    private Label playerLabel;

    // Containers
    private Table playerTable;
    private Table topTable;
    private Table middleTable;

    /**
     * @inheritdoc
     */
    public InGameRenderer(ScreenModel currentModel) {
        super(currentModel);
        // render();
    }

    @Override
    public void resize(int width, int height) {
        playerTable.setWidth(width);
        topTable.setWidth(width);
        topTable.setY(height - 100);
        middleTable.setWidth(width);
        middleTable.setHeight(height - playerTable.getHeight() - topTable.getHeight());

        render();

    }

    @Override
    public void drawControllers(AbstractScreen screen) {
        skin = new Skin();

        actorPanel = new WidgetGroup();

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
        playerTable.setPosition(0, 0);

        topTable = new Table();
        topTable.setHeight(100f);
        topTable.setWidth(800f);
        topTable.setBackground(skin.newDrawable("tableBack"));
        topTable.setX(0);
        topTable.setY(500);

        middleTable = new Table();
        middleTable.setWidth(800);
        middleTable.setHeight(800 - topTable.getHeight() - playerTable.getHeight());
        middleTable.setX(0);
        middleTable.setY(playerTable.getHeight());

        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        skin.add("default", font);
        labelStyle.font = skin.getFont("default");

        opponentLabel = new Label("Opponent", labelStyle);
        opponentLabel.setX(10);
        opponentLabel.setY(55);

        playerLabel = new Label(screen.getModel().getClient().getPlayer().getName(), labelStyle);
        playerLabel.setX(10);
        playerLabel.setY(100);

        btnStyle = new TextButton.TextButtonStyle();
        // labelStyle = new Label.LabelStyle();

        // Configs buttons
        setButtons();


        btnStyle.font = skin.getFont("default");

        // Adds font to skin

        topTable.addActor(opponentLabel);
        playerTable.addActor(playerLabel);

        actorPanel.addActor(playerTable);
        actorPanel.addActor(topTable);
        actorPanel.addActor(middleTable);

        //TODO: Add listeners and more..
    }

    public WidgetGroup getActorPanel() {
        return actorPanel;
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

    /**
     * @inheritdoc
     */
    @Override
    public void render() {
        // Why isn't this called automatically??
        // Why isn't this called automatically??
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.09f, 0.28f, 0.5f, 1);

        //batch.begin();
    }

}
