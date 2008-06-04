/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import lib.SObject;
import lib.SpriteSheet;


/** Provides a moveable Cannon which releses the Balls */
public class Cannon extends SObject{
	/**Gap between balls*/
	private int gap=16; //
	/**Ball lenght*/
	private int ba=48;
	/**The current column*/
	private int pos=0;
	
	private Ball ball=null;
	private BallTable ballTable;
	private Sound move,alarm;
	private SpriteSheet cannons;
	private Animation anim1,anim2;
	private final int duration=180;
	
	public Cannon(SpriteSheet cannons, int x, int y, Sound[] sounds) {
		super(cannons.getSprite(0, 0), x, y);
		this.move=sounds[0];
		this.alarm=sounds[1];
		this.cannons=cannons;
		anim1=new Animation(cannons.getSprites(1),duration);
		anim2=new Animation(cannons.getSprites(2),duration);
		anim1.start();
		anim1.setPingPong(true);
		anim2.start();
		anim2.setPingPong(true);
	}	
	
	public void setBallTable(BallTable ballTable) {
		this.ballTable = ballTable;
	}
	
	/**Moves the canon one step left*/
	public void moveLeft() {
		if(pos>=1){
			pos--;
			move.play();
		}
		setBallPos(ball);
//		checkHigh();
	}
	
	/**Moves the canon one step right*/
	public void moveRight() {
		if(pos<7){
			pos++;
			move.play();
		}
		setBallPos(ball);
//		checkHigh();
	}
	
	@Override
	public int getX() {
		return x+gap+pos*(ba+gap);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(pic,getX()-12,y-3);
	}
	
	private void checkHigh(){
		int height=ballTable.getColumnHeight(pos);
		if(height<=6){
			setPic(cannons.getSprite(0,0));
		}
		if(height==7){
			setPic(anim1.getCurrentFrame());
		}
		if(height==8){
			setPic(anim2.getCurrentFrame());
			if(!alarm.playing())
				alarm.play();
		}
	}
	
	/**Lets the ball start moving and loads the next Ball out of the BallTable*/
	public void releaseBall(Ball nextBall){
		if(ball!=null){
			ball.toggleMoving();
			setBallPos(ball);
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
		setBallPos(ball);
	}
	
	/**Sets the postion from the Cannon tho the Ball*/
	private void setBallPos(Ball ball){
		if(ball!=null)
			ball.setPos(getX(),y+8);
	}

	public void update(int delta) {
		anim1.update(delta);
		anim2.update(delta);
		checkHigh();
	}

}
