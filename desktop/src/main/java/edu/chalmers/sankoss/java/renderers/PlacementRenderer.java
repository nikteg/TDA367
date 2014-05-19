package edu.chalmers.sankoss.java.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.models.PlacementModel;

import java.util.Observable;

/**
 * AbstractRenderer for the placement of ships.
 * This class will handle all rendering
 * called by the PlacementScreen.
 *
 * @author Mikael Malmqvist
 */
public class PlacementRenderer extends AbstractRenderer {

	TextButton btnReady = new TextButton("Ready", SankossGame.getInstance().getSkin());
	TextButton btnNextFlag = new TextButton(">", SankossGame.getInstance().getSkin());
	TextButton btnPreviousFlag = new TextButton("<", SankossGame.getInstance().getSkin());
	Image grid = new Image(new Texture(Gdx.files.internal("textures/grid.png")));
	Image flag = new Image();
	
    public PlacementRenderer(Observable observable) {
        super(observable);
        PlacementModel model = (PlacementModel) observable;
        
        btnReady.pad(8f);
        btnNextFlag.pad(8f);
        btnPreviousFlag.pad(8f);
        
        getTable().debug();
        
        getTable().add(grid);
        getTable().row();
        
        flag.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(model.getFlagPath()))));
        
        getTable().add(flag);
        getTable().add(btnReady).fillX().pad(8f);
        
        getTable().add(btnNextFlag).pad(8f).bottom().left().expand();
        getTable().add(btnPreviousFlag.pad(8f));
        getStage().addActor(getTable());
        
        
        
        btnReady.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				// TODO Auto-generated method stub
				
			}
		});
        btnPreviousFlag.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				// TODO Auto-generated method stub
				
			}
		});
        btnNextFlag.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				// TODO Auto-generated method stub
				
			}
		});
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
    public void update(Observable object, Object arg) {
    	if(arg.equals("NationalityChanged")){
    		flag.setDrawable(new TextureRegionDrawable(new TextureRegion(
    				new Texture(((PlacementModel)object).getFlagPath()))));
    	}
    }
}