/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing.start;

import java.util.Properties;
import java.util.logging.*;
import lib.mylib.tools.*;
import lib.mylib.util.*;
import lib.mylib.version.Version;
import org.newdawn.slick.*;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.*;
import org.newdawn.slick.util.Log;
import xswing.GamePanel;
import xswing.constant.Constants;
import xswing.gui.XSwingScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;

/**
 * Starts XSwing Plus with main menu.
 * 
 * @see #main(String[])
 */
public class XSwing extends StateBasedGame {

	public static final String VERSION = Version.getVersion();

	public static final int LOADING_SCREEN = 0, START_SCREEN = 1, GAME_PANEL = 2,
			GAME_OVER = 3;
	public static final String POST_BUG_URL = "http://xswing.net/postbug.php";

	private static String RES = Constants.RESOURCE_FOLDER;

	public XSwing() {
		super("XSwing Plus " + VERSION);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {

		XSwingScreenController controller = new XSwingScreenController(this);
		addState(new LoadingScreen(LOADING_SCREEN, new FadeOutTransition(Color.black),
				new EmptyTransition()));
		// MyNiftyState state = new MyNiftyState(START_SCREEN, RES + "gui/StartScreen.xml",
		// controller);
		NiftyGameState state = new NiftyGameState(START_SCREEN) {

			private Image cursor;

			@Override
			public void init(GameContainer container, StateBasedGame game)
					throws SlickException {
				super.init(container, game);
				cursor = new Image("restest/cursor.png");
			}

			@Override
			public void enter(GameContainer container, StateBasedGame game) {
				try {
					super.enter(container, game);
				} catch (SlickException e1) {
					e1.printStackTrace();
				}
				try {
					game.getContainer().setMouseCursor(cursor, 0, 0);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		};
		state.fromXml(RES + "gui/StartScreen.xml", controller);
		addState(state);
		addState(new GamePanel(GAME_PANEL));
	}

	/**
	 * @param args [0] debugmode (true= window mode, no mouse grabbing, debuginfos, show fps)
	 */
	public static void main(String[] args) {
		Properties properties = PropertiesTools.convertArgsToLinkedHashMap(args);
		boolean debug = Boolean.valueOf(properties.getProperty("debug")), fullsceen = Boolean
				.valueOf(properties.getProperty("fullscreen"));
		if (!debug) {
			Log.setLogSystem(new MyLogSystem());
		}
		Log.info("XSwing Version: " + VERSION);
		Log.info("Debugmode: " + debug);
		// Disable nifty logging
		Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
		// setInternalLogger();
		// Log.setVerbose(debug); //debug info logging
		try {
			AppGameContainer game = new AppGameContainer(new XSwing());
			game.setShowFPS(debug);
			game.setMinimumLogicUpdateInterval(26);
			// game.setMaximumLogicUpdateInterval(26);
			game.setDisplayMode(1024, 768, fullsceen);
			game.setClearEachFrame(false);
			game.setIcons(new String[] { RES + "16.png", RES + "32.png" });
			LoadingList.setDeferredLoading(true);
			// game.setMouseGrabbed(!debug);
			// game.setMouseCursor("restest/" + "hand.png", 1, 1);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
			new ErrorReporter(e, new ServerRequest(POST_BUG_URL));
		} catch (RuntimeException e) {
			new ErrorReporter(e, new ServerRequest(POST_BUG_URL));
			e.printStackTrace();
		}
	}

}