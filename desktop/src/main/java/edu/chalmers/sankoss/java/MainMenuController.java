package edu.chalmers.sankoss.java;

/**
 * Controller for the main menu for handling communication between the MainMenuView
 * and the StateController class.
 * This class will listen to user input and make them available to the
 * StateController, that will pass them to the GameModel for appropriate action.
 *
 * @author Mikael Malmqvist
 * @date 3/23/14
 */
public class MainMenuController implements State {
    private MainMenuView mainMenuView;

    public MainMenuController() {
        mainMenuView = new MainMenuView();
    }
}
