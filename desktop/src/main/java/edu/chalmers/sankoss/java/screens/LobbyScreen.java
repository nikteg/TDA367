package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import edu.chalmers.sankoss.core.Coordinate;
import edu.chalmers.sankoss.core.Player;
import edu.chalmers.sankoss.core.Room;
import edu.chalmers.sankoss.core.Ship;
import edu.chalmers.sankoss.java.Models.Lobby;
import edu.chalmers.sankoss.java.Renderers.LobbyRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;
import edu.chalmers.sankoss.java.client.SankossClientListener;

import java.util.Map;

/**
 * Screen used at the game lobby when finding a game/room to join.
 * Handles game logic in lobby, almost like a controller.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class LobbyScreen extends AbstractScreen implements SankossClientListener{

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
        model.getClient().addListener(this);
        renderer = new LobbyRenderer(model);
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

    public void connected(Long playerID) {
        System.out.print("You are connected");
    }

    /**
     * Method for getting active rooms on server.
     * @param rooms Rooms on Server to join.
     */
    public void fetchedRooms(Map<Long, Room> rooms) {
        this.gameRooms = rooms;

        for(Room room : rooms.values()) {
            System.out.println("Fetched room #" + room.getName());

        }

        model.setRoomMap(gameRooms);

        ((LobbyRenderer)renderer).setList(rooms);
    }

    public void createdRoom(Long roomID) {

    }

    public void joinedRoom(Player player) {

    }

    public void startedGame(Long gameID, java.util.List<Player> players) {

    }

    public void gameReady() {

    }

    public void playerIsReady(Player player) {

    }

    public void turn() {

    }

    public void fireResult(Long gameID, Player target, Coordinate coordinate, boolean hit) {

    }

    public void destroyedShip(Player player, Ship ship) {

    }

    public void disconnected() {

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

    private class JoinButtonListener extends ChangeListener{
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            // Retrives selected name and matches with room
            String roomName = ((LobbyRenderer)renderer).getRoomList().getSelection();

            Room roomToJoin = model.getRoomByName(roomName, gameRooms);

            model.getClient().joinRoom(roomToJoin.getID());

            System.out.println(model.getClient().getPlayer().getName() + " has joined room: #" + roomToJoin.getName());
            controller.changeScreen(new PlacementScreen(controller, game));
        }
    }

    private class CancelButtonListener extends ChangeListener{
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            controller.changeScreen(new MainMenuScreen(controller, game));
        }
    }

    private class EditButtonListener extends ChangeListener{
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            Gdx.input.getTextInput(new Input.TextInputListener() {

                // Gets user input
                @Override
                public void input(String name) {
                    // TODO: DO WE NEED TO CHECK IF PLAYERS HAVE SAME NAME?!?
                    model.getClient().getPlayer().setName(name);
                    ((LobbyRenderer)renderer).setNameLabel(name);

                }

                @Override
                public void canceled() {
                    // nothing..
                }
            }, "Enter new name:", "");

        }
    }
}
