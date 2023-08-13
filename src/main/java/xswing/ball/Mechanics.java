/*
 * @version 0.0 23.04.2008
 * @author Tobse F
 */
package xswing.ball;

import lib.mylib.math.Point;
import xswing.events.BallEvent;
import xswing.events.BallEvent.BallEventType;
import xswing.events.BallEventListener;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The mechanic which executes all game logics (eg. finding 3 Balls in a row...)
 *
 * @see #checkOfThree()
 * @see #checkOfFive()
 */
public class Mechanics {

    private final BallTable ballTable;
    private final List<BallEventListener> eventListenerList = new LinkedList<BallEventListener>();

    private BallEventListener ballEventListener;

    private static final int[][] POSITIONS_FOR_SOROUNDING_CHECK = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public Mechanics(BallTable ballTable) {
        this.ballTable = ballTable;
    }

    public void checkOfThree(Ball ball) {
        if (isInRowWithThree(ball)) {
            ball.fireBallEvent(BallEventType.BALL_WITH_THREE_IN_A_ROW);
        }
    }

    /**
     * Horizontal row check
     *
     * @param ball to check
     * @return whether given Ball lies in the middle of three in horizontal a row
     */
    public boolean isInRowWithThree(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException("ball can't be null");
        }
        Point ballPos = ballTable.getField(ball);
        Ball ballLeft1 = ballTable.getBall(ballPos.x - 1, ballPos.y), ballLeft2, ballRight1, ballRight2;
        if (compare(ball, ballLeft1)) {
            ballLeft2 = ballTable.getBall(ballPos.x - 2, ballPos.y);
            if (compare(ball, ballLeft2)) {
                return true;
            } else {
                ballRight1 = ballTable.getBall(ballPos.x + 1, ballPos.y);
                return compare(ball, ballRight1);
            }
        } else {
            ballRight1 = ballTable.getBall(ballPos.x + 1, ballPos.y);
            if (compare(ball, ballRight1)) {
                ballRight2 = ballTable.getBall(ballPos.x + 2, ballPos.y);
                return compare(ball, ballRight2);
            }
        }
        return false;
    }

    /**
     * Vertical row check
     *
     * @param ball to check
     * @return whether the given ball lays as fifth ball (on the top) in a vertical stack
     */
    public boolean isInRowWithFive(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException("ball can't be null");
        }
        Point field = ballTable.getField(ball);
        for (int y = field.y - 1; y >= 0; y--) {
            Ball ballInStack = ballTable.getBall(field.x, y);
            if (compare(ballInStack, ball)) {
                if (field.y - y == 4) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    private static boolean compare(Ball a, Ball b) {
        return a != null && a.compare(b);
    }

    /**
     * Checks all surrounding Balls of the given
     */
    public List<Ball> getConnectedBalls(Ball ball) {
        List<Ball> ballsTemp = new ArrayList<Ball>();
        ballsTemp.add(ball);
        for (int i = 0; i < ballsTemp.size(); i++) {
            ballsTemp = getSurroundings(ballsTemp.get(i), ballsTemp);
        }
        return ballsTemp;
    }

    /**
     * Checks surrounding four Balls of the given whether they're null, an other or the same balls as the given. In last
     * case, the ball will be added to the given list -only if not happened already.
     */
    public List<Ball> getSurroundings(Ball ball, List<Ball> ballsTemp) {
        Point pos = ballTable.getField(ball);
        Ball checkinBall;
        for (int[] position : POSITIONS_FOR_SOROUNDING_CHECK) {
            if (isOnBalltable(pos.x + position[0], pos.y + position[1])) {
                checkinBall = ballTable.getBall(pos.x + position[0], pos.y + position[1]);
                if (compare(ball, checkinBall) && !ballsTemp.contains(checkinBall)) {
                    ballsTemp.add(checkinBall);
                }
            }
        }
        return ballsTemp;
    }

    private boolean isOnBalltable(int nextXToCheck, int nextYToCheck) {
        return nextXToCheck >= 0 && nextXToCheck < BallTable.LINES && nextYToCheck >= 0 && nextYToCheck < BallTable.LINES;
    }

    /**
     * Checks whether there are five balls on top of the other -and shrinks them
     */
    public void checkOfFive() {
        List<Ball> ballsT = new ArrayList<Ball>();
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                checkOfFive(ballsT, column, row);
            }
            ballsT.clear();
        }
    }

    private void checkOfFive(List<Ball> ballsT, int column, int row) {
        Ball ball = ballTable.getBall(column, row);
        if (ball != null) {
            ballsT.add(ball);
            if (ballsT.size() > 1 && ballsT.get(ballsT.size() - 2).getNr() != ball.getNr()) {
                ballsT.clear();
                ballsT.add(ball);
            } else if (ballsT.size() > 4) {
                notifyListener(new BallEvent(this, ballsT.get(0), BallEventType.BALL_WITH_FIVE_IN_A_ROW));
                shrinkRow(ballsT);
            }
        }
    }

    public void checkOfFive(Ball ball) {
        if (isInRowWithFive(ball)) {
            System.out.println("row with five");
            shrincRow(ball);
        }
    }

    private void shrincRow(Ball ball) {
        ball.fireBallEvent(BallEventType.BALL_WITH_FIVE_IN_A_ROW);
        Point initialBallPosition = ballTable.getField(ball);
        int weight = 0;
        Ball shrincedBall = ballTable.getBall(initialBallPosition.x, initialBallPosition.y - 4);
        List<Ball> ballsToShrink = ballTable.getBalls(initialBallPosition.x, initialBallPosition.y - 4, initialBallPosition.y);

        for (Ball ballToShrink : ballsToShrink) {
            weight += ballToShrink.getWeight();
            if (!ballToShrink.equals(shrincedBall)) {
                ballToShrink.fireBallEvent(BallEventType.BALL_CAUGHT_BY_EXPLOSION);
            }
        }
        shrincedBall.setWeight(weight);
        shrincedBall.fireBallEvent(BallEventType.BALL_CAUGHT_BY_SHRINC);
    }

    /**
     * Shrinks five balls to one heavy
     */
    private void shrinkRow(List<Ball> ballsT) {
        int weight = 0;
        for (Ball ball : ballsT.subList(1, ballsT.size())) {
            weight += ball.getWeight();
            ball.fireBallEvent(BallEventType.BALL_CAUGHT_BY_EXPLOSION);
        }
        Ball firstBall = ballsT.get(0);
        weight += firstBall.getWeight();
        firstBall.setWeight(weight);
        firstBall.fireBallEvent(BallEventType.BALL_CAUGHT_BY_SHRINC);
    }

    public void performWeight(int[] weights) {

        for (int i = 0; 0 < 8; i = i + 2) {
            // int w1=weights[i];
            // int w2=weights[i+1];
            // if(w1>w2)
        }
    }

    /**
     * Checks the 8th row for Balls -GameOverState
     */
    public boolean checkHight() {
        for (int i = 0; i < 8; i++) {
            if (!ballTable.isEmpty(i, 8)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the score of the balls to kill.<br>
     * score = sumOfallWeigts * balls;
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
     *
     * @param listener the {@code BallEventListener} to be added
     */
    public void addBallEventListener(BallEventListener listener) {
        eventListenerList.add(listener);
    }

    /**
     * Removes an {@code BallEventListener}
     *
     * @param listener to be removed
     */
    public void removeBallEventListener(BallEventListener listener) {
        eventListenerList.remove(listener);
    }

    /**
     * Notifies all {@code BallEventListener}s about a {@code BallEvent}
     *
     * @param event the {@code BallEvent} object
     * @see EventListenerList
     */
    protected synchronized void notifyListener(BallEvent event) {
        for (BallEventListener l : eventListenerList) {
            l.ballEvent(event);
        }
    }

    public BallTable getBallTable() {
        return ballTable;
    }

}