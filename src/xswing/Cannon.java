/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Cannon extends SObject{
	/**Gap between balls*/
	private int gap=16; //
	/**Ball lenght*/
	private int ba=48;
	/**The current column*/
	private int pos=0;
	private Ball ball=null;
	private BallTable ballTable;
	
	public Cannon(Image pic, int x, int y) {
		super(pic, x, y);
	}	
	
	public void setBallTable(BallTable ballTable) {
		this.ballTable = ballTable;
	}
	
	/**Moves the canon one step left*/
	public void moveLeft() {
		if(pos>=1)pos--;
		if(ball!=null)
			ball.setPos(getX(),y);
	}
	
	/**Moves the canon one step right*/
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
	
	/**Lets the ball start moving and loads the next Ball out of the BallTable*/
	public void releaseBall(Ball nextBall){
		if(ball!=null){
			ball.toggleMoving();
			ball.setY(y+ba/2);
		}
		setBall(ballTable.getBall(pos,11));
		ballTable.setBall(pos,11,ballTable.getBall(pos,12));
		ballTable.setBall(pos,12,nextBall);
	}
	
	/**Returns the current column*/
	public int getPos(){
		return pos;
	}
	
	/**Returns the current Ball in the Cannon*/
	public Ball getBall() {
		return ball;
	}
	
	/**Sets a current Ball to the Cannon*/
	public void setBall(Ball ball){
		this.ball=ball;
		this.ball.setPos(getX(),y+8);
	}

}
