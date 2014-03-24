package edu.chalmers.sankoss.java;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Class to start the application from.
 * Creates a application window with initial
 * size, title and GL20 support.
 *
 * @author Mikael Malmqvist
 * @date 3/23/14
 */
public class GameStarter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.title = "Battleships";
        cfg.useGL20 = true;
        cfg.width = 800;
        cfg.height = 600;
        LwjglApplication application = new LwjglApplication(new SankossGame(), cfg);
    }
}
