package edu.chalmers.sankoss.desktop.mvc.gameover;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 */
public class GameOverScreen extends AbstractScreen<GameOverModel, GameOverRenderer> {

    public GameOverScreen() {

        SankossClient.getInstance().addListener(new SankossClientListener(){

        });

        getRenderer().getBackBtn().addListener(new ChangeListener() {

            /**
             * Returns to main menu.
             * @param changeEvent
             * @param actor
             */
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                SankossClient.getInstance().leaveGame();

                changeScreen("mainmenu");
            }
        });
    }

    @Override
    public void update(float delta) {

    }
}
