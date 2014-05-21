package edu.chalmers.sankoss.desktop.mvc.placement;

import com.badlogic.gdx.Gdx;

import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

/**
 * Screen used when placing the ships. Handles game logic when placing ships,
 * almost like a controller.
 * 
 * @author Daniel Eineving
 */
public class PlacementScreen extends
		AbstractScreen<PlacementModel, PlacementRenderer> {

	public PlacementScreen() {

		SankossClient.getInstance()
				.addListener(new SankossClientListener() {

					/**
					 * Method runs when opponent is ready.
					 * 
					 * @param player
					 *            the opponent.
					 */
					@Override
					public void playerIsReady(CorePlayer player) {
						if (player != null) {
							getModel().setOpponentReady(true);
						}
					}

					@Override
					public void leftGame(CorePlayer player) {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                changeScreen("lobby");
                            }
                        });
					}

					@Override
					public void startedGame(Long ID) {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                changeScreen("placement");
                            }
                        });

					}

					@Override
					public void gameReady() {

						Gdx.app.postRunnable(new Runnable() {

							@Override
							public void run() {
								// TODO: Check if ready to enter
								changeScreen("game");
							}
						});
					}

				});
	}

	public void shipPlaced(Ship ship) {
		getModel().addShip(ship);

	}

	/**
	 * Method for setting user as ready. If user has placed his five ships and
	 * isn't already ready, set as ready and notify server.
	 */
	public void setReady() {

		if (getModel().getFleet().getLength() == 5
				&& !getModel().getUserReady()) {
			getModel().setUserReady(true);
			SankossClient.getInstance().setReady(true);

			// Updates server and tells opponent you are ready
			SankossClient.getInstance()
					.playerReady(getModel().getFleet());
		}
	}

	@Override
	public void update(float delta) {

	}
}