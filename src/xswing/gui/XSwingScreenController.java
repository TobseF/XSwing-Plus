package xswing.gui;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyInputMapping;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * ScreenController for Hello World Example.
 * 
 * @author void
 */
public class XSwingScreenController implements ScreenController {

	/** nifty instance. */
	private Nifty nifty;
	private Screen screen;
	private StateBasedGame game;

	public XSwingScreenController(StateBasedGame game) {
		this.game = game;
	}

	/**
	 * Bind this ScreenController to a screen.
	 * 
	 * @param newNifty nifty
	 * @param screen screen
	 */
	public final void bind(final Nifty nifty, final Screen newScreen) {
		this.nifty = nifty;
		screen = newScreen;
	}

	/**
	 * on start screen interactive.
	 */
	public final void onStartScreen() {
		nifty.addControls();

		screen.findElementByName("start").setFocus();
		screen.addKeyboardInputHandler(new NiftyInputMapping() {
			@Override
			public NiftyInputEvent convert(KeyboardInputEvent e) {
				if (e.getKey() == Keyboard.KEY_ESCAPE) {
					return NiftyInputEvent.Escape;
				} else {
					return null;
				}
			}
		}, new KeyInputHandler() {
			@Override
			public boolean keyEvent(NiftyInputEvent e) {
				if (e != null && e == NiftyInputEvent.Escape) {
					System.out.println("ESC pressed");
					exitXSwing();
				}
				return false;
			}
		});
		game.getContainer().getInput().resume();
	}

	/**
	 * on end screen.
	 */
	public final void onEndScreen() {
		screen.findElementByName("Start").setFocus();
	}

	public final void startXSwing() {
		System.out.println("start XSing()");
		game.enterState(2);
	}

	public void exitXSwing() {
		game.getContainer().exit();
		nifty.exit();
	}

}
