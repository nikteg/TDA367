package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.Lobby;
import edu.chalmers.sankoss.java.Renderers.LobbyRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

/**
 * Screen used at the game lobby when finding a game/room to join.
 * Handles game logic in lobby, almost like a controller.
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

    /**
     * Game loop for the current Screen.
     * This method loops as long this Screen is active.
     * @param delta
     */
    @Override
    public void render(float delta) {
        //game.setScreen(controller.getNextScreen(SankossController.CurrentScreen.LOBBY));
        lobbyRenderer.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        System.out.println("Welcome to the Lobby!");
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
