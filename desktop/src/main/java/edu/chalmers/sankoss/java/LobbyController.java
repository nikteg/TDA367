package edu.chalmers.sankoss.java;

/**
 * Controller for the room lobby (where players join a game) for handling
 * communication between the LobbyView and the StateController class.
 * This class will listen to user input and make them available to the
 * StateController, that will pass them to the GameModel for appropriate action.
 *
 * @author Mikael Malmqvist
 * @date 3/23/14
 */
public class LobbyController implements State {
    private LobbyView lobbyView;

    public LobbyController() {
        lobbyView = new LobbyView();
    }
}
