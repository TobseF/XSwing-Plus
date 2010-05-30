/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing.start;

import static lib.mylib.options.Paths.RES_DIR;
import java.util.Arrays;
import java.util.logging.*;
import lib.mylib.gamestates.LoadingScreen;
import lib.mylib.hacks.NiftyGameState;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.tools.*;
import lib.mylib.util.*;
import lib.mylib.version.Version;
import org.newdawn.slick.*;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.*;
import org.newdawn.slick.util.Log;
import xswing.GamePanel;
import xswing.gui.ScreenControllerMainMenu;

/**
 * Starts XSwing Plus with main menu.
 * 
 * @see #main(String[])
 */
public class XSwing extends StateBasedGame {

	public static final String VERSION = Version.getVersion();

	public static final int LOADING_SCREEN = 0, START_SCREEN = 1, GAME_PANEL = 2, GAME_OVER = 3;
	public static final String XSWING_HOST_URL = "http://xswing.net/";
	public static final String POST_BUG_URL = XSWING_HOST_URL + "postbug.php";

	public XSwing() {
		super("XSwing Plus " + VERSION);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		try {
			ScreenControllerMainMenu controller = new ScreenControllerMainMenu(this);
			addState(new LoadingScreen(LOADING_SCREEN, new FadeOutTransition(Color.black), new EmptyTransition()));
			container.getHeight();
			container.getWidth();
			NiftyGameState mainMenu = new NiftyGameState(START_SCREEN);
			// VOID: how to enable mouse image without grabbed mouse
			try {
				container.setMouseCursor(new Image("res/cursor.png"), 2, 2);
			} catch (NoSuchMethodError e) {
				Log.warn("No native Mouse cursor supported. Now using Nifty and grabbing mouse.");
				mainMenu.enableMouseImage(new Image("res/cursor.png"), 2, 2); // Nifty way
				container.setMouseGrabbed(true);
			}
			// VOID: how to catch nifty exceptions
			mainMenu.fromXml("xswing/gui/main_menu.xml", controller);
			addState(mainMenu);
			addState(new GamePanel(GAME_PANEL));
		} catch (Throwable e) {
			new ErrorReporter(e, new ServerRequest(POST_BUG_URL));
			// e.printStackTrace();
		}
	}

	/**
	 * @param
	 */
	public static void main(String[] args) {
		MyOptions.setFile(XSwing.class);
		MyOptions.setStrings(args);
		Log.info("Args: " + Arrays.toString(args));
		boolean debug = MyOptions.getBoolean(Args.debug);
		boolean fullsceen = MyOptions.getBoolean(Args.startGameInFullscreen);

		Log.setLogSystem(new MyLogSystem());
		Log.info("XSwing Version: " + VERSION);
		Log.info("Debugmode: " + debug);
		Log.info("Fullstreen: " + fullsceen);
		// Disable nifty logging
		Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
		// Log.setVerbose(debug); //debug info logging
		try {
			AppGameContainer game = new AppGameContainer(new XSwing());
			game.setShowFPS(debug);
			game.setMinimumLogicUpdateInterval(26);
			// game.setMaximumLogicUpdateInterval(26);
			try {
				game.setDisplayMode(1024, 768, fullsceen);
			} catch (SlickException e) {
				if (fullsceen) {
					// If fullscreen is not supported, try it in window mode
					game.setDisplayMode(1024, 768, false);
				} else {
					throw e;
				}
			}
			game.setClearEachFrame(false);
			game.setIcons(new String[] { RES_DIR + "16.png", RES_DIR + "32.png" });
			LoadingList.setDeferredLoading(true);
			game.setMouseGrabbed(fullsceen);
			try {
				game.start();
			} catch (SlickException e) {
				if (fullsceen) {
					// If fullscreen is not supported, try it in window mode
					game.setFullscreen(false);
					game.start();
				}else{
					throw e;
				}
			}
		} catch (Throwable e) {
			new ErrorReporter(e, new ServerRequest(POST_BUG_URL));
			// e.printStackTrace();
		}
	}

}