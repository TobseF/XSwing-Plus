package xswing.gui;

import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;

final class XSwingScreenController$2
implements KeyInputHandler {
    XSwingScreenController$2() {
    }

    public boolean keyEvent(NiftyInputEvent e) {
        if (e != null && e == NiftyInputEvent.Escape) {
            System.out.println("ESC pressed");
            XSwingScreenController.this.exitXSwing();
        }
        return false;
    }
}
