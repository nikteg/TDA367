package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;
import edu.chalmers.sankoss.java.models.MainMenu;
import edu.chalmers.sankoss.java.renderers.MainMenuRenderer;

import java.util.Map;

/**
 * Screen used at the main menu.
 * Handles game logic at the main menu, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class MainMenuScreen extends AbstractScreen {

    private String roomName;
    private Long lastRoomID;
    private Map<Long, Room> gameRooms;
    private Object[] rooms = new Object[0];

    /**
     * @inheritdoc
     */
    public MainMenuScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new MainMenu();
        model.getClient().addListener(new MainMenuListener());
        renderer = new MainMenuRenderer(model);

        create();

    }

    private class MainMenuListener extends SankossClientListener {
        public void connected(Long playerID) {
            System.out.print("SERVER: Client connected");
        }

        public void fetchedRooms(Map<Long, Room> fetchedRooms) {
            rooms = fetchedRooms.values().toArray();
        }

        public void createdRoom(Long roomID) {
            System.out.println("SERVER: " + model.getClient().getPlayer().getName() + " created room #" + roomID);
        }

        public void joinedRoom(Player player) {
            ((MainMenuRenderer)renderer).setStatusLabel(player.getName() + " has joined your room!");
            ((MainMenuRenderer)renderer).createStartButton();
        }
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

    /**
     * @inheritdoc
     */
    @Override
    public void create() {
        // Defines variables for visuals
        super.create();
        renderer.drawControllers(this);

        // Sets the stage as input source
        controller.changeInput(stage);

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

    public JoinButtonListener getJoinButtonListener() {
        return new JoinButtonListener();
    }

    public HostButtonListener getHostButtonListener() {
        return new HostButtonListener();
    }

    public HelpButtonListener getHelpButtonListener() {
        return new HelpButtonListener();
    }

    public StartButtonListener getStartButtonListener() {
        return new StartButtonListener();
    }

    private class StartButtonListener extends ChangeListener{

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



    private class JoinButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {

            jumpToLobby();
        }


        public void jumpToLobby() {
            controller.changeScreen(new LobbyScreen(controller, game));
        }
    }

    private class HostButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            startHosting();

        }

        /**
         * Method for hosting a game.
         */
        public void startHosting() {
            model.getClient().fetchRooms();

            Gdx.input.getTextInput(new Input.TextInputListener() {

                // Gets user input
                @Override
                public void input(String roomName) {
                    boolean same = false;

                    for(int i = 0; i < rooms.length; i++) {
                        if(((Room)rooms[i]).getName().equals(roomName)) {
                            same = true;
                        }
                    }

                    if(same) {
                        //TODO: Display better error msg
                        System.out.println("ERROR: Room already exists!");
                    } else {
                        ((MainMenuRenderer)renderer).setStatusLabel("Waiting for opponent to join " + roomName + "..");
                        model.getClient().createRoom(roomName, ""); //Roomname and password


                        if(lastRoomID != null) model.getClient().removeRoom(lastRoomID);

                        lastRoomID = model.getClient().getRoomID();

                        // Disables join and host button
                        ((MainMenuRenderer)renderer).getJoinBtn().removeListener(((MainMenuRenderer)renderer).getJoinBtn().getListeners().first());
                        ((MainMenuRenderer)renderer).getHostBtn().removeListener(((MainMenuRenderer)renderer).getHostBtn().getListeners().first());
                    }

                }

                @Override
                public void canceled() {
                    // nothing..
                }
            }, "Enter room name:", "");
        }

    }

    private class HelpButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {

            // controller.changeScreen(new PlacementScreen(controller, game));

        }
    }
}
