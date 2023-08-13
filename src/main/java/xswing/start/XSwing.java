/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing.start;

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
import org.newdawn.slick.*;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;
import xswing.MainGame;
import xswing.gui.ScreenControllerMainMenu;
import xswing.properties.XSGameConfigs;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lib.mylib.options.Paths.RES_DIR;

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
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        try {
            ScreenControllerMainMenu controller = new ScreenControllerMainMenu(this);
            addState(new LoadingScreen(LOADING_SCREEN, new FadeOutTransition(Color.black), new EmptyTransition()));
            NiftyGameState mainMenu = new NiftyGameState(START_SCREEN);
            setMouseCursor(container, mainMenu);
            mainMenu.fromXml("xswing/gui/main_menu.xml", controller);
            addState(mainMenu);
            container.setForceExit(false);
            // GamePanel gamePanel = new GamePanel(GAME_PANEL);
            MainGame mainGame = new MainGame();
            container.setClearEachFrame(true);
            ScalableGameState scaledGame = new ScalableGameState(mainGame, container.getWidth(), container.getHeight(), true);
            scaledGame.setId(GAME_PANEL);
            addState(scaledGame);
        } catch (Throwable e) {
            e.printStackTrace();
            new ErrorReporter(e, new ServerRequest(POST_BUG_URL), true);
        }
    }

    private void setMouseCursor(GameContainer container, NiftyGameState mainMenu) throws SlickException {
        if (MyOptions.getBoolean(Args.useNativeMouseCursor)) {
            Log.info("Using native mouse cursor");
            container.setMouseCursor(new Image("res/cursor.png"), 2, 2);
        } else {
            Log.warn("Using Nifty mouse cursor and grabbing mouse.");
            mainMenu.enableMouseImage(new Image("res/cursor.png"), 2, 2); // Nifty way
        }
    }

    /**
     * @param
     */
    public static void main(String[] args) {
        MyOptions.setFile(XSwing.class);
        MyOptions.setStrings(args);
        Log.info("Current dir" + new File(".").getAbsolutePath());

        Log.info("Args: " + Arrays.toString(args));
        GameConfig config = XSGameConfigs.getConfig(MyOptions.getString(Args.configFile, XSGameConfigs.OPTION_FILE_NAME));
        boolean debug = isDebug(config);
        boolean fullscreen = isFullscreen(config);
        Resolution resolution = getResolution(config);

        Log.setLogSystem(new MyLogSystem());
        logInfo(debug, fullscreen, resolution);
        disableNiftyLogging();
        try {
            XSwing xSwing = new XSwing();
            // ScalableGame scalableGame = new ScalableGame(xSwing, resolution.getWidth(), resolution.getHeight(),true);
            AppGameContainer game = new AppGameContainer(xSwing);
            game.setForceExit(false);
            game.setShowFPS(debug);
            game.setMinimumLogicUpdateInterval(config.getMinimumLogicUpdateInterval());
            // game.setMaximumLogicUpdateInterval(26);
            setResolution(fullscreen, resolution, game);
            game.setClearEachFrame(false);
            setIcon(game, "icon");
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
            e.printStackTrace();
            new ErrorReporter(e, new ServerRequest(POST_BUG_URL), true);
        }
    }

    private static void setIcon(AppGameContainer game, String iconName) throws SlickException {
        game.setIcons(new String[]{RES_DIR + iconName + "_16.png", RES_DIR + iconName + "_32.png"});
    }

    private static void setResolution(boolean fullscreen, Resolution resolution, AppGameContainer game) throws SlickException {
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
    }

    private static void disableNiftyLogging() {
        Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
        // Log.setVerbose(debug); //debug info logging
    }

    private static void logInfo(boolean debug, boolean fullscreen, Resolution resolution) {
        Log.info("XSwing Version: " + VERSION);
        Log.info("Debugmode: " + debug);
        Log.info("Fullscreen: " + fullscreen);
        Log.info("Resolution: " + resolution.getWidth() + "x" + resolution.getHeight());
    }

    private static Resolution getResolution(GameConfig config) {
        Resolution resolution = config.getDisplayConfig().getSelectedResolution();
        if (MyOptions.hasProperty(Args.resolution)) {
            resolution = parseResolution(MyOptions.getString(Args.resolution), resolution);
        }
        return resolution;
    }

    private static boolean isDebug(GameConfig config) {
        return MyOptions.hasProperty(Args.debug) ? MyOptions.getBoolean(Args.debug) : config.isDebugMode();
    }

    private static boolean isFullscreen(GameConfig config) {
        return MyOptions.hasProperty(Args.fullscreen) ? MyOptions.getBoolean(Args.fullscreen) : config.getDisplayConfig()
                .isFullscreen();
    }

    public static Resolution parseResolution(String resolutionAsString, Resolution defaultResolution) {
        if (resolutionAsString != null && resolutionAsString.matches("\\d+(x|\\*)\\d+")) {
            String[] values = resolutionAsString.split("x|\\*");
            return new Resolution(Integer.parseInt(values[0].trim()), Integer.parseInt(values[1].trim()));
        }
        return defaultResolution;
    }

}