package edu.chalmers.sankoss.desktop.mvc.placement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Fleet;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.mvc.game.PlayerPanel;
import edu.chalmers.sankoss.desktop.utils.Common;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;










import java.util.Observable;

/**
 * AbstractRenderer for the placement of ships. This class will handle all
 * rendering called by the PlacementScreen.
 * 
 * @author Daniel Eineving
 */
public class PlacementRenderer extends AbstractRenderer {
	private Table container = new Table();

	private TextureRegionDrawable greenTextureBackground;
	private TextureRegionDrawable redTextureBackground;

	// Offset for texture to follow cursor
	private int textureXOffset;
	private int textureYOffset;

	TextButton btnReady = new TextButton("Ready", Common.getSkin());
	TextButton btnNextFlag = new TextButton(">", Common.getSkin());
	TextButton btnPreviousFlag = new TextButton("<", Common.getSkin());
	
	Image grid = new Image(new Texture(Gdx.files.internal("textures/grid.png")));
	Image flag = new Image();
	Table bottomTable = new Table();
	PlacementModel model;

	public PlacementRenderer(Observable observable) {
		super(observable);

		model = (PlacementModel) observable;

		btnReady.pad(8f);
		btnNextFlag.pad(8f);
		btnPreviousFlag.pad(8f);

		getTable().debug();

		getTable().add(grid);
		getTable().row();

		flag.setDrawable(new TextureRegionDrawable(new TextureRegion(
				new Texture(model.getNationality().getPath()))));

		bottomTable.add(flag).pad(8f).colspan(2);

		getTable().row();
		bottomTable.add(btnPreviousFlag).pad(8f).fillX();
		bottomTable.add(btnNextFlag).pad(8f);

		bottomTable.add(btnReady).fillX().pad(8f);

		getTable().add(bottomTable).bottom().expand();
		getStage().addActor(getTable());

		btnReady.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {

                // TODO Hard coding done here
                Fleet temp = new Fleet();
                try {
                    temp.add(new Ship(new Coordinate(1, 1), (new Coordinate(2,
                            1))));
                    temp.add(new Ship(new Coordinate(1, 2), (new Coordinate(3,
                            2))));
                    temp.add(new Ship(new Coordinate(1, 3), (new Coordinate(3,
                            3))));
                    temp.add(new Ship(new Coordinate(1, 4), (new Coordinate(4,
                            4))));
                    temp.add(new Ship(new Coordinate(1, 5), (new Coordinate(5,
                            5))));

                    SankossGame.getInstance().getClient().getPlayer().setFleet(temp);
                    model.setFleet(temp);

                    //SankossGame.getInstance().getClient().playerReady(temp);
                } catch (Exception ignore) {

                }

                if(model.getFleet().getLength() == 5 && !model.getUserReady()) {
                    model.setUserReady(true);
                    SankossGame.getInstance().getClient().setReady(true);

                    // Updates server and tells opponent you are ready
                    SankossGame.getInstance().getClient().playerReady(model.getFleet());
                }
			}
		});

		btnPreviousFlag.addListener(new ChangeListener() {

            /**
             * Switches to previous flag.
             * @param arg0
             * @param arg1
             */
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {

                model.setNationality(model.getNationality().getLast());

			}
		});

		btnNextFlag.addListener(new ChangeListener() {

            /**
             * Switches to next flag.
             * @param arg0
             * @param arg1
             */
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {

                model.setNationality(model.getNationality().getNext());
			}
		});

		Pixmap pix = new Pixmap(32, 32, Pixmap.Format.RGBA8888);

		pix.setColor(0, 1f, 0.2f, 0.5f);
		pix.fill();
		greenTextureBackground = new TextureRegionDrawable(new TextureRegion(
				new Texture(pix)));

		pix.setColor(0.8f, 0, 0f, 0.5f);
		pix.fill();
		redTextureBackground = new TextureRegionDrawable(new TextureRegion(
				new Texture(pix)));

		container.setWidth(1 * 32f);
		container.setHeight(3 * 32f);
		container.setX(50f);
		container.setY(50f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.09f, 0.58f, 0.2f, 1);

		if (isOverlapping(container, grid))
			container.setBackground(greenTextureBackground);
		else
			container.setBackground(redTextureBackground);

		textureXOffset = ((int) container.getWidth() / 32) / 2 * 32;
		textureYOffset = ((int) container.getHeight() / 32) / 2 * 32;

		container.setX(((mouseOnGridX()) / 32) * 32 + grid.getX()
				- textureXOffset);
		container.setY((((int) grid.getHeight() - mouseOnGridY()) / 32) * 32
				+ grid.getY() - textureYOffset);

		getStage().act(delta);
		getStage().draw();
		Table.drawDebug(getStage());
	}

	@Override
	public void update(Observable object, Object arg) {
		if (arg.equals("NationalityChanged")) {
			flag.setDrawable(new TextureRegionDrawable(new TextureRegion(
					new Texture(((PlacementModel) object).getNationality()
							.getPath()))));
		}

        if (arg.equals("OpponentReady")) {
			btnReady.setText("Enter Game");
		}

        if (arg.equals("playerReady")) {
			btnReady.setDisabled(true);
		}
	}

	public boolean isOverlapping(Actor act1, Actor act2) {
		int x1 = (int) act1.getX();
		int y1 = (int) act1.getY();
		int w1 = (int) act1.getWidth();
		int h1 = (int) act1.getHeight();

		int x2 = (int) act2.getX();
		int y2 = (int) act2.getY();
		int w2 = (int) act2.getWidth();
		int h2 = (int) act2.getHeight();

		int x1Max = x1 + w1;
		int x2Max = x2 + w2;
		int y1Max = y1 + h1;
		int y2Max = y2 + h2;
		if ((x2 >= x1 && x2Max <= x1Max) || (x1 >= x2 && x1Max <= x2Max)) {
			if ((y2 >= y1 && y2Max <= y1Max) || (y1 >= y2 && y1Max <= y2Max)) {
				return true;
			}
		}
		return false;
	}

	public int mouseOnGridX() {
		return Gdx.input.getX() - (int) grid.getX();
	}

	public int mouseOnGridY() {
		return (Gdx.input.getY() - (int) (Gdx.graphics.getHeight()
				- grid.getY() - grid.getHeight()));
	}

}
