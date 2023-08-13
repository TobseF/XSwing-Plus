/*
 * @version 0.0 03.04.2010
 * @author 	Tobse F
 */
package tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class DisplayModes {


    public DisplayModes() {
        try {
            DisplayMode[] displayModes = Display.getAvailableDisplayModes();
            for (DisplayMode displayMode : displayModes) {
                System.out.println(displayMode);
            }

        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DisplayModes();
    }
}
