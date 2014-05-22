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
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Fleet;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.mvc.AbstractRenderer;
import edu.chalmers.sankoss.desktop.utils.Common;

import java.beans.PropertyChangeEvent;

/**
 * AbstractRenderer for the placement of ships. This class will handle all
 * rendering called by the PlacementScreen.
 * 
 * @author Daniel Eineving
 */
public class PlacementRenderer extends AbstractRenderer<PlacementModel> {
	private Table container = new Table();

	private TextureRegionDrawable greenTextureBackground;
	private TextureRegionDrawable redTextureBackground;

	// Offset for texture to follow cursor
	private int textureXOffset;
	private int textureYOffset;

	TextButton btnReady = new TextButton("Ready", Common.getSkin());
	TextButton btnNextFlag = new TextButton(">", Common.getSkin());
	TextButton btnPreviousFlag = new TextButton("<", Common.getSkin());

    PlacementGrid grid = new PlacementGrid();
	Image flag = new Image();
	Table bottomTable = new Table();

	public PlacementRenderer(PlacementModel model) {
		super(model);

		btnReady.pad(8f);
		btnNextFlag.pad(8f);
		btnPreviousFlag.pad(8f);

		//getTable().debug();

		getTable().add(grid).expand();
		getTable().row();

		flag.setDrawable(new TextureRegionDrawable(new TextureRegion(
				new Texture(model.getNationality().getPath()))));

		bottomTable.add(flag).pad(8f);

		getTable().row();
		bottomTable.add(btnPreviousFlag).pad(8f).fillX();
		bottomTable.add(btnNextFlag).pad(8f);

		bottomTable.add(btnReady).fillX().pad(8f);

		getTable().add(bottomTable).bottom().expand();
		getStage().addActor(getTable());

		btnReady.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
                if(getModel().getFleet().getLength() == 5 && !getModel().getUserReady()) {
                    getModel().setUserReady(true);
                    SankossClient.getInstance().setReady(true);

                    // Updates server and tells opponent you are ready
                    SankossClient.getInstance().playerReady(getModel().getFleet());
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

                getModel().setNationality(getModel().getNationality().getLast());

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

                getModel().setNationality(getModel().getNationality().getNext());
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

		getStage().act(delta);
		getStage().draw();
		Table.drawDebug(getStage());
	}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("NationalityChanged")) {
            CorePlayer.Nationality msg = (CorePlayer.Nationality)evt.getNewValue();
            flag.setDrawable(new TextureRegionDrawable(new TextureRegion(
                    new Texture((msg.getPath())))));
        }

        if (evt.getPropertyName().equals("OpponentReady")) {
            boolean msg = (boolean)evt.getNewValue();
            btnReady.setText("Enter Game");
        }

        if (evt.getPropertyName().equals("playerReady")) {
            boolean msg = (boolean)evt.getNewValue();
            btnReady.setDisabled(true);
        }

        if (evt.getPropertyName().equals("ship_added")) {
            Ship ship = (Ship)evt.getNewValue();
        }
    }

    public PlacementGrid getGrid() {
        return grid;
    }

}
