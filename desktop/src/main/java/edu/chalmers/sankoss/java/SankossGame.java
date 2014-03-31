package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Game;
import edu.chalmers.sankoss.core.SankossClient;

/**
 * The Game.
 * Upon creation the game will create a new Controller class
 * and use it self as a parameter.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class SankossGame extends Game {

    /**
     * Create method which is called automatically upon
     * creation.
     * The method creates a new controller for the application.
     * */
    @Override
    public void create () {
        new SankossController(this);
    }
}
