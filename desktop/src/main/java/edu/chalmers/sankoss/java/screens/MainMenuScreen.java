package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.MainMenu;
import edu.chalmers.sankoss.java.Renderers.MainMenuRenderer;

/**
 * Description of class.
 * More detailed description.
 *
 * @author Mikael Malmqvist
 * @date 3/24/14
 */
public class MainMenuScreen implements Screen {

    private MainMenu mainMenu;
    private MainMenuRenderer mainMenuRenderer;

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        mainMenu = new MainMenu();
        mainMenuRenderer = new MainMenuRenderer(mainMenu);
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
