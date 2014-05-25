package edu.chalmers.sankoss.desktop.mvc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import edu.chalmers.sankoss.core.core.Coordinate;
import edu.chalmers.sankoss.core.core.CorePlayer;
import edu.chalmers.sankoss.core.core.Ship;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

/**
 * Screen used when placing the ships.
 * Handles game logic, like a controller.
 *
 * @author Mikael Malmqvist
 */
public class GameScreen extends AbstractScreen<GameModel, GameRenderer> {
    private final float GRID_CELL_WIDTH = 32f;
    private final int GRID_CELL_WIDTH_INT = (int)GRID_CELL_WIDTH;

    public GameScreen() {
        /**
         * Listener for GameScreen.
         */
        SankossClient.getInstance().addListener(new SankossClientListener() {

            @Override
            public void playerChangedNat(CorePlayer player) {

            }


            /**
             * Method runs when opponent is set ready.
             * Calls on update opponent visuals.
             * @param player opponent player.
             */
            @Override
            public void playerIsReady(CorePlayer player) {

            }

            /**
             * Method to run if player has won.
             */
            @Override
            public void winner() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        changeScreen("gameover");
                    }
                });
            }

            /**
             * Method to run if player has lost.
             */
            @Override
            public void looser() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        changeScreen("gameover");
                    }
                });
            }

            @Override
            public void destroyedShip(CorePlayer player, Ship ship) {
                if (!player.getID().equals(SankossClient.getInstance().getPlayer().getID()))
                    getRenderer().getGrid1().addShip(ship);
            }

            /**
             * Sets to your turn when getting
             * this package call.
             */
            @Override
            public void turn() {
                getModel().setShootingAllowed(true);
            }

            /**
             * Message when a player has fired.
             * @param target the player that got shot at.
             * @param coordinate coordinate shoot at.
             * @param hit hit or miss.
             */
            @Override
            public void fireResult(CorePlayer target, Coordinate coordinate, boolean hit) {
                super.fireResult(target, coordinate, hit);

                // Determines if you were shot at
                if (target.equals(SankossClient.getInstance().getPlayer())) {
                    getModel().addOpponentShot(new Shot(coordinate, hit ? Shot.State.HIT : Shot.State.MISS));
                } else {
                    getModel().addShot(new Shot(coordinate, hit ? Shot.State.HIT : Shot.State.MISS));
                }
            }
        });

        getRenderer().getGrid1().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                /**
                 * Get coordinate from mouse position
                 */
                Coordinate coord = getCoordinateFromGrid(x, y);

                // Left click
                if (button == 0 && getModel().isShootingAllowed()) {
                    SankossClient.getInstance().fire(coord);
                    getModel().setShootingAllowed(false);
                } else if (button == 1) {
                    getRenderer().getGrid1().toggleFlag(coord);
                }

                return false;

            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                getRenderer().getGrid1().getCrosshair().setX(((int) x / GRID_CELL_WIDTH_INT) * GRID_CELL_WIDTH_INT);
                getRenderer().getGrid1().getCrosshair().setY(((int) y / GRID_CELL_WIDTH_INT) * GRID_CELL_WIDTH_INT);

                return false;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    getRenderer().getGrid1().getCrosshair().setVisible(false);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor toActor) {
                getRenderer().getGrid1().getCrosshair().setVisible(true);
            }
        });

        getModel().setShootingAllowed(false);
    }

    public Coordinate getCoordinateFromGrid(float mx, float my) {
        int cX = (int)mx / GRID_CELL_WIDTH_INT + 1;
        int cY = ((int)getRenderer().getGrid1().getHeight() - (int)my) / GRID_CELL_WIDTH_INT + 1;
        return new Coordinate(cX, cY);
    }

    @Override
    public void show() {
        super.show();

        try {
            getModel().setOpponent(SankossClient.getInstance().getOpponents().get(0));
        } catch(IndexOutOfBoundsException e) {
            e.getStackTrace();
        }

        getRenderer().getOpponentPanel().setLblName(SankossClient.getInstance().getOpponents().get(0).getName());
        getRenderer().getPlayerPanel().setLblName(SankossClient.getInstance().getPlayer().getName());
        getRenderer().getPlayerPanel().setNationality(SankossClient.getInstance().getPlayer().getNationality());

        // Removes all ships before adding new ones
        getRenderer().getGrid1().resetShips();
        getRenderer().getGrid2().resetShips();
        
        // Adding ships
        for (Ship ship : SankossClient.getInstance().getPlayer().getFleet().getShips()) {
            getModel().addShip(ship);
            getRenderer().getGrid2().addShip(ship);
        }
    }

    @Override
    public void update(float delta) {

    }


}
