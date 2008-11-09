/*
 * @version 0.0 05.05.2008
 * @author 	Tobse F
 */
package xswing.extras;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import xswing.Ball;

public class ExtraJoker extends Ball{

	public ExtraJoker(int level, int x, int y) {
		super(level, x, y);
		nr=99;
		weight=0;
		try {
			setPic(new Image("res/joker.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean compare(Ball ball) {
		return true;
	}
	
	@Override
	protected void drawNumber(Graphics g) {
		
		//do nothing
	}
}
