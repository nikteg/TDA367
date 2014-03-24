package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.InGame;
import edu.chalmers.sankoss.java.Renderers.InGameRenderer;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class InGameScreen implements Screen{

    private InGame inGame;
    private InGameRenderer inGameRenderer;

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        inGame = new InGame();
        inGameRenderer = new InGameRenderer(inGame);
    }

    @Override
    public void hide() {

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
