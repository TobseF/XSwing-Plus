package tests;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * This demonstrates a standard slick GameState that has been "overlayed" with a simple Nifty
 * GUI. Note that no NiftyGameState is used! Instead this uses the plain Nifty methods one
 * would use in a none Slick environment.
 * 
 * @author void
 */
public class OverlayMain2 extends StateBasedGame {

	public OverlayMain2(final String title) {
		super(title);
	}

	@Override
	public void initStatesList(final GameContainer container) throws SlickException {
		addState((GameState) new TestState2());
	}

	public static void main(final String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new OverlayMain2(
					"Nifty Overlay over Slick"));
			container.setDisplayMode(1024, 768, false);
			container.setTargetFrameRate(1000);
			container.setMinimumLogicUpdateInterval(1);
			container.setMinimumLogicUpdateInterval(2);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
