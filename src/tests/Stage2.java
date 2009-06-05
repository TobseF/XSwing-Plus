/*
 * @version 0.0 19.07.2009
 * @author 	Tobse F
 */
package tests;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import lib.mylib.object.BasicGameState;


public class Stage2 extends BasicGameState{
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.blue);
		g.fillOval(100, 100, 100, 100);
		g.setColor(Color.white);
		g.drawString("State 1", 10, 10);
	}

}
