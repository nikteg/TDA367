package edu.chalmers.sankoss.java.mainMenu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.mvc.AbstractScreen;

/**
 * Screen used at the main menu.
 * Handles game logic at the main menu, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @modified Fredrik Thune, Niklas Tegnander
 * @date 3/24/14
 */
public class MainMenuScreen extends AbstractScreen<MainMenuModel, MainMenuRenderer> {

    public MainMenuScreen() {
        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {

            @Override
            public void connected() {
                getModel().setConnected(true);
            }
        });

        getRenderer().getBtnMultiPlayer().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen("lobby");
            }
        });

        getRenderer().getBtnOptions().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Dialog dialog = new Dialog("Message", SankossGame.getInstance().getSkin()) {

                    {
                        text("Not implemented yet");
                        button("OK");
                    }
                };

                dialog.setMovable(false);
                dialog.show(getRenderer().getStage());
            }
        });

        getRenderer().getBtnCredits().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeScreen("credits");
            }
        });

        getRenderer().getBtnExit().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                exitGame();
            }
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean keyDown(int keyCode) {
        super.keyDown(keyCode);

        /**
         * If debugging is enabled
         */
        if (Gdx.app.getLogLevel() == Application.LOG_DEBUG) {
            if (keyCode == Input.Keys.NUM_1) {
                changeScreen("game");
            }

            if (keyCode == Input.Keys.NUM_2) {
                changeScreen("placement");
            }
        }

        return true;
    }
}
