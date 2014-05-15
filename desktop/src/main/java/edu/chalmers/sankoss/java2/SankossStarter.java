package edu.chalmers.sankoss.java2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by nikteg on 15/05/14.
 */
public class SankossStarter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

        cfg.title = "Battleships";
        cfg.vSyncEnabled = true;
        cfg.width = 800;
        cfg.height = 600;
        LwjglApplication application = new LwjglApplication(SankossGame.getInstance(), cfg);
    }

}
