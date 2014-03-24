package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.Lobby;
import edu.chalmers.sankoss.java.Renderers.LobbyRenderer;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class LobbyScreen implements Screen {
    private Lobby lobby;
    private LobbyRenderer lobbyRenderer;

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        lobby = new Lobby();
        lobbyRenderer = new LobbyRenderer(lobby);
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
