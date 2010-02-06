/*
 * @version 0.0 19.07.2009
 * @author Tobse F
 */
package tests;

import lib.mylib.object.BasicGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class TestStage1 extends BasicGameState {

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.blue);
		g.fillOval(100, 100, 100, 100);
		g.setColor(Color.white);
		g.drawString("State 1", 10, 10);
	}

}
