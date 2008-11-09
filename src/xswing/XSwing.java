/*
 * @version 0.0 22.07.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

public class XSwing extends StateBasedGame{

	public XSwing() {
		super("Xswing");
	}

	/**
	 * @param args[0] debugmode (true= window mode, no mouse grabbing, debuginfos, show fps)
	 */
	public static void main(String[] args) {
		boolean debug=false;
		if(args.length>0)
			debug=Boolean.valueOf(args[0]);
		boolean fullsceen=!debug;
		Log.info("Debugmode: "+debug);
		//Log.setVerbose(debug); //debug info logging
		try {
			AppGameContainer game = new AppGameContainer(new XSwing());
			game.setShowFPS(debug);
			game.setMinimumLogicUpdateInterval(26);
			game.setMaximumLogicUpdateInterval(26);
			game.setDisplayMode(1024,768,fullsceen);
			game.setClearEachFrame(false);			
			game.setIcons(new String[]{"res/16.png","res/32.png"});
			game.setMouseGrabbed(!debug);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new LoadingScreen(0));
		addState(new MainGame(1));
		//addState(new GameOver(2,300));
		
		/*addState(new EmptyState(1));
		addState(new GameOver(0,300));*/
	}

}
