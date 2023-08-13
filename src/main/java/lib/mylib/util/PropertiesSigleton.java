/*
 * @version 0.0 14.06.2010
 * @author Tobse F
 */
package lib.mylib.util;

import org.newdawn.slick.SavedState;
import org.newdawn.slick.util.Log;

import java.io.IOException;

public class PropertiesSigleton {

    private static final Properties properties = new Properties();
    private static boolean isLoaded = false;
    private String defaultFile;
    /**
     * It's used to store the options local.
     */
    private static SavedState options;

    private PropertiesSigleton() {
        // TODO Auto-generated constructor stub
    }

    public Properties getProperties() {
        return properties;
    }

    public void load() {
        try {
            options.load();

            isLoaded = true;
        } catch (IOException e) {
            Log.warn("Could't load Properties: " + e.getMessage());
        }
    }


}
