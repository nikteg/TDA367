package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.Screen;
import edu.chalmers.sankoss.java.Models.MainMenu;
import edu.chalmers.sankoss.java.Renderers.MainMenuRenderer;
import edu.chalmers.sankoss.java.SankossController;
import edu.chalmers.sankoss.java.SankossGame;

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
    private SankossGame game;
    private SankossController controller;

    /**
     * This will keep a reference of the main game.
     * @param game reference to the SankossGame class
     * @param controller reference to the SankossController class
     */
    public MainMenuScreen(SankossController controller, SankossGame game) {
        this.controller = controller;
        this.game = game;
    }

    @Override
    public void render(float delta) {
        game.setScreen(controller.getNextScreen(SankossController.CurrentScreen.MAINMENU));
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
