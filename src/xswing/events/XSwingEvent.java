/*
 * @version 0.0 22.02.2009
 * @author 	Tobse F
 */
package xswing.events;

import java.util.EventObject;

import xswing.Ball;

public class XSwingEvent extends EventObject{
	private Ball ball = null;
	private int value = 0;
	
	public enum GameEventType{
		BALL_DROPPED, CANNON_MOVED_LEFT, CANNON_MOVED_RIGHT, 
		PRESSED_LEFT, PRESSED_RIGHT, PRESSED_DOWN, GAME_PAUSED, 
		GAME_SARTED, NEW_SCORE};
		
	private final GameEventType gameEventType;
		
	public XSwingEvent(Object source, GameEventType gameEventType) {
		super(source);
		this.gameEventType = gameEventType;
	}
	
	public XSwingEvent(Object source, GameEventType gameEventType, Ball ball) {
		this(source, gameEventType);
		this.ball = ball;
	}
	
	public int getValue() {
		return value;
	}
	
	public Ball getBall() {
		return ball;
	}

	public GameEventType getGameEventType() {
		return gameEventType;
	}
}