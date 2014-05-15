package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import edu.chalmers.sankoss.java.models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;

import java.util.LinkedList;

/**
 * Renderer for the Credit text
 * 
 * @author Daniel Eineving
 * @date 2014-05-13
 */
public class CreditRenderer extends Renderer {

	private AbstractScreen screen;

	private TextButton backButton;
	private Label creditsLabel;
	private LinkedList<Label> labels;

	private Skin skin;
    private Table table;

	private static final int LINE_SPACING = 40;

	/**
	 * @inheritdoc
	 */
	public CreditRenderer(ScreenModel currentModel) {
		super(currentModel);

        skin = new Skin();
        table = new Table();

        BitmapFont font = new BitmapFont();
        font.scale(1); // Sets font's scale relative to current scale

        // Adds font to skin
        skin.add("default", font);

        labelStyle.font = skin.getFont("default");

        creditsLabel = new Label("Sankoss\n\nTDA367 Group 9\n\nDaniel 'Eineving' Eineving\n\n" +
                "Mikael 'Laxen' Malmqvist\n\nNiklas 'Bipshark' Tegnander\n\nFredrik 'Asfalt' Thune\n\n" +
                "Chalmers University of Technology\n\nSpring 2014", labelStyle);
        creditsLabel.setAlignment(Align.center);
        creditsLabel.setY(-creditsLabel.getHeight());
        //creditsLabel.setY(-creditsLabel.getHeight());
        //backButton = new TextButton("Back", btnStyle);
        //table.row();
        //table.add(backButton);
	}

    public Label getCreditsLabel() {
        return creditsLabel;
    }

    public Table getTable() {
        return table;
    }

	@Override
	public void resize(int width, int height) {
        creditsLabel.setX(Gdx.graphics.getWidth() / 2 - creditsLabel.getWidth() / 2);
		//backBtb.setPosition(0, 0);
	}

	@Override
	public void render() {
        gameCamera.update();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

    @Override
    public void drawControllers(AbstractScreen screen) {

    }
}
