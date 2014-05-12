package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.core.BasePlayer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.Waiting;
import edu.chalmers.sankoss.java.renderers.WaitingRenderer;

/**
 * Screen to be placed in when hosting a game and
 * waiting for an opponent to join.
 *
 * @author Mikael Malmqvist
 * @date 5/5/14
 */
public class WaitingScreen extends AbstractScreen<WaitingRenderer> {

    PlacementScreen placementScreen;

    public WaitingScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new Waiting();
        model.getClient().addListener(new WaitingListener());
        renderer = new WaitingRenderer(model);
        placementScreen = new PlacementScreen(controller, game);
        create();
    }

    /**
     * @inheritdoc
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        renderer.resize(width, height);

    }

    public void setHost(boolean host) {
        renderer.setHost(host);
    }

    private class WaitingListener extends SankossClientListener {

        @Override
        public void joinedRoom(BasePlayer player) {
            if (!player.getID().equals(model.getClient().getPlayer().getID())) {
                renderer.getWaitingLabel().setText(player.getName() + " has joined your room!");
                renderer.getStartGameBtn().setDisabled(false);
            }

        }

        @Override
        public void startedGame(Long gameID) {
            controller.changeScreen(placementScreen);
        }
    }

    @Override
    public void show() {
        // Sets the stage as input source
        controller.changeInput(stage);
    }

    @Override
    public void hide() {

    }

    /**
     * @inheritdoc
     */
    @Override
    public void create() {
        super.create();

        renderer.drawControllers(this);

        stage.addActor(renderer.getTable());
        stage.draw();
        Table.drawDebug(stage);

    }

    public CancelButtonListener getCancelButtonListener() {
        return new CancelButtonListener();
    }

    public StartButtonListener getStartButtonListener() {
        return new StartButtonListener();
    }

    private class StartButtonListener extends ChangeListener {

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            startGame();
        }

    }

    private class CancelButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            System.out.println("FISK");
            jumpToLobby();
        }

    }

    /**
     * Method for stating a game.
     * Calls client to start game and controller to change Screen.
     */
    public void startGame() {
        model.getClient().startGame(model.getClient().getRoomID());
    }

    public void jumpToLobby() {
        // Removes room and backs to lobby
        model.getClient().removeRoom(model.getClient().getRoomID());
        controller.changeScreen(new LobbyScreen(controller, game));
    }

}
