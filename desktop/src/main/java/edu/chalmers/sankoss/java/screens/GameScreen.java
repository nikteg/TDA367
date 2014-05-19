package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.CorePlayer;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.GameModel;
import edu.chalmers.sankoss.java.renderers.GameRenderer;

/**
 * Screen used when placing the ships.
 * Handles game logic, like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class GameScreen extends AbstractScreen<GameModel, GameRenderer> {

    public GameScreen(Class<GameModel> model, Class<GameRenderer> renderer) {
        super(model, renderer);

        /**
         * Listener for GameScreen.
         */
        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {

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
                    shotAtOpponent(coordinate, hit);
                }

            }
        });
    }

    public void shotAtYou(Coordinate coordinate, boolean hit) {
        if(hit) {
            youWereHit(coordinate);
        } else {
            youWereMissed(coordinate);
        }
    }

    public void shotAtOpponent(Coordinate coordinate, boolean hit) {
        if(hit) {
            opponentWereHit(coordinate);
        } else {
            opponentWereMissed(coordinate);
        }
    }

    public void youWereHit(Coordinate coordinate) {

        getRenderer().drawPlayerWereHit(coordinate);
    }

    public void youWereMissed(Coordinate coordinate) {

        getRenderer().drawPlayerWereMissed(coordinate);
    }

    public void opponentWereHit(Coordinate coordinate) {

        getRenderer().drawOpponentWereHit(coordinate);
    }

    public void opponentWereMissed(Coordinate coordinate) {

        getRenderer().drawOpponentWereMissed(coordinate);
    }

    @Override
    public void update(float delta) {

    }

}
