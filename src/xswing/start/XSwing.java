/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing.start;

import static lib.mylib.options.Paths.RES_DIR;
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

		ScreenControllerMainMenu controller = new ScreenControllerMainMenu(this);
		addState(new LoadingScreen(LOADING_SCREEN, new FadeOutTransition(Color.black), new EmptyTransition()));

		NiftyGameState mainMenu = new NiftyGameState(START_SCREEN);
		mainMenu.enableMouseImage(new Image("res/cursor.png"), 2, 2);

		// VOID: how to catch nifty exceptions
		mainMenu.fromXml("xswing/gui/main_menu.xml", controller);
		addState(mainMenu);
		addState(new GamePanel(GAME_PANEL));
	}

	/**
	 * @param args [0] debugmode (true= window mode, no mouse grabbing, debuginfos, show fps)
	 */
	public static void main(String[] args) {
		MyPropertys.setFile(XSwing.class);
		MyPropertys.setStrings(args);
		boolean debug = MyOptions.getBoolean(Args.debug);
		boolean fullsceen = MyOptions.getBoolean(Args.startGameInFullscreen);

		Log.setLogSystem(new MyLogSystem());
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
			game.setIcons(new String[] { RES_DIR + "16.png", RES_DIR + "32.png" });
			LoadingList.setDeferredLoading(true);
			game.setMouseGrabbed(true);
			// game.setMouseCursor("restest/" + "hand.png", 1, 1);
			game.start();
		} catch (Exception e) {
			new ErrorReporter(e, new ServerRequest(POST_BUG_URL));
			//e.printStackTrace();
		}
	}

}