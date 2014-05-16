package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.java.SankossGame;
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
public class PlacementScreen extends AbstractScreen<PlacementRenderer, PlacementModel> {

    public PlacementScreen() {
        setModel(new PlacementModel());
        setRenderer(new PlacementRenderer(getModel()));

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */
        });
    }

    @Override
    public void update(float delta) {

    }
}