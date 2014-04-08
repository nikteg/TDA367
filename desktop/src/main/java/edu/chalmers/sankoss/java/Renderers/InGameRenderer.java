package edu.chalmers.sankoss.java.Renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    // Controllers
    private Label opponentLabel;
    private Label playerLabel;

    // Containers
    private Table playerTable;
    private Table topTable;

    /**
     * @inheritdoc
     */
    public InGameRenderer(ScreenModel currentModel) {
        super(currentModel);
    }

    @Override
    public void resize(int width, int height) {

    }

    /**
     * @inheritdoc
     */
    @Override
    public void render() {

    }

    @Override
    public void drawControllers(AbstractScreen screen) {
        skin = new Skin();

        // Configs buttons
        setButtons();

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

        // Panel with ships to be placed
        topTable = new Table();
        topTable.setHeight(100f);
        topTable.setWidth(800f);
        topTable.setBackground(skin.newDrawable("tableBack"));
        topTable.setPosition(0, 500);

        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        // Adds font to skin
        skin.add("default", font);
        labelStyle.font = skin.getFont("default");

        opponentLabel = new Label("Opponent", labelStyle);
        opponentLabel.setX(10);
        opponentLabel.setY(55);

        playerLabel = new Label("Player #" + screen.getModel().getClient().getPlayer().getID(), labelStyle);
        playerLabel.setX(10);
        playerLabel.setY(100);

        topTable.addActor(opponentLabel);
        playerTable.addActor(playerLabel);

        //TODO: Add listeners and more..
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
}
