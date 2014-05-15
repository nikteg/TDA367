package edu.chalmers.sankoss.java2.screens;

import com.badlogic.gdx.Input;
import edu.chalmers.sankoss.java2.SankossGame;
import edu.chalmers.sankoss.java2.models.LobbyModel;
import edu.chalmers.sankoss.java2.renderers.LobbyRenderer;

/**
 * Created by nikteg on 15/05/14.
 */
public class LobbyScreen extends AbstractScreen<LobbyRenderer, LobbyModel> {

    public LobbyScreen() {
        setModel(new LobbyModel()).addPropertyChangeListener(setRenderer(new LobbyRenderer()));
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            SankossGame.Screens.MAIN_MENU.show();
        }

        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        System.out.println(String.format("Position: %dx%d Button: %d", x, y, button));

        return true;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
