package xswing.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.TextFieldControl;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.newdawn.slick.state.StateBasedGame;

public class XSwingScreenController
implements ScreenController {
    private Nifty nifty;
    private StateBasedGame game;

    public XSwingScreenController(StateBasedGame game) {
        this.game = game;
    }

    public final void bind(Nifty newNifty, Screen newScreen) {
        this.nifty = newNifty;
    }

    public final void onStartScreen() {
    }

    public final void onEndScreen() {
    }

    public final void startXSwing() {
        System.out.println("start XSing()");
        this.game.enterState(2);
    }

    public final void setHighScore() {
        System.out.println("entered HighScore");
    }

    public final void enterHighScore() {
        System.out.println("entered HighScore 2");
        Screen screen = this.nifty.getCurrentScreen();
        ((TextRenderer)((Object)screen.findElementByName("labelScore").getRenderer(TextRenderer.class))).setText("Your Score: 2200");
        String t = ((TextFieldControl)((Object)screen.findElementByName("name").getControl(TextFieldControl.class))).getText();
        System.out.println(t);
    }

    public void exitXSwing() {
        this.game.getContainer().exit();
        this.nifty.exit();
    }
}
