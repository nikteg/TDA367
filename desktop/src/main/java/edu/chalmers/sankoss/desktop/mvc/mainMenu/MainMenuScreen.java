package edu.chalmers.sankoss.desktop.mvc.mainMenu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.desktop.client.SankossClient;
import edu.chalmers.sankoss.desktop.client.SankossClientListener;
import edu.chalmers.sankoss.desktop.mvc.AbstractScreen;
import edu.chalmers.sankoss.desktop.utils.Common;

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
    	SankossClient.getInstance().addListener(new SankossClientListener() {

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
                Dialog dialog = new Dialog("Message", Common.getSkin()) {

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

            if (keyCode == Input.Keys.NUM_3) {
                changeScreen("gameover");
            }
        }

        return true;
    }
}
