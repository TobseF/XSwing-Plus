package xswing.gui;

import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;

final class ScoreScreenController$1
implements KeyInputHandler {
    ScoreScreenController$1() {
    }

    public boolean keyEvent(NiftyInputEvent e) {
        if (e == NiftyInputEvent.SubmitText) {
            ScoreScreenController.this.enterHighScore();
            return true;
        }
        return false;
    }
}
