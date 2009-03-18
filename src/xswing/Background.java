/*
 * @version 0.0 25.02.2009
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import lib.mylib.object.SObject;

public class Background extends SObject{
	private Image singlePlayerBackground, 
		multiPlayerBackground;
	private Image background;
	public Background(boolean multiplayer) {
		try {
			singlePlayerBackground = new Image("res/" + "swing_background_b.jpg");
			multiPlayerBackground = new Image("res/" + "swing_background_b2.jpg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		background = multiplayer? multiPlayerBackground : singlePlayerBackground;
	}
	
	@Override
	public void render(Graphics g) {
		background.draw();
	}
}
