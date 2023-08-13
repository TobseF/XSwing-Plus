package xswing.gui;

import de.lessvoid.nifty.EndNotify;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.controller.ButtonControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import lib.mylib.util.LanguageSelector;
import lib.mylib.util.SlickUtils;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import xswing.LocationController;
import xswing.start.XSwing;

/**
 * ScreenController for Hello World Example.
 *
 * @author void
 */
public class ScreenControllerMainMenu implements ScreenController {

    /**
     * nifty instance.
     */
    private Nifty nifty;
    private Screen screen;
    private final StateBasedGame game;

    public ScreenControllerMainMenu(StateBasedGame game) {
        this.game = game;
    }

    /**
     * Bind this ScreenController to a screen.
     *
     * @param newNifty nifty
     * @param screen   screen
     */
    public final void bind(final Nifty nifty, final Screen newScreen) {
        this.nifty = nifty;
        screen = newScreen;
        screen.findElementByName("version_label").getRenderer(TextRenderer.class).setText(XSwing.VERSION);
        setLanguageStings();
    }

    /**
     * on start screen interactive.
     */
    public final void onStartScreen() {
        SlickUtils.hideMouse(game.getContainer(), false);
//		try {
//			game.getContainer().setMouseCursor(mouseCursor, 2, 2);
//		} catch (SlickException e) {
//			e.printStackTrace();
//		}
        /*
         * screen.findElementByName("startSinglePlayer").setFocus();
         * screen.addKeyboardInputHandler(new NiftyInputMapping() {
         * @Override public NiftyInputEvent convert(KeyboardInputEvent e) { if (e.getKey() ==
         * Keyboard.KEY_ESCAPE) { return NiftyInputEvent.Escape; } else { return null; } } }, new
         * KeyInputHandler() {
         * @Override public boolean keyEvent(NiftyInputEvent e) { if (e != null && e ==
         * NiftyInputEvent.Escape) { System.out.println("pressed: ESC"); exitXSwing(); } return
         * false; } });
         */

    }

    /**
     * Sets the text of the gui elements according the selected language. The id (name) have be
     * saved in a local language file
     */
    private void setLanguageStings() {
        setButtonText("startSinglePlayer");
        setButtonText("startMultiplayer");
        // setButtonName("tutorial");
        setButtonText("options");
        setButtonText("credits");
        setButtonText("exit");
    }

    /**
     * Sets the text of the button with the given id
     *
     * @param elementName name (id in xml) of the button
     */
    private void setButtonText(String elementName) {
        screen.findControl(elementName, ButtonControl.class).setText(LanguageSelector.getString(elementName));
    }

    /**
     * on end screen.
     */
    public final void onEndScreen() {
        Log.info("Switched from MainMenu");
//		try {
//			game.getContainer().setMouseCursor(mouseCursorInvisible, 2, 2);
//		} catch (SlickException e) {
//			e.printStackTrace();
//		}
        // screen.findElementByName("Start").setFocus();
    }

    /**
     * Starts a single player Game -called from nifty xml gui
     */
    public final void startSingleplayer() {
        Log.info("Started Singleplayer Game");
        LocationController.setMultiplayer(false);
        enterGame();
    }

    /**
     * Starts a multiplayer Game -called from nifty xml gui
     */
    public final void startMultiplayer() {
        Log.info("Started Multiplayer Game");
        LocationController.setMultiplayer(true);
        enterGame();
    }

    /**
     * Enters the game (from the main menu)
     */
    private void enterGame() {
        System.out.println("enterGame");
        //game.getContainer().setMouseGrabbed(true);
        SlickUtils.hideMouse(game.getContainer(), true);
        game.enterState(2);
    }


    public void popupNotAvailable() {
        Element popup = nifty.createPopup("popupNotAvailable");
        popup.findControl("buttonOK", ButtonControl.class).setText(LanguageSelector.getString("ok"));
        popup.findElementByName("textNotAvailable").getRenderer(TextRenderer.class).setText(
                LanguageSelector.getString("not_available"));
        nifty.showPopup(screen, "popupNotAvailable", null);
    }

    public void popupNotAvailableOK() {
        nifty.closePopup("popupNotAvailable");
    }

    /**
     * popupExit.
     *
     * @param exit exit string
     */
    public void popupExit(final String exit) {
        nifty.closePopup("popupExit", new EndNotify() {

            public void perform() {
                if ("yes".equals(exit)) {
                    nifty.setAlternateKey("fade");
                    nifty.exit();
                    game.getContainer().exit();

                }
            }
        });
    }

    /**
     * Exits the game -called from nifty xml gui
     */
    public void exitXSwing() {
        Element popup = nifty.createPopup("popupExit");
        popup.findControl("buttonYes", ButtonControl.class).setText(LanguageSelector.getString("yes"));
        popup.findControl("buttonNo", ButtonControl.class).setText(LanguageSelector.getString("no"));
        popup.findElementByName("textRealWantToExit").getRenderer(TextRenderer.class).setText(
                LanguageSelector.getString("real_want_to_exit"));
        //screen.findControl("buttonYes", ButtonControl.class).setText(LanguageSelector.getString("yes"));
//		screen.findElementByName("buttonNo").getRenderer(TextRenderer.class).setText(
//				LanguageSelector.getString("no"));
        nifty.showPopup(screen, "popupExit", null);
    }

}