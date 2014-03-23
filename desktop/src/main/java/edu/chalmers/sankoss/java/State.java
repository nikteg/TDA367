package edu.chalmers.sankoss.java;

/**
 * State for current game.
 * State is where the game is at - either in
 * main menu,lobby, placement of boats or in-game.
 * @author Mikael Malmqvist
 * @date 3/23/14
 */
public interface State {
    /**
     * Method for displaying current view.
     * StateController will call this method from
     * its instance of currentState to display the
     * current view.
     */
    public void displayView();

}
