package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import edu.chalmers.sankoss.java.models.ScreenModel;
import edu.chalmers.sankoss.java.screens.AbstractScreen;
import edu.chalmers.sankoss.java.screens.CreditScreen;

/**
 * Renderer for the Credit text
 * 
 * @author Daniel Eineving
 * @date 2014-05-13
 */
public class CreditRenderer extends Renderer {

	private AbstractScreen screen;

	private TextButton backBtb;
	private Label creditLabel;

	private Skin skin;

	/**
	 * @inheritdoc
	 */
	public CreditRenderer(ScreenModel currentModel) {
		super(currentModel);
	}

	@Override
	public void resize(int width, int height) {
		backBtb.setPosition(0, 0);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.09f, 0.28f, 0.2f, 1);
	}

	@Override
	public void drawControllers(AbstractScreen screen) {
		System.out.println("drawControllers @ CreditRendererererererr!");

		skin = new Skin();
		this.screen = screen;

		setButton();

		btnStyle = new TextButton.TextButtonStyle();
		actorPanel = new WidgetGroup();

		// Makes the default styles for buttons and labels
		btnStyle.font = skin.getFont("default");
		labelStyle.font = skin.getFont("default");

		backBtb = new TextButton("Back", btnStyle);
		creditLabel = new Label("Here we have credits", labelStyle);

		setButton();

		backBtb.setX(0);
		backBtb.setY(0);

		actorPanel.addActor(backBtb);

		backBtb.addListener(((CreditScreen) screen).getBackButtonListener());

	}

	private void setButton() {
		Pixmap pixmap = new Pixmap(50, 50, Pixmap.Format.RGBA8888);
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
		btnStyle.fontColor = Color.WHITE;
		btnStyle.disabledFontColor = Color.GRAY;
		btnStyle.disabled = skin.newDrawable("white", Color.DARK_GRAY);

		skin.add("default", btnStyle);
	}

}
