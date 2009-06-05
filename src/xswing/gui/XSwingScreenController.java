package xswing.gui;

import lib.mylib.util.LanguageSelector;
import org.newdawn.slick.state.StateBasedGame;
import xswing.LocationController;
import xswing.start.XSwing;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.*;

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

	/** To prevent starting the game without pressing a start button after returung from a running game*/
	private boolean resumingFromGame = false;

	public XSwingScreenController(StateBasedGame game) {
		this.game = game;
		// VOID: registerSound can't be entered behind register effect
	}

	/**
	 * Bind this ScreenController to a screen.
	 * 
	 * @param newNifty nifty
	 * @param screen screen
	 */
	public final void bind(final Nifty nifty, final Screen newScreen) {
		System.out.println("bind");
		game.getContainer().setMouseGrabbed(false);
		this.nifty = nifty;
		this.screen = newScreen;
		screen.findElementByName("version_label").getRenderer(TextRenderer.class).setText(XSwing.VERSION);
		// VOID: Nifty's <onHover name="changeFont" doesn't set the text position
		// VOID: <onHover name="focus" /> woher soll das einer wissen, damit er <onFucus nutzen
		// kann?
		//screen.findElementByName("startSinglePlayer").getl.getRenderer(TextRenderer.class).setText(XSwing.VERSION);
		
		//d setLanguageStings();
	}
	


	/**
	 * on start screen interactive.
	 */
	public final void onStartScreen() {
		System.out.println("onStartScreen");
		// nifty.addControls();


		// game.getContainer().getInput().resume();

		/*
		 * screen.findElementByName("startSinglePlayer").setFocus();
		 * screen.addKeyboardInputHandler(new NiftyInputMapping() {
		 * @Override public NiftyInputEvent convert(KeyboardInputEvent e) { if (e.getKey() ==
		 * Keyboard.KEY_ESCAPE) { return NiftyInputEvent.Escape; } else { return null; } } },
		 * new KeyInputHandler() {
		 * @Override public boolean keyEvent(NiftyInputEvent e) { if (e != null && e ==
		 * NiftyInputEvent.Escape) { System.out.println("pressed: ESC"); exitXSwing(); } return
		 * false; } });
		 */

	}

	private void setLanguageStings() {
		setButtonName("startSinglePlayer");
		setButtonName("startMultiplayer");
		setButtonName("tutorial");
		setButtonName("options");
		setButtonName("credits");
		setButtonName("exit");
		// TODO: void: available?
		// ((MenuItemType)screen.findElementByName("labelScore")).setHintText("View the Tutorials stages")
	}

	private void setButtonName(String elementName) {
		Element buttonLabel= screen.findElementByName("startSinglePlayer").getElements().get(0);
		buttonLabel.getRenderer(TextRenderer.class).setText(LanguageSelector.getString(elementName));
	}

	/**
	 * on end screen.
	 */
	public final void onEndScreen() {
	// screen.findElementByName("Start").setFocus();
	/*
	 * game.getContainer().getInput().clearControlPressedRecord();
	 * game.getContainer().getInput().clearKeyPressedRecord();
	 * game.getContainer().getInput().clearMousePressedRecord();
	 */
	}

	public final void startSingleplayer() {
		System.out.println("pressed: startSingleplayer()");
		LocationController.setMultiplayer(false);
		enterGame();
	}

	public final void startMultiplayer() {
		System.out.println("pressed: startMultiplayer()");
		LocationController.setMultiplayer(true);
		enterGame();
	}

	private void enterGame() {
		if (!resumingFromGame) {
			//VOID: the game starts without pressing a start button after returung from a running game
			System.out.println("enterGame");
			resumingFromGame = true;
			game.getContainer().setMouseGrabbed(true);
			game.enterState(2);
		} else {
			resumingFromGame = false;
		}
	}

	public void exitXSwing() {
		game.getContainer().exit();
		nifty.exit();
	}

}