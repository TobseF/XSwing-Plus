/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package lib.mylib;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/** A basic Object on the Screen, which can be moved and drawn */
public class SObject implements Drawable, Updateable{
	protected int x,y;
	protected Image pic=null;
	
	public SObject() {
	}
	
	public SObject(Image pic) {
		this.pic = pic;
	}
	
	/**Sets an image which is drawn with draw
	 * @param pic I Image
	 */
	public void setPic(Image pic) {
		this.pic = pic;
	}
	
	public SObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public SObject(Image pic,int x, int y) {
		this(x,y);
		this.pic = pic;
	}
	
	/** Sets the X-Position on the Screen
	 * @param x -in pixels
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/** Sets the Y-Position on the Screen
	 * @param y -in pixels
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	public void setPos(int[] pos) {
		setPos(pos[0],pos[1]);
	}
	
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(Graphics g) {
		if(pic!=null)
			g.drawImage(pic,x,y);
	}

	@Override
	public void draw(float x, float y) {
		
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	@Override
	public void update(int delta) {		
	}


}
