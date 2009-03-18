/*
 * @version 0.0 23.04.2008
 * @author Tobse F
 */
package xswing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import xswing.events.BallEvent;
import xswing.events.BallEventListener;
import xswing.events.BallEvent.BallEventType;

/**
 * The mechanic which executes all game logics (eg. finding 3 Balls in a row...)
 * 
 * @see #checkOfThree()
 * @see #checkOfFive()
 */
public class Mechanics {
	private Ball[][] balls;
	private BallTable ballTable;
	private List<Ball> ballsTemp = new ArrayList<Ball>();
	private Ball waitingForKill; // TODO: change to Listener
	private EventListenerList eventListenerList = new EventListenerList();

	private static final int[][] POSITIONS_FOR_SOROUNDING_CHECK = { { 0, 1 }, { 1, 0 },
			{ 0, -1 }, { -1, 0 } };

	public Mechanics(BallTable ballTable) {
		this.ballTable = ballTable;
		balls = this.ballTable.getBalls();
	}

	/** Kills balls if ther're more than two alikes in one row -successive */
	public void checkOfThree() {
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				Ball ball = balls[column][row];
				if (ball != null) { // is field filled?
					ballsTemp.add(ball);
					if (ballsTemp.size() > 1
							&& !ball.compare(ballsTemp.get(ballsTemp.size() - 2))) {
						ballsTemp.clear();
						ballsTemp.add(ball);
					} else {
						checkRow(ballsTemp);
					}
				} else {
					ballsTemp.clear();// clear if there is an empty field
				}
			}
			ballsTemp.clear();// clear every row
		}
	}
	
	public void checkOfThree(Ball ball) {
		if(isInRowWithThree(ball)){
			ball.fireBallEvent(BallEventType.BALL_WITH_THREE_IN_A_ROW);
		}
	}
	
	/** Horizontal row check
	 * @param ball to check
	 * @return wether given Ball lies in the middle of three in horizontal a row
	 */
	public boolean isInRowWithThree(Ball ball) {
		if (ball == null)
			throw new IllegalArgumentException("ball can't be null");
		Point position = ballTable.getField(ball);
		Ball ballLeft1 = ballTable.getBall(position.x - 1, position.y), ballLeft2, ballRight1, ballRight2;
		if (ballLeft1 != null && ballLeft1.compare(ball)) {
			ballLeft2 = ballTable.getBall(position.x - 2, position.y);
			if (ballLeft2 != null && ballLeft2.compare(ball)) {
				return true;
			} else {
				ballRight1 = ballTable.getBall(position.x + 1, position.y);
				if (ballRight1 != null && ballRight1.compare(ball)) {
					return true;
				}
			}
		} else {
			ballRight1 = ballTable.getBall(position.x + 1, position.y);
			if (ballRight1 != null && ballRight1.compare(ball)) {
				ballRight2 = ballTable.getBall(position.x + 2, position.y);
				if (ballRight2 != null && ballRight2.compare(ball)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/** Vertikal row check
	 * @param ball to check
	 * @return wehter the given ball lais as fifh ball in a vertikal stack
	 */
	public boolean isInRowWithFive(Ball ball){
		if (ball == null)
			throw new IllegalArgumentException("ball can't be null");
		Point position = ballTable.getField(ball);
		for(int y = position.y -1; y >= 0; y--){
			Ball ballInStack = ballTable.getBall(position.x, y);
			if(ballInStack != null && ballInStack.compare(ball)){
				if(position.y - y == 4){
					System.out.println("d");
					return true;
				}
			}else{
				break;
			}
		}
		return false;
	}
	
	
	public Ball getWaitingForKill() {
		return waitingForKill; //TODO: change to listener!
	}

	/** Kills the saved balls if the're more than two */
	private void checkRow(List<Ball> ballsTemp) {
		if (ballsTemp.size() > 2) {
			notifyListener(new BallEvent(this, ballsTemp.get(0), BallEventType.BALL_WITH_THREE_IN_A_ROW));
		waitingForKill = ballsTemp.get(0);
		}
	}

	/** Checks alls surrounding Balls of the given */
	public List<Ball> getConnectedBalls(Ball ball) {
		List<Ball> ballsTemp = new ArrayList<Ball>();
		ballsTemp.add(ball);
		for (int i = 0; i < ballsTemp.size(); i++) {
			ballsTemp = getSurroundings(ballsTemp.get(i), ballsTemp);
		}
		return ballsTemp;
	}

	/**
	 * Checks surrounding four Balls of the given wether they're null, an other or the same
	 * balls as the given. In last case, the ball will be added to ballsTemp -only if not
	 * happened already
	 */
	private List<Ball> getSurroundings(Ball ball, List<Ball> ballsTemp) {
		Point pos = ballTable.getField(ball);
		Ball checkinBall;
		for (int[] position : POSITIONS_FOR_SOROUNDING_CHECK) {
			int nextXToCheck = pos.x + position[0], nextYToCheck = pos.y + position[1];
			if (nextXToCheck >= 0 && nextXToCheck < 8 && nextYToCheck >= 0 && nextYToCheck < 8) {
				checkinBall = ballTable.getBall(pos.x + position[0], pos.y + position[1]);
				if (checkinBall != null && checkinBall.compare(ball)
						&& !ballsTemp.contains(checkinBall)) {
					ballsTemp.add(checkinBall);
				}
			}
		}
		return ballsTemp;
	}

	/** Checks wether there are five balls on top of the other -and shrincs them */
	public void checkOfFive() {
		List<Ball> ballsT = new ArrayList<Ball>();
		for (int column = 0; column < 8; column++) {
			for (int row = 0; row < 8; row++) {
				Ball ball = ballTable.getBall(column, row);
				if (ball != null) {
					ballsT.add(ball);
					if (ballsT.size() > 1
							&& ballsT.get(ballsT.size() - 2).getNr() != ball.getNr()) {
						ballsT.clear();
						ballsT.add(ball);
					} else if (ballsT.size() > 4) {
						notifyListener(new BallEvent(this, ballsT.get(0), BallEventType.BALL_WITH_FIVE_IN_A_ROW));
						shrinkRow(ballsT);
					}
				}
			}
			ballsT.clear();
		}
	}

	public void checkOfFive(Ball ball){
		if(isInRowWithFive(ball)){
			System.out.println("row with five");
			shrincRow(ball);		
		}
	}
	
	private void shrincRow(Ball ball){
		ball.fireBallEvent(BallEventType.BALL_WITH_FIVE_IN_A_ROW);
		Point position = ballTable.getField(ball);
		int weight = 0;
		for(int y = position.y ; y > position.y - 4; y-- ){
			weight += ballTable.getBall(position.x, y).getWeight();
			ballTable.getBall(position.x, y).fireBallEvent(BallEventType.BALL_CAUGHT_BY_EXPLOSION);
		}
		Ball shrincedBall =ballTable.getBall(position.x, position.y - 4);
		shrincedBall.setWeight(weight + shrincedBall.getWeight());
		shrincedBall.fireBallEvent(BallEventType.BALL_CAUGHT_BY_SHRINC);
	}

	/** Shrincs five balls to one havy */
	private void shrinkRow(List<Ball> ballsT) {
		int weight = 0;
		for (int i = ballsT.size() - 1; i > 0; i--) {
			weight += ballsT.get(i).getWeight();
			ballsT.get(i).fireBallEvent(BallEventType.BALL_CAUGHT_BY_EXPLOSION);
		}
		weight += ballsT.get(0).getWeight();
		ballsT.get(0).setWeight(weight);
		ballsT.get(0).fireBallEvent(BallEventType.BALL_CAUGHT_BY_SHRINC);
	}
	
	

	public void performWeight(int[] weights) {

		for (int i = 0; 0 < 8; i = i + 2) {
			// int w1=weights[i];
			// int w2=weights[i+1];
			// if(w1>w2)
		}
	}

	/** Checks the 8th row for Balls -GameOverState */
	public boolean checkHight() {
		for (int i = 0; i < 8; i++) {
			if (!ballTable.isEmpty(i, 8)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calculates the score of the balls to kill.<br> score = sumOfallWeigts * balls;
	 */
	public int calculateScore(List<Ball> ballsTemp) {
		int score = 0;
		for (Ball ball : ballsTemp) {
			score += ball.getWeight();
		}
		return score * ballsTemp.size();
	}

	/**
	 * Adds an {@code BallEventListener}
	 * @param listener the {@code BallEventListener} to be added
	 */
	public void addBallEventListener(BallEventListener listener) {
		eventListenerList.add(BallEventListener.class, listener);
	}

	/**
	 * Removes an {@code BallEventListener}
	 * @param listener to be removed
	 */
	public void removeBallEventListener(BallEventListener listener) {
		eventListenerList.remove(BallEventListener.class, listener);
	}

	/**
	 * Notifies all {@code BallEventListener}s about a {@code BallEvent}
	 * @param event the {@code BallEvent} object
	 * @see EventListenerList
	 */
	protected synchronized void notifyListener(BallEvent event) {
		for (BallEventListener l : eventListenerList.getListeners(BallEventListener.class))
			l.ballEvent(event);
	}

}