/*
 * @version 0.0 21.02.2009
 * @author 	Tobse F
 */
package xswing.events;

import java.util.EventObject;

import xswing.Ball;

public class BallEvent extends EventObject{
	/** The Ball which activated the <code>BallEvent</code>*/ 
	private Ball ball;
	
	public enum BallEventType{
		/** Ball hits the ground*/ BALL_HITS_GROUND, 
		/** Ball hits a Ball*/ BALL_HITS_BALL,  
		/** Ball activates an explosion*/ BALL_EXPLODED,
		/** Ball is activaded by an exploded Ball*/ BALL_CAUGHT_BY_EXPLOSION,
		/** Ball is was shrinced to a heave one*/ BALL_CAUGHT_BY_SHRINC,
		/** Three same neigbour are dedected*/ BALL_WITH_THREE_IN_A_ROW , 
		/** Is first Ball on a stack of five*/ BALL_WITH_FIVE_IN_A_ROW,
		/** Ball is wainting for shrinking with the four balls beneath*/ WAITING_FOR_SHRINK}
	
	/** The <code>BallEventType</code> of this <code>BallEvent</code>*/ 
	private final BallEventType ballEventType; 
	
	/**
	 * @param source The object that triggered the <code>BallEvent</code>
	 * @param ball The Ball which activated the <code>BallEvent</code>
	 * @param ballEventType The <code>BallEventType</code> of this <code>BallEvent</code>
	 */
	public BallEvent(Object source, Ball ball, BallEventType ballEventType) {
		super(source);
		this.ball = ball;
		this.ballEventType = ballEventType;
	}
	
	/**
	 * @return The <code>BallEventType</code> of this <code>BallEvent</code>
	 */
	public BallEventType getBallEventType() {
		return ballEventType;
	}
	
	/**
	 * @return The Ball which activated the <code>BallEvent</code>
	 */
	public Ball getBall() {
		return ball;
	}
}