/*
 * @version 0.0 19.02.2009
 * @author Tobse F
 */
package lib.mylib.swing;

import org.newdawn.slick.util.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.net.URL;
import java.util.ArrayList;

public class SwingUtils {

    /**
     * Tries to set the LookAndFeel to the os default
     *
     * @return if the system look and feel could be set
     */
    public static boolean setSystemLookAndFeel() {
        boolean isSystemLookAndFeelSet = true;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            isSystemLookAndFeelSet = false;
        }
        return isSystemLookAndFeelSet;
    }

    /**
     * Tries to set the NimbusLookAndFeel
     *
     * @return if the NimbusLookAndFeel could bet set
     */
    public static boolean setNimbusLookAndFeel() {
        boolean isNumbusSet = true;
        try { // Nimbus Loyout
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            isNumbusSet = false;
        } catch (InstantiationException e) {
            isNumbusSet = false;
        } catch (IllegalAccessException e) {
            isNumbusSet = false;
        } catch (UnsupportedLookAndFeelException e) {
            isNumbusSet = false;
        }
        return isNumbusSet;

        /*
         * try {//alternative way for (LookAndFeelInfo info :
         * UIManager.getInstalledLookAndFeels()) { if ("Nimbus".equals(info.getName())) {
         * UIManager.setLookAndFeel(info.getClassName()); break; } } } catch
         * (UnsupportedLookAndFeelException e) { } catch (ClassNotFoundException e) { } catch
         * (InstantiationException e) { } catch (IllegalAccessException e) { }
         */
    }

    /**
     * Tries to set the NimbusLookAndFeel or (if Nibus is not available) the OS like
     * LookAndFeel
     *
     * @return true if the LookAndFeel could be changed
     */
    public static boolean setCoolLookAndFeel() {
        return (setNimbusLookAndFeel() || setSystemLookAndFeel());
    }

    /**
     * Sets the given {@link Window} to the center of the sceen
     *
     * @param window to set to the screen center
     */
    public static void setLocationToCenter(Window window) {
        window.setLocationRelativeTo(null);
    }

    /**
     * Adds a global Keylistener which receive all KeyEvents which are sent.</br> Code
     * Example:</br> <code><pre>
     * AWTEventListener ael = new AWTEventListener() {
     * 	public void eventDispatched(AWTEvent e ) {
     * 		if(e.getID() == KeyEvent.KEY_PRESSED){
     * 			doSomeThing(); //eg. keyPressed((KeyEvent) e);
     *        }
     *    }
     * };
     * </pre>
     * </code>
     *
     * @param listener to add
     */
    public static void addGlobalKeyListener(AWTEventListener listener) {
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * Sets icons to the given {@link Frame}. Icons have to be saved as
     * "iconName16.png, iconName32.png etc". Follwing sizes are shearched: 16, 22, 32, 48, 64 &
     * 128.
     *
     * @param frame      which should get the icon
     * @param iconFolder path to the folder whre the icon images are located
     * @param iconName   name of the icon without resoultion key and fileextions eg.
     *                   wariningImage
     */
    public static void setIcons(Frame frame, String iconFolder, String iconName) {
        ArrayList<Image> imageList = new ArrayList<Image>();
        int[] sizes = {16, 22, 32, 48, 64, 128};
        for (int size : sizes) {
            try {
                URL filepaph = ResourceLoader.getResource(iconFolder + iconName + size + ".png");
                imageList.add(Toolkit.getDefaultToolkit().getImage(filepaph));
            } catch (RuntimeException e) {
            }
        }
        frame.setIconImages(imageList);
    }

    /**
     * As icon name "icon" will be used. For more information see
     * {@link #setIcons(Frame, String, String)}
     *
     * @param frame
     * @param iconFolder
     * @see #setIcons(Frame, String, String)
     */
    public static void setIcons(Frame frame, String iconFolder) {
        setIcons(frame, iconFolder, "icon");
    }

    /**
     * Returns the center of the first display.
     *
     * @return the center (displayWidht/2,diplayHight/2) of the first display.
     */
    public static Point getScreenCenter() {
        Dimension dimension = getScreenSize();
        return new Point(dimension.width / 2, dimension.height / 2);
    }

    /**
     * Returns the Screen resolution. If there're more than one display you might want to use: {@link #getScreenSizes()}
     *
     * @return the Screen resolution
     * @see #getScreenSizes()
     */
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Returns the Screen resolution of all available displays. If ther's only one display you might use: {@link #getScreenSize()}
     *
     * @return Screen resolution of all available displays.
     * @see #getScreenSize()
     */
    public static Dimension[] getScreenSizes() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices(); // Get size of each screen
        Dimension[] dimension = new Dimension[gs.length];
        for (int i = 0; i < gs.length; i++) {
            DisplayMode dm = gs[i].getDisplayMode();
            dimension[i] = new Dimension(dm.getWidth(), dm.getHeight());
        }
        return dimension;
    }

}