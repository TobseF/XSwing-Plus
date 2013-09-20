/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing.start;

import static lib.mylib.options.Paths.RES_DIR;
import static xswing.properties.XSGameConfigs.getConfig;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import lib.mylib.ScalableGameState;
import lib.mylib.gamestates.LoadingScreen;
import lib.mylib.hacks.NiftyGameState;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.properties.GameConfig;
import lib.mylib.properties.Resolution;
import lib.mylib.tools.ErrorReporter;
import lib.mylib.tools.ServerRequest;
import lib.mylib.util.MyLogSystem;
import lib.mylib.util.MyOptions;
import lib.mylib.version.Version;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import xswing.GamePanel;
import xswing.MainGame;
import xswing.gui.ScreenControllerMainMenu;
import xswing.properties.XSGameConfigs;

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
	public final static String SCORE_SERVER_PATH = "";
	public final static String SCORE_LINE_SUBMIT_FILE = "submit_high_score_line.php";

	public XSwing() {
		super("XSwing Plus " + VERSION);
		// TODO: Put all res in a dir on export

	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		try {
			ScreenControllerMainMenu controller = new ScreenControllerMainMenu(this);
			addState(new LoadingScreen(LOADING_SCREEN, new FadeOutTransition(Color.black), new EmptyTransition()));
			NiftyGameState mainMenu = new NiftyGameState(START_SCREEN);
			if (MyOptions.getBoolean(Args.useNativeMouseCursor)) {
				Log.info("Using native mouse cursor");
				container.setMouseCursor(new Image("res/cursor.png"), 2, 2);
			} else {
				Log.warn("Using Nifty mouse cursor and grabbing mouse.");
				mainMenu.enableMouseImage(new Image("res/cursor.png"), 2, 2); // Nifty way
			}
			// VOID: how to catch nifty exceptions
			mainMenu.fromXml("xswing/gui/main_menu.xml", controller);
			addState(mainMenu);
			container.setForceExit(false);
			// GamePanel gamePanel = new GamePanel(GAME_PANEL);
			MainGame mainGame = new MainGame();
			container.setClearEachFrame(true);
			ScalableGameState scaledGame = new ScalableGameState(mainGame, 1920, 1080, true);
			scaledGame.setId(GAME_PANEL);
			// ResizeableGameState scaledGamePanel = new ResizeableGameState(gamePanel,
			// container.getWidth(),container.getHeight());
			addState(scaledGame);
		} catch (Throwable e) {
			new ErrorReporter(e, new ServerRequest(POST_BUG_URL), true);
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
		GameConfig config = XSGameConfigs.getConfig(MyOptions.getString(Args.configFile, XSGameConfigs.OPTION_FILE_NAME));
		boolean debug = MyOptions.hasProperty(Args.debug) ? MyOptions.getBoolean(Args.debug) : config.isDebugMode();
		boolean fullscreen = MyOptions.hasProperty(Args.fullscreen) ? MyOptions.getBoolean(Args.fullscreen) : config.getDisplayConfig()
				.isFullscreen();
		Resolution resolution = config.getDisplayConfig().getSelectedResolution();
		if (MyOptions.hasProperty(Args.resolution)) {
			resolution = parseResolution(MyOptions.getString(Args.resolution), resolution);
		}
		Log.setLogSystem(new MyLogSystem());
		Log.info("XSwing Version: " + VERSION);
		Log.info("Debugmode: " + debug);
		Log.info("Fullscreen: " + fullscreen);
		Log.info("Resolution: " + resolution.getWidth() + "x" + resolution.getHeight());
		// Disable nifty logging
		Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
		// Log.setVerbose(debug); //debug info logging
		try {
			XSwing xSwing = new XSwing();
			// ScalableGame scalableGame = new ScalableGame(xSwing, resolution.getWidth(), resolution.getHeight(),true);
			AppGameContainer game = new AppGameContainer(xSwing);
			game.setForceExit(false);
			game.setShowFPS(debug);
			game.setMinimumLogicUpdateInterval(config.getMinimumLogicUpdateInterval());
			// game.setMaximumLogicUpdateInterval(26);
			try {
				game.setDisplayMode(resolution.getWidth(), resolution.getHeight(), fullscreen);
			} catch (SlickException e) {
				if (fullscreen) {
					// If fullscreen is not supported, try it in window mode
					game.setDisplayMode(resolution.getWidth(), resolution.getHeight(), false);
				} else {
					throw e;
				}
			}
			game.setClearEachFrame(false);
			game.setIcons(new String[] { RES_DIR + "16.png", RES_DIR + "32.png" });
			LoadingList.setDeferredLoading(true);
			game.setMouseGrabbed(fullscreen);
			try {
				game.start();
			} catch (SlickException e) {
				if (fullscreen) {
					// If fullscreen is not supported, try it in window mode
					game.setFullscreen(false);
					game.start();
				} else {
					throw e;
				}
			}
		} catch (Throwable e) {
			new ErrorReporter(e, new ServerRequest(POST_BUG_URL), true);
			// e.printStackTrace();
		}
	}

	public static Resolution parseResolution(String resolutionAsString, Resolution defaultResolution) {
		if (resolutionAsString != null && resolutionAsString.matches("\\d+(x|\\*)\\d+")) {
			String[] values = resolutionAsString.split("x|\\*");
			return new Resolution(Integer.parseInt(values[0].trim()), Integer.parseInt(values[1].trim()));
		}
		return defaultResolution;
	}

}