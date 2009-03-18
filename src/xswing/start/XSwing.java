/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing.start;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import lib.mylib.util.LoadingScreen;
import lib.mylib.util.PropertiesTools;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import xswing.GamePanel;
import xswing.constant.Constants;
import xswing.gui.XSwingScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;

/**
 * Starts the Game with menue.
 * 
 * @see #main(String[]) to start game in debug mode
 */
public class XSwing extends StateBasedGame {
	public static final String VERSION = "v0.344";
	
	public static final int LOADING_SCREEN = 0,
			START_SCREEN = 1, GAME_PANEL =2, GAME_OVER = 3;
	
	private static String RES = Constants.RESOURCE_FOLDER;
	public XSwing() {
		super("XSwing Plus " + VERSION);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		XSwingScreenController controller = new XSwingScreenController(this);
		addState(new LoadingScreen(LOADING_SCREEN, 
				new FadeOutTransition(Color.black), new EmptyTransition()));;
		//MyNiftyState state = new MyNiftyState(START_SCREEN, RES + "gui/StartScreen.xml", controller);
		NiftyGameState state= new NiftyGameState(START_SCREEN);
		state.fromXml(RES + "gui/StartScreen.xml", controller);
		addState(state);
		addState(new GamePanel(GAME_PANEL));
	}

	/**
	 * @param args [0] debugmode (true= window mode, no mouse grabbing, debuginfos, show fps)
	 */
	public static void main(String[] args) {
		Properties properties = PropertiesTools.convertArgsToLinkedHashMap(args);
		boolean debug = Boolean.valueOf( properties.getProperty("debug")),
				fullsceen = Boolean.valueOf( properties.getProperty("fullscreen"));;
		Log.info("Debugmode: " + debug);
		// Disable nifty logging
		Logger.getLogger("de.lessvoid.nifty").setLevel(Level.WARNING);
		
		// Log.setVerbose(debug); //debug info logging
		try {
			AppGameContainer game = new AppGameContainer(new XSwing());
			game.setShowFPS(debug);
			game.setMinimumLogicUpdateInterval(26);
			//game.setMaximumLogicUpdateInterval(26);
			game.setDisplayMode(1024, 768, fullsceen);
			game.setClearEachFrame(false);
			game.setIcons(new String[] { RES +"16.png", RES + "32.png" });
			game.setMouseGrabbed(!debug);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}