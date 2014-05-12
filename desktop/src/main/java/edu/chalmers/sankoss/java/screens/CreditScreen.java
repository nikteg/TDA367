package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.models.Credit;
import edu.chalmers.sankoss.java.client.SankossClientListener;

public class CreditScreen extends AbstractScreen{

	public CreditScreen(SankossController controller, SankossGame game) {
		super(controller, game);
		model=new Credit();
		
		// TODO Auto-generated constructor stub
	}
	
	private class CreditScreenListener extends ChangeListener {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
