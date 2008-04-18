/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Renderable;

public interface Drawable extends Renderable{
	public int x=0;
	public int y=0;
	
	public int getX();
	public int getY();
	
	public void update();
	
	public void draw(Graphics g);
	
	public void draw(float x, float y);
}
