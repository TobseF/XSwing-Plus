/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

import lib.mylib.Resetable;
import lib.mylib.SObject;
import lib.mylib.SpriteSheet;


/** Provides a moveable Cannon which releses the Balls */
public class Cannon extends SObject implements Resetable{
	/**Gap between balls*/
	private int gap=16; //
	/**Ball lenght*/
	private int ballLenght=48;
	/**The current column*/
	private int cannonPosition=0;
	
	private Ball ball=null;
	private BallTable ballTable;
	private Sound move;
	private Sound alarm;
	private SpriteSheet cannons;
	private Animation animationWarning;
	private Animation animationDanger;
	/** period of time of one anmation frame*/
	private final int duration=180;
	
	private Ball lastreleasedBall=null;
	
	
	/**
	 * @param cannons Sprite sheet with four three frames
	 * @param x The x position of the cannon when it's over the first hole (extremist left)
	 * @param y The y position of the cannon when it's over the first hole (extremist left)
	 * @param sounds The moving sound (0) and the alarm sound
	 * @param ballTable
	 */
	public Cannon(SpriteSheet cannons, int x, int y, Sound[] sounds, BallTable ballTable) {
		super(cannons.getSprite(0, 0), x, y);
		this.move=sounds[0];
		this.alarm=sounds[1];
		this.cannons=cannons;
		this.ballTable = ballTable;
		animationWarning=new Animation(cannons.getSprites(1),duration);
		animationDanger=new Animation(cannons.getSprites(2),duration);
		animationWarning.start();
		animationWarning.setPingPong(true);
		animationDanger.start();
		animationDanger.setPingPong(true);
	}	
	
	/**Moves the canon one step left*/
	public void moveLeft() {
		if(cannonPosition>=1){
			cannonPosition--;
			move.play();
		}
		setBallPos(ball);
	}
	
	/**Moves the canon one step right*/
	public void moveRight() {
		if(cannonPosition<7){
			cannonPosition++;
			move.play();
		}
		setBallPos(ball);
	}
	
	@Override
	public int getX() {
		return x+gap+cannonPosition*(ballLenght+gap);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(pic,getX()-12,y-3);
	}
	
	private void checkHigh(){
		int ballPileHeight=ballTable.getColumnHeight(cannonPosition);
		if(ballPileHeight<=6){ //no warning
			setPic(cannons.getSprite(0,0));
		}
		if(ballPileHeight==7){ //orange Warning
			setPic(animationWarning.getCurrentFrame());
		}
		if(ballPileHeight==8){ //red Warining
			setPic(animationDanger.getCurrentFrame());
			if(!alarm.playing())
				alarm.play();
		}
	}
	
	/**Lets the ball start moving and loads the next Ball out of the BallTable*/
	public void releaseBall(Ball nextBall){
		if(ball!=null){
			ball.toggleMoving(); //Ball should start falling
			ball.setPos(getX(),y+30); //Set the Ball position
			lastreleasedBall=ball;
		}
		
		setBall(ballTable.getBall(cannonPosition,11));
		ballTable.setBall(cannonPosition,11,ballTable.getBall(cannonPosition,12));
		ballTable.setBall(cannonPosition,12,nextBall);
		ball.setPos(getX(),y-22);
	}
	
	/**Returns the current column*/
	public int getPos(){
		return cannonPosition;
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
		if(ball!=null){
			//ball.setPos(getX(),y+8);
			ball.setPos(getX(),ball.getY());
		}
	}

	@Override
	public void update(int delta) {
		animationWarning.update(delta);
		animationDanger.update(delta);
		checkHigh();
		doBallEnterAnimation();
	}
	
	private void doBallEnterAnimation(){
		int ballPosition=y+8;
		if(ball!=null)
			if(ball.getY()<ballPosition){
				ball.setY(ball.getY()+3);
			}
	}

	@Override
	public void reset() {
		cannonPosition=0;
		ball=null;
		lastreleasedBall=null;
	}

	public boolean isReadyToReleaseBall(){
		return lastreleasedBall==null||lastreleasedBall.getY()>getY()+70;
	}
}
