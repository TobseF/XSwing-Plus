/*
 * @version 0.0 25.02.2009
 * @author Tobse F
 */
package xswing.extras;

import static lib.mylib.options.Paths.RES_DIR;
import org.newdawn.slick.*;
import xswing.ball.Ball;

public class Stone extends Ball {

	public Stone(int level, int x, int y) {
		super(level, x, y);
		nr = 100;
		weight = 0;
		try {
			setImage(new Image(RES_DIR + "stone.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean compare(Ball ball) {
		return false;
	}

	@Override
	protected void drawNumber(Graphics g) {
	// do nothing
	}

}
