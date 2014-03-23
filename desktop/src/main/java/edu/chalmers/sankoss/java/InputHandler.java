package edu.chalmers.sankoss.java;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Class for handling input from user.
 * Will listen to keypresses and mouse clicks and
 * passes them to the controller.
 *
 * @author Mikael Malmqvist
 * @date 3/23/14
 */
public class InputHandler implements ApplicationListener{

    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        // When a user clicks left mouse button
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
