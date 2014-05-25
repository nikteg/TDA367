package edu.chalmers.sankoss.desktop.mvc.placement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
            public void gameReady() {

                Gdx.app.postRunnable(new Runnable() {

                    @Override
                    public void run() {
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


                // Left click
                if (button == 0 && getRenderer().getGrid().hasFollower()) {
                    try {
                        if (getModel().addShip(getRenderer().getGrid().getFollower().getShip())) {
                            getRenderer().getGrid().clearFollower();
                        }
                    } catch (IllegalShipCoordinatesException e) {
                        System.out.println(e.getCause());
                    }

                } else if (button == 1 && getRenderer().getGrid().hasFollower()) {
                    getRenderer().getGrid().rotateFollower();
                } else if (button == 0 && event.getTarget() instanceof ShipImage) {
                    getModel().removeShip(((ShipImage) event.getTarget()).getShip());
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
        });

        getRenderer().getBtnReady().addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent arg0, Actor arg1) {
                if (getModel().getFleet().getLength() == 5 && !getModel().getUserReady()) {
                    getModel().setUserReady(true);

                    SankossClient.getInstance().playerChangeNat(getModel().getNationality());

                    //Set your fleet on client
                    SankossClient.getInstance().getPlayer().setFleet(getModel().getFleet());

                    // Updates server and tells opponent you are ready
                    SankossClient.getInstance().playerReady();
                }
            }
        });

        getRenderer().getBtnPreviousFlag().addListener(new ChangeListener() {

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

        getRenderer().getBtnNextFlag().addListener(new ChangeListener() {

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
    }

    public Coordinate getCoordinateFromGrid(float mx, float my) {
        int cX = (int)mx / GRID_CELL_WIDTH_INT + 1;
        int cY = ((int)getRenderer().getGrid().getHeight() - (int)my) / GRID_CELL_WIDTH_INT + 1;
        return new Coordinate(cX, cY);
    }

    @Override
    public void show() {
        super.show();

        /*
        try {
            shipPlaced(new Ship(new Coordinate(1, 1), new Coordinate(1, 2)));
        } catch (IllegalShipCoordinatesException e) {
            e.printStackTrace();
        }*/

        try {
            for (int i=1; i<=5; i++) {
                int j = i < 3 ? i + 1 : i;
                Ship ship = new Ship(new Coordinate(1, i), new Coordinate(j, i));
                getModel().addShip(ship);
                getRenderer().getGrid().addShip(ship);
            }
        } catch (IllegalShipCoordinatesException e) {
            e.printStackTrace();
        }

        System.out.println("ADDED SHIPS");
    }

	@Override
	public void update(float delta) {

	}
}