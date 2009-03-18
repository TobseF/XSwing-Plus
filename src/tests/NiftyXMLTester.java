/*
 * @version 0.0 09.12.2008
 * @author 	Tobse F
 */
package tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;

public class NiftyXMLTester extends StateBasedGame implements ScreenController{
	
	public NiftyXMLTester() {
		super("NiftyXMLTester");
	}

	private static final String  resFolder = "restest/";
	private String niftyXMLFile = "xmlTest.xml";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer game = new AppGameContainer(new NiftyXMLTester());
			game.setMinimumLogicUpdateInterval(26);
			game.setMaximumLogicUpdateInterval(26);
			game.setDisplayMode(1024, 768, false);
			game.setClearEachFrame(false);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		NiftyGameState niftyGameState = new NiftyGameState(0);
		niftyGameState.fromXml(resFolder + niftyXMLFile, this);
		addState(niftyGameState);
	}

	public final void buttonPressed(){
		System.out.println("button pressed");
	}
	
	@Override
	public void bind(Nifty nifty, Screen screen) {
	}

	@Override
	public void onEndScreen() {
	}

	@Override
	public void onStartScreen() {
	}		

}