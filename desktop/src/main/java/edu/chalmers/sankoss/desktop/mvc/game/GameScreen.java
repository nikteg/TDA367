package edu.chalmers.sankoss.desktop.mvc.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.desktop.SankossGame;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.misc.GridImage;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

/**
 * Screen used when placing the ships.
 * Handles game logic, like a controller.
 *
 * @author Mikael Malmqvist
 */
public class GameScreen extends AbstractScreen<GameModel, GameRenderer> {

    public GameScreen(Class<GameModel> model, Class<GameRenderer> renderer) {
        super(model, renderer);

        /**
         * Listener for GameScreen.
         */
        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {

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
                getModel().setWon(true);
            }

            /**
             * Method to run if player has lost.
             */
            @Override
            public void looser() {
                getModel().setWon(false);
            }

            @Override
            public void destroyedShip(CorePlayer player, Ship ship) {
                super.destroyedShip(player, ship);
            }

            /**
             * Sets to your turn when getting
             * this package call.
             */
            @Override
            public void turn() {
                getModel().setMyTurn(true);

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
                if(target.equals(SankossGame.getInstance().getClient().getPlayer())) {
                    shotAtYou(coordinate, hit);

                } else {
                    shotAtOpponent(hit);
                }

            }
        });

    }

    public void updateOpponentVisuals() {

        getRenderer().updateOpponentVisuals();
    }

    public void shotAtYou(Coordinate coordinate, boolean hit) {
        if(hit) {
            youWereHit(coordinate);
        } else {
            youWereMissed(coordinate);
        }
    }

    public void shotAtOpponent(boolean hit) {
        if(hit) {
            opponentWereHit();
        } else {
            opponentWereMissed();
        }
    }

    public void youWereHit(Coordinate coordinate) {

        Image hit = new Image(getRenderer().getHitTexture());
        ((GridImage)getRenderer().getGrid2()).add(hit, coordinate);

    }

    public void youWereMissed(Coordinate coordinate) {

        Image miss = new Image(getRenderer().getMissTexture());
        ((GridImage)getRenderer().getGrid2()).add(miss, coordinate);
    }

    public void opponentWereHit() {

        Image hit = new Image(getRenderer().getHitTexture());
        ((GridImage)getRenderer().getGrid1()).add(hit, getModel().getYourShots().get(getModel().getYourShots().size() - 1));
    }

    public void opponentWereMissed() {

        Image miss = new Image(getRenderer().getHitTexture());
        ((GridImage)getRenderer().getGrid1()).add(miss, getModel().getYourShots().get(getModel().getYourShots().size() - 1));
    }

    @Override
    public void show() {
        super.show();

        updateOpponentVisuals();
    }

    @Override
    public void update(float delta) {

    }


}
