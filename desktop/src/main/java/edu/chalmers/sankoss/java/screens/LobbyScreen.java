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
import edu.chalmers.sankoss.java.models.Lobby;
import edu.chalmers.sankoss.java.renderers.LobbyRenderer;

import java.util.Map;

/**
 * Screen used at the game lobby when finding a game/room to join.
 * Handles game logic in lobby, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class LobbyScreen extends AbstractScreen {

    // private Lobby lobby;
    private Object[] keys;
    private Room[] rooms;
    private String[] roomNames;
    private Map<Long, Room> gameRooms;


    /**
     * @inheritdoc
     */
    public LobbyScreen(SankossController controller, SankossGame game) {
        super(controller, game);
        model = new Lobby();
        model.getClient().addListener(new LobbyListener());
        renderer = new LobbyRenderer(model);
        create();

    }

    private class LobbyListener extends SankossClientListener {
        /**
         * Method for getting active rooms on server.
         * @param rooms Rooms on Server to join.
         */
        public void fetchedRooms(Map<Long, Room> rooms) {
            gameRooms = rooms;

            for(Room room : rooms.values()) {
                System.out.println("SERVER: Fetched room #" + room.getName());

            }

            model.setRoomMap(gameRooms);

            ((LobbyRenderer)renderer).setList(rooms);
        }

        public void joinedRoom(Player player) {
            System.out.println("SERVER: " + player.getName() + " joined!");
        }

        public void startedGame(Long gameID, java.util.List<Player> players) {
            ((LobbyRenderer)renderer).getJoinBtn().setText("Enter Game");
        }

        @Override
        public void createdRoom(Long roomID) {
            System.out.println("You hosted room #" + roomID);
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
        super.create();

        // Sets the stage as input source
        controller.changeInput(stage);

        renderer.drawControllers(this);

        model.getClient().fetchRooms();

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

    public CancelButtonListener getCancelButtonListener() {
        return new CancelButtonListener();
    }

    public EditButtonListener getEditButtonListener() {
        return new EditButtonListener();
    }

    public HostButtonListener getHostButtonListener() {
        return new HostButtonListener();
    }

    private class JoinButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            joinGame();
        }
    }

    private class CancelButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            jumpToMainMenu();
        }

        public void jumpToMainMenu() {
            controller.changeScreen(new MainMenuScreen(controller, game));
        }
    }

    // TODO: REDO COMPLETELY
    private class EditButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            changeName();
        }
    }

    private class HostButtonListener extends ChangeListener{

        @Override
        public void changed(ChangeEvent event, Actor actor) {
            startHosting();
            jumpToWaiting();
        }
    }

    /**
     * Method for hosting a room.
     */
    public void startHosting() {
        model.getClient().createRoom(model.getClient().getPlayer().getName() + "'s Room", ""); //Roomname and password

    }

    public void jumpToWaiting() {
        controller.changeScreen(new WaitingScreen(controller, game));
    }

    /**
     * Method for changing player's name.
     */
    public void changeName() {
        Gdx.input.getTextInput(new Input.TextInputListener() {

            // Gets user input
            @Override
            public void input(String name) {

                model.getClient().getPlayer().setName(name);
                ((LobbyRenderer)renderer).setNameLabel(name);

            }

            @Override
            public void canceled() {
                // nothing..
            }
        }, "Enter new name:", "");
    }

    /**
     * Method for joining a game.
     */
    public void joinGame() {
        String buttonText = "" + ((LobbyRenderer)renderer).getJoinBtn().getText();

        if(buttonText.equals("Join") && gameRooms.size() > 0) {

            // Retrives selected name and matches with room
            String roomName = ((LobbyRenderer)renderer).getRoomList().getSelection();

            Room roomToJoin = model.getRoomByName(roomName, gameRooms);
            model.getClient().joinRoom(roomToJoin.getID());

            ((LobbyRenderer)renderer).getJoinBtn().setText("Joined");


        } else if(buttonText.equals("Enter Game") && model.getClient().getGameID() != null) {
            controller.changeScreen(new PlacementScreen(controller, game));
        }
    }
}
