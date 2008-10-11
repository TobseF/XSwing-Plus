package tests;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MyScreenController
implements ScreenController {
    private Nifty nifty;

    public final void bind(Nifty newNifty, Screen newScreen) {
        this.nifty = newNifty;
    }

    public final void onStartScreen() {
    }

    public final void onEndScreen() {
    }

    public final void StartXSwing() {
        System.out.println("start XSing()");
    }

    public final void guiTest() {
    }
}
