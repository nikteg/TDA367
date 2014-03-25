package edu.chalmers.sankoss.java.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
public class MainMenuScreen implements Screen, ApplicationListener {

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

    /**
     * Game loop for the current Screen.
     * This method loops as long this Screen is active.
     * @param delta
     */
    @Override
    public void render(float delta) {

        if(controller.updateNow()) {
            game.setScreen(controller.getNextScreen(SankossController.CurrentScreen.MAINMENU));

        }

        mainMenuRenderer.render();
    }

    /**
     * Method for when this Screen is set.
     * This method is called automatically when the game sets
     * this Screen as its active Screen. It instantiates its
     * MainMenu and Renderer.
     */
    @Override
    public void show() {
        mainMenu = new MainMenu();
        mainMenuRenderer = new MainMenuRenderer(mainMenu);
    }
    /**
     * Method when this is no longer the active Screen.
     * This method is called automatically when the game sets
     * its active Screen to a different Screen than this.
     */
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


    // Methods for ApplicationListener
    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        if(Gdx.input.isKeyPressed(Input.Keys.F5)) {
            System.out.println("Refresh!");
        }
    }
}
