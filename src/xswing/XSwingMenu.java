/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import xswing.gui.XSwingScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;

/**
 * Starts the Game with menue.
 * 
 * @see #main(String[]) to start game in debug mode
 */
public class XSwingMenu extends StateBasedGame {

	public XSwingMenu() {
		super("XSwing");
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		XSwingScreenController controller = new XSwingScreenController(this);
		addState(new LoadingScreen(0));
		NiftyGameState state = new NiftyGameState(1);
		state.fromXml("res/gui/StartScreen.xml", controller);
		addState(state);
		addState(new MainGame(2));
	}

	/**
	 * @param args [0] debugmode (true= window mode, no mouse grabbing, debuginfos, show fps)
	 */
	public static void main(String[] args) {
		boolean debug = false;
		if (args.length > 0) {
			debug = Boolean.valueOf(args[0]);
		}
		boolean fullsceen = !debug;
		Log.info("Debugmode: " + debug);
		// Disable nifty logging
		Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
		// Log.setVerbose(debug); //debug info logging
		try {
			AppGameContainer game = new AppGameContainer(new XSwingMenu());
			game.setShowFPS(debug);
			game.setMinimumLogicUpdateInterval(26);
			game.setMaximumLogicUpdateInterval(26);
			game.setDisplayMode(1024, 768, fullsceen);
			game.setClearEachFrame(false);
			game.setIcons(new String[] { "res/16.png", "res/32.png" });
			game.setMouseGrabbed(!debug);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}