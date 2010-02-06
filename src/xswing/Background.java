/*
 * @version 0.0 25.02.2009
 * @author Tobse F
 */
package xswing;

import static lib.mylib.options.Paths.RES_DIR;
import lib.mylib.object.SObject;
import org.newdawn.slick.*;

public class Background extends SObject {

	private Image singlePlayerBackground, multiPlayerBackground;
	private Image background;

	public Background(boolean multiplayer) {
		try {
			singlePlayerBackground = new Image(RES_DIR + "swing_background_b.jpg");
			multiPlayerBackground = new Image(RES_DIR + "swing_background_b2.jpg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		background = multiplayer ? multiPlayerBackground : singlePlayerBackground;
	}

	@Override
	public void render(Graphics g) {
		background.draw();
	}
}
