package xswing.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.TextFieldControl;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyInputMapping;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.newdawn.slick.state.StateBasedGame;

public class XSwingScreenController
implements ScreenController {
    private Nifty nifty;
    Screen newScreen;
    private StateBasedGame game;

    public XSwingScreenController(StateBasedGame game) {
        this.game = game;
    }

    public final void bind(Nifty newNifty, Screen newScreen) {
        this.nifty = newNifty;
        this.newScreen = newScreen;
    }

    public final void onStartScreen() {
        this.nifty.addControls();
        this.newScreen.findElementByName("start").setFocus();
        this.newScreen.addKeyboardInputHandler(new NiftyInputMapping(){

            public NiftyInputEvent convert(KeyboardInputEvent e) {
                if (e.getKey() == 1) {
                    return NiftyInputEvent.Escape;
                }
                return null;
            }
        }, new KeyInputHandler(){

            public boolean keyEvent(NiftyInputEvent e) {
                if (e != null && e == NiftyInputEvent.Escape) {
                    System.out.println("ESC pressed");
                    XSwingScreenController.this.exitXSwing();
                }
                return false;
            }
        });
        this.game.getContainer().getInput().resume();
    }

    public final void onEndScreen() {
        this.newScreen.findElementByName("Start").setFocus();
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
