package edu.chalmers.sankoss.java;

/**
 * Controller in-game for handling communication between the InGameView
 * and the StateController class.
 * This class will listen to user input and make them available to the
 * StateController, that will pass them to the GameModel for appropriate action.
 *
 * @author Mikael Malmqvist
 * @date 3/23/14
 */
public class InGameController  implements State {
    private InGameView inGameView;

    public InGameController() {
        inGameView = new InGameView();
    }
}
