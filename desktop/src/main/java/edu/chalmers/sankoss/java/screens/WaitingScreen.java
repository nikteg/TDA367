package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.core.Player;
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
public class WaitingScreen extends AbstractScreen {

    /**
     * @inheritdoc
     */
    public WaitingScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new Waiting();
        model.getClient().addListener(new WaitingListener());
        renderer = new WaitingRenderer(model);
        create();
    }

    private class WaitingListener extends SankossClientListener {

        @Override
        public void joinedRoom(Player player) {
            ((WaitingRenderer)renderer).getWaitingLabel().setText(player.getName() + " has joined your room!");
            ((WaitingRenderer)renderer).createStartBtn();
        }
    }

    @Override
    public void show() {

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

        // Sets the stage as input source
        controller.changeInput(stage);

        renderer.drawControllers(this);

        stage.addActor(renderer.getActorPanel());
        stage.draw();

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

        /**
         * Method for stating a game.
         * Calls client to start game and controller to change Screen.
         */
        public void startGame() {
            model.getClient().startGame(model.getClient().getRoomID());
            controller.changeScreen(new PlacementScreen(controller, game));
        }
    }

    private class CancelButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            jumpToLobby();
        }

        public void jumpToLobby() {
            // Removes room and backs to lobby
            model.getClient().removeRoom(model.getClient().getRoomID());
            controller.changeScreen(new LobbyScreen(controller, game));
        }
    }

}
