/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class SObject implements Drawable{
	protected int x;
	protected int y;
	protected Image pic;
	
	public SObject() {
	}
	
	public SObject(Image pic) {
		this.pic=pic;
	}
	
	public SObject(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public SObject(Image pic,int x, int y) {
		this(x,y);
		this.pic=pic;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setPos(int[] pos) {
		setPos(pos[0],pos[1]);
	}
	
	public void setPos(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(pic,x,y);
	}

	@Override
	public void draw(float x, float y) {
		
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void update() {
		
	}

}
