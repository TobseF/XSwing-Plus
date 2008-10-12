package xswing.gui;

import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyInputMapping;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;

final class XSwingScreenController$1
implements NiftyInputMapping {
    XSwingScreenController$1() {
    }

    public NiftyInputEvent convert(KeyboardInputEvent e) {
        if (e.getKey() == 1) {
            return NiftyInputEvent.Escape;
        }
        return null;
    }
}
