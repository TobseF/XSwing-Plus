package xswing.gui;

import lib.mylib.util.LanguageSelector;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.state.StateBasedGame;

import xswing.LocationController;
import xswing.start.XSwing;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.render.TextRenderer;
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
		screen.findElementByName("version").getRenderer(TextRenderer.class).setText(
		XSwing.VERSION);
		
		setLanguageStings();
	}

	/**
	 * on start screen interactive.
	 */
	public final void onStartScreen() {
		nifty.addControls();

		screen.findElementByName("startSinglePlayer").setFocus();
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
					System.out.println("pressed: ESC");
					exitXSwing();
				}
				return false;
			}
		});
		game.getContainer().getInput().resume();
		
	}

	private void setLanguageStings(){
		setElementName("startSinglePlayer");
		setElementName("startMultiplayer");
		setElementName("tutorial");
		setElementName("options");
		setElementName("credits");
		setElementName("exit");
		//TODO: void: available? ((MenuItemType)screen.findElementByName("labelScore")).setHintText("View the Tutorials stages")
	}
	
	private void setElementName(String elementName){
		setElementName(screen, elementName);
	}
	
	public static void setElementName(Screen screen, String elementName){
		screen.findElementByName(elementName).getRenderer(TextRenderer.class).setText(
				LanguageSelector.getString(elementName));
	}
	
	/**
	 * on end screen.
	 */
	public final void onEndScreen() {
		screen.findElementByName("Start").setFocus();
	}

	public final void startSingleplayer() {
		System.out.println("pressed: start XSing()");
		LocationController.setMultiplayer(false);
		game.enterState(2);
	}
	
	public final void startMultiplayer() {
		System.out.println("pressed: start XSing()");
		LocationController.setMultiplayer(true);
		game.enterState(2);
	}

	public void exitXSwing() {
		game.getContainer().exit();
		nifty.exit();
	}

}