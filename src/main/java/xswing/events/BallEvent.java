/*
 * @version 0.0 21.02.2009
 * @author Tobse F
 */
package xswing.events;


import lib.mylib.math.Point;
import xswing.ball.Ball;

import java.util.EventObject;

public class BallEvent extends EventObject {

    /**
     * The Ball which activated the <code>BallEvent</code>
     */
    private final Ball ball;
    /**
     * Field position in the BallTable, <code>null</code> means unknown.
     */
    private Point positionInBallTable = null;

    public enum BallEventType {
        /**
         * Ball hits the ground
         */
        BALL_HITS_GROUND,
        /**
         * Ball hits a Ball
         */
        BALL_HITS_BALL,
        /**
         * Ball activates an explosion
         */
        BALL_EXPLODED,
        /**
         * Ball is activated by an exploded Ball
         */
        BALL_CAUGHT_BY_EXPLOSION,
        /**
         * Ball was shrinked to a heavy one
         */
        BALL_CAUGHT_BY_SHRINC,
        /**
         * Three same neighbor are detected
         */
        BALL_WITH_THREE_IN_A_ROW,
        /**
         * Is first Ball on a stack of five
         */
        BALL_WITH_FIVE_IN_A_ROW,
        /**
         * Ball is waiting for shrinking with the four balls beneath
         */
        WAITING_FOR_SHRINK,
        /**
         * Ball was added to a BallTable.
         */
        ADDED_TO_BALLTABLE,
        /**
         * Ball was added to a BallTable in the area of the play field.
         */
        ADDED_TO_PLAY_FIELD
    }

    /**
     * The <code>BallEventType</code> of this <code>BallEvent</code>
     */
    private final BallEventType ballEventType;

    /**
     * @param source        The object that triggered the <code>BallEvent</code>
     * @param ball          The Ball which activated the <code>BallEvent</code>
     * @param ballEventType The <code>BallEventType</code> of this <code>BallEvent</code>
     */
    public BallEvent(Object source, Ball ball, BallEventType ballEventType) {
        super(source);
        this.ball = ball;
        this.ballEventType = ballEventType;
    }

    /**
     * @param source        The object that triggered the <code>BallEvent</code>
     * @param ball          The Ball which activated the <code>BallEvent</code>
     * @param ballEventType The <code>BallEventType</code> of this <code>BallEvent</code>
     */
    public BallEvent(Object source, Ball ball, BallEventType ballEventType, Point positionInBallTable) {
        this(source, ball, ballEventType);
        this.positionInBallTable = positionInBallTable;
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


    public Point getPositionInBallTable() {
        return positionInBallTable;
    }
}