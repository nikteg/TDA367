package edu.chalmers.sankoss.java.renderers;

import java.util.LinkedList;

import net.java.games.input.LinuxJoystickPOV;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
	private LinkedList<Label> labels;

	private Skin skin;

	private static final int LINE_SPACING = 40;

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

		setButton();

		backBtb.setX(0);
		backBtb.setY(0);

		actorPanel.addActor(backBtb);

		setLabels();

		backBtb.addListener(((CreditScreen) screen).getBackButtonListener());

	}

	/**
	 * Sets the labels and makes them move
	 */
	private void setLabels() {
		labels = new LinkedList<Label>();

		labels.add(0, new Label("Sankoss", labelStyle));
		labels.add(1, new Label("TDA367 Group 9", labelStyle));
		labels.add(2, new Label("Daniel 'Eineving' Eineving", labelStyle));
		labels.add(3, new Label("Mikael 'Laxen' Malmqvist", labelStyle));
		labels.add(4, new Label("Niklas 'Bipshark' Tegnander", labelStyle));
		labels.add(5, new Label("Fredrik 'Asphalt' Thune", labelStyle));
		labels.add(6,
				new Label("Chalmers University of Technology", labelStyle));
		labels.add(7, new Label("Spring 2014", labelStyle));

		Label temp;
		for (int i = 0; i < labels.size(); i++) {
			temp = labels.get(i);

			temp.setY(-i * temp.getHeight() - i * LINE_SPACING);
			temp.setX((1200 - temp.getWidth()) / 2);

			temp.addAction(Actions.moveTo((1200 - temp.getWidth()) / 2, 2000
					- i * temp.getHeight() - i * LINE_SPACING, 45));

			actorPanel.addActor(temp);
		}
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
