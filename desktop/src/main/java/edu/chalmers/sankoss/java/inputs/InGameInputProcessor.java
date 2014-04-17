package edu.chalmers.sankoss.java.inputs;

import com.badlogic.gdx.InputProcessor;

/**
 * Handles input for the InGame Screen.
 * The SankossController will switch to this InputProcessor
 * when the InGameScreen to set to its active Screen.
 *
 * @author Mikael Malmqvist
 * @date 3/26/14
 */
public class InGameInputProcessor implements InputProcessor{

    @Override
    public boolean keyDown(int i) {

        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i2, int i3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i2) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
