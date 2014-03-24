package edu.chalmers.sankoss.java;

import com.badlogic.gdx.Game;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class SankossGame extends Game {

    @Override
    public void create () {
        new SankossController(this);
    }
}
