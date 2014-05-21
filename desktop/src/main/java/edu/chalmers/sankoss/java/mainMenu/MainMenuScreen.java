package edu.chalmers.sankoss.java.mainMenu;

import com.badlogic.gdx.Input;

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

    public MainMenuScreen(Class<MainMenuModel> model, Class<MainMenuRenderer> renderer) {
        super(model, renderer);

        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            /* DO STUFF */

            @Override
            public void connected() {
                getModel().setConnected(true);
            }
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean keyDown(int keyCode) {
        super.keyDown(keyCode);
        if (keyCode == Input.Keys.Q){
        	getProptertyChangeSupport().firePropertyChange("ShowGame", true, false);
        }
        if (keyCode == Input.Keys.W){
        	getProptertyChangeSupport().firePropertyChange("ShowPlacement", true, false);
        }
        System.out.println("NU TRYCKTE DU PÅ EN KNAPP: " + keyCode);

        return true;
    }
}