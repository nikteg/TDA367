package edu.chalmers.sankoss.java2.screens;

import com.badlogic.gdx.Input;
import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.java2.SankossGame;
import edu.chalmers.sankoss.java2.client.SankossClientListener;
import edu.chalmers.sankoss.java2.models.MainMenuModel;
import edu.chalmers.sankoss.java2.renderers.MainMenuRenderer;

/**
 * Created by nikteg on 15/05/14.
 */
public class MainMenuScreen extends AbstractScreen<MainMenuRenderer, MainMenuModel> {

    public MainMenuScreen() {
        setModel(new MainMenuModel()).addPropertyChangeListener(setRenderer(new MainMenuRenderer()));
        SankossGame.getInstance().getClient().addListener(new SankossClientListener() {
            @Override
            public void connected(BasePlayer player) {
                //getRenderer().enableMultiPlayer();
                System.out.println("ENABLE MULTIPLAYER");
            }
        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            SankossGame.Screens.LOBBY.show();
        }

        return true;
    }

    @Override
    public void show() {
        super.show();
    }
}
