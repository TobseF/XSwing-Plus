/*
 * @version 0.0 22.07.2008
 * @author Tobse F
 */
package xswing.start;

import static lib.mylib.options.Paths.RES_DIR;
import lib.mylib.gamestates.LoadingScreen;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;
import xswing.*;

/**
 * Starts XSwing Plus immediately in <b>single player mode</b></br>
 * <ul>
 * <li>No Options on start</li>
 * <li>No Loading Scrren</li>
 * <li>No Main manu</li>
 * </ul>
 * 
 * @author Tobse
 */
public class XSwingFastStart extends StateBasedGame {

	public XSwingFastStart() {
		super("XSwing");
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
		// Log.setVerbose(debug); //debug info logging
		try {
			AppGameContainer game = new AppGameContainer(new XSwingFastStart());
			game.setShowFPS(debug);
			game.setDisplayMode(1024, 768, fullsceen);
			// game.setDisplayMode(460,390,fullsceen);
			game.setClearEachFrame(true);
			game.setIcons(new String[] { RES_DIR + "16.png", RES_DIR + "32.png" });
			game.setMouseGrabbed(!debug);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		LocationController.setMultiplayer(false);
		addState(new LoadingScreen(0));
		addState(new GamePanel(1));
	}

}