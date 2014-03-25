package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.Lobby;
import edu.chalmers.sankoss.java.Renderers.LobbyRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

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
    private SankossGame game;
    private SankossController controller;

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public LobbyScreen(SankossController controller, SankossGame game) {
        this.controller = controller;
        this.game = game;
    }

    @Override
    public void render(float delta) {
        game.setScreen(controller.getNextScreen(SankossController.CurrentScreen.LOBBY));
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
