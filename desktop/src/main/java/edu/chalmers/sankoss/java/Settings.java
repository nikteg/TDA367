package edu.chalmers.sankoss.java;

import edu.chalmers.sankoss.core.Network;
import org.ini4j.Ini;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by nikteg on 14/04/14.
 */
public class Settings {
    public static String HOSTNAME = "localhost";
    public static int PORT = Network.PORT;
    private static Ini iniInstance = new Ini();

    public static void loadSettings() {
        try {
            File file = new File("settings.ini");

            if (!file.exists()) {
                file.createNewFile();
            }

            loadSettings(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings(File file) throws IOException {
        iniInstance.load(new FileReader(file));

        if (iniInstance.isEmpty()) return;

        Ini.Section networkSection = iniInstance.get("network");

        HOSTNAME = networkSection.get("hostname");
        PORT = Integer.parseInt(networkSection.get("port"));
    }
}