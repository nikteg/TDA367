package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Gdx;

import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.Screens;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.PlacementModel;
import edu.chalmers.sankoss.java.renderers.PlacementRenderer;

/**
 * Screen used when placing the ships. Handles game logic when placing ships,
 * almost like a controller.
 * 
 * @author Daniel Eineving
 */
public class PlacementScreen extends
		AbstractScreen<PlacementModel, PlacementRenderer> {

	public PlacementScreen(Class<PlacementModel> model,
			Class<PlacementRenderer> renderer) {
		super(model, renderer);

		SankossGame.getInstance().getClient()
				.addListener(new SankossClientListener() {
					@Override
					public void playerIsReady(CorePlayer player) {
						getModel().setOpponentReady(true);
					}

					@Override
					public void leftGame(CorePlayer player) {
						Screens.LOBBY.show();
					}

					@Override
					public void startedGame(Long ID) {
						Gdx.app.postRunnable(new Runnable() {

							@Override
							public void run() {
								Screens.GAME.show();
							}
						});
					}

				});
	}

	public void shipPlaced(Ship ship) {
		getModel().addShip(ship);
	}

	@Override
	public void update(float delta) {

	}
}