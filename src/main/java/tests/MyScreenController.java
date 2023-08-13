package tests;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * ScreenController for Hello World Example.
 *
 * @author Tobse
 */
public class MyScreenController implements ScreenController {

    /**
     * nifty instance.
     */
    @SuppressWarnings("unused")
    private Nifty nifty;

    /**
     * Bind this ScreenController to a screen.
     *
     * @param newNifty  nifty
     * @param newScreen screen
     */
    public final void bind(final Nifty newNifty, final Screen newScreen) {
        nifty = newNifty;
    }

    /**
     * on start screen interactive.
     */
    public final void onStartScreen() {
    }

    /**
     * on end screen.
     */
    public final void onEndScreen() {
    }

    /**
     * quit method called from the xml-Fil
     */
    public final void StartXSwing() {
        System.out.println("start XSing()");
    }
}