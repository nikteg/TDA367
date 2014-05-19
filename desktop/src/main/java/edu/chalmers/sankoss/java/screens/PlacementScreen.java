package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.Screens;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.PlacementModel;
import edu.chalmers.sankoss.java.renderers.PlacementRenderer;

/**
 * Screen used when placing the ships.
 * Handles game logic when placing ships, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @modified Fredrik Thune
 * @date 3/24/14
 * @modified Daniel Eineving 2014-05-12
 */
public class PlacementScreen extends AbstractScreen<PlacementModel, PlacementRenderer> {

    public PlacementScreen(Class<PlacementModel> model, Class<PlacementRenderer> renderer) {
        super(model, renderer);

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
        	@Override
        	public void playerIsReady(CorePlayer player){
        		getModel().setOpponentReady(true);
        	}
        	@Override
        	public void leftGame(CorePlayer player){
        		Screens.LOBBY.show();
        	}
        });
    }
    
    public void shipPlaced(Ship ship){
    	getModel().addShip(ship);
    }

    @Override
    public void update(float delta) {

    }
}