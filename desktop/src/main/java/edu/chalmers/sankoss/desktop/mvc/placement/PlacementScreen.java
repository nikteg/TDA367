package edu.chalmers.sankoss.desktop.mvc.placement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.core.exceptions.IllegalShipCoordinatesException;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.misc.ShipImage;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

/**
 * Screen used when placing the ships. Handles game logic when placing ships,
 * almost like a controller.
 * 
 * @author Daniel Eineving
 */
public class PlacementScreen extends AbstractScreen<PlacementModel, PlacementRenderer> {
    private final float GRID_CELL_WIDTH = 32f;
    private final int GRID_CELL_WIDTH_INT = (int)GRID_CELL_WIDTH;

	public PlacementScreen() {

		SankossClient.getInstance().addListener(new SankossClientListener() {

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

        getRenderer().getGrid().addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                /**
                 * Get coordinate from mouse position
                 */
                Coordinate coord = getCoordinateFromGrid(x, y);

                System.out.println(event.getTarget().getClass());

                // Left click
                if (button == 0 && getRenderer().getGrid().hasFollower()) {
                    int length = getRenderer().getGrid().getFollower().getLength();

                    try {
                        System.out.println("ROTATION" + event.getTarget().getRotation() / 90);
                        System.out.println("ADDAR SHIP" + coord.getX() +":"+ coord.getY() + " " + (coord.getX() + length-1) +":"+ coord.getY());
                        System.out.println("ADDAR SHIP" + coord.getX() +":"+ coord.getY() + " " + (coord.getX() + length-1) +":"+ coord.getY());
                        getModel().addShip(new Ship(coord, new Coordinate(coord.getX() + length - 1, coord.getY())));
                        getRenderer().getGrid().clearFollower();
                    } catch (IllegalShipCoordinatesException e) {
                        System.out.println("NU GICK NÅGOT FEL DU");
                    }

                } else if (button == 1 && getRenderer().getGrid().hasFollower()) {
                    getRenderer().getGrid().rotateFollower();
                } else if (button == 0 && event.getTarget() instanceof ShipImage) {
                    getRenderer().getGrid().setFollower((ShipImage)event.getTarget());
                }

                return false;

            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                if (getRenderer().getGrid().hasFollower()) {
                    getRenderer().getGrid().getFollower().setX(((int) x / GRID_CELL_WIDTH_INT) * GRID_CELL_WIDTH_INT);
                    getRenderer().getGrid().getFollower().setY(((int) y / GRID_CELL_WIDTH_INT) * GRID_CELL_WIDTH_INT);
                }

                return false;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    //getRenderer().getGrid1().getCrosshair().setVisible(false);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //getRenderer().getGrid1().getCrosshair().setVisible(true);
            }
        });

        try {
            getRenderer().getGrid().addShip(new Ship(new Coordinate(1, 1), new Coordinate(2, 1)));
            getRenderer().getGrid().addShip(new Ship(new Coordinate(1, 2), new Coordinate(3, 2)));
            getRenderer().getGrid().addShip(new Ship(new Coordinate(1, 3), new Coordinate(3, 3)));
            getRenderer().getGrid().addShip(new Ship(new Coordinate(1, 4), new Coordinate(4, 4)));
            getRenderer().getGrid().addShip(new Ship(new Coordinate(1, 5), new Coordinate(5, 5)));
        } catch (IllegalShipCoordinatesException e) {
            e.printStackTrace();
        }
    }

    public Coordinate getCoordinateFromGrid(float mx, float my) {
        int cX = (int)mx / GRID_CELL_WIDTH_INT + 1;
        int cY = ((int)getRenderer().getGrid().getHeight() - (int)my) / GRID_CELL_WIDTH_INT + 1;
        return new Coordinate(cX, cY);
    }

	public void shipPlaced(Ship ship) {
		getModel().addShip(ship);
	}

    @Override
    public void show() {
        super.show();

        try {
            shipPlaced(new Ship(new Coordinate(1, 1), new Coordinate(1, 2)));
        } catch (IllegalShipCoordinatesException e) {
            e.printStackTrace();
        }
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