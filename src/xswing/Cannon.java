/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Cannon extends SObject{
	/**Gap between balls*/
	int gap=16; //
	/**Ball lenght*/
	int ba=48;
	/**The current column*/
	private int pos=0;
	Ball ball=null;
	
	Image image2;
	
	public Cannon(Image pic, int x, int y) {
		super(pic, x, y);
	}	
	
	public void moveLeft() {
		if(pos>=1)pos--;
		if(ball!=null)
			ball.setPos(getX(),y);
	}
	
	public void moveRight() {
		if(pos<7)pos++;
		if(ball!=null)
			ball.setPos(getX(),y);
	}
	
	@Override
	public int getX() {
		return x+gap+pos*(ba+gap);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(pic,getX()-12,y-3);
	}
	
	public void releaseBall(){
		if(ball!=null){
			ball.toggleMoving();
			ball.setY(y+ba/2);
		}
	}
	
	public int getPos(){
		return pos;
	}
	
	public void setBall(Ball ball){
		this.ball=ball;
		this.ball.setPos(getX(),y+8);
	}

}
