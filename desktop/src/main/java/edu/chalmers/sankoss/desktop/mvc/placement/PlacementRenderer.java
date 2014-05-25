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
import edu.chalmers.sankoss.core.core.CorePlayer;
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
	}

    public TextButton getBtnReady() {
        return btnReady;
    }

    public TextButton getBtnNextFlag() {
        return btnNextFlag;
    }

    public TextButton getBtnPreviousFlag() {
        return btnPreviousFlag;
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

        if (evt.getPropertyName().equals("reset")) {
            btnReady.setText("Ready");
            btnReady.setDisabled(false);
            getGrid().resetShips();
        }

        if (evt.getPropertyName().equals("OpponentReady")) {
            boolean msg = (boolean)evt.getNewValue();
            btnReady.setText("Enter Game");
        }

        if (evt.getPropertyName().equals("playerReady")) {
            boolean msg = (boolean)evt.getNewValue();
            btnReady.setDisabled(true);
        }
    }

    public PlacementGrid getGrid() {
        return grid;
    }

}
