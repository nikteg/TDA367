package edu.chalmers.sankoss.java.screens;

import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.Models.InGame;
import edu.chalmers.sankoss.java.Renderers.InGameRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;

import java.util.List;
import java.util.Map;

/**
 * Screen used ingame when actually playing!
 * Handles game logic ingame, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class InGameScreen extends AbstractScreen implements SankossClientListener {

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public InGameScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new InGame();
        model.getClient().addListener(this);
        renderer = new InGameRenderer(model);

        create();
    }

    /**
     * @inheritdoc
     */
    @Override
    public void show() {

    }

    /**
     * @inheritdoc
     */
    @Override
    public void hide() {
        if(stage.getRoot().hasChildren()) {
            stage.getRoot().clearChildren();
        }

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void create() {
        super.create();

        // Sets the stage as input source
        controller.changeInput(stage);

        renderer.drawControllers(this);

        stage.addActor(renderer.getActorPanel());
        stage.draw();
    }

    /**
     * @inheritdoc
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        renderer.resize(width, height);
    }

    /**
     * Game loop for the current Screen.
     * This method loops as long this Screen is active.
     * @param delta
     */
    @Override
    public void render(float delta) {

    }

    @Override
    public void render() {

    }

    @Override
    public void connected(Long playerID) {

    }

    @Override
    public void fetchedRooms(Map<Long, Room> rooms) {

    }

    @Override
    public void createdRoom(Long roomID) {

    }

    @Override
    public void joinedRoom(Player player) {

    }

    @Override
    public void startedGame(Long gameID, List<Player> players) {

    }

    @Override
    public void gameReady() {

    }

    @Override
    public void playerIsReady(Player player) {

    }

    @Override
    public void turn() {

    }

    @Override
    public void fireResult(Long gameID, Player target, Coordinate coordinate, boolean hit) {

    }

    @Override
    public void destroyedShip(Player player, Ship ship) {

    }

    @Override
    public void disconnected() {

    }
}
