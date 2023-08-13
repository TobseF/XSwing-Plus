/*
 * @version 0.0 22.02.2009
 * @author Tobse F
 */
package xswing.events;

import xswing.ball.Ball;

import java.util.EventObject;

public class XSwingEvent extends EventObject implements Comparable<XSwingEvent> {

    /**
     * Ball which comes along with this Event. (Only for Ball dropped)
     */
    private Ball ball = null;
    /**
     * Time when this Event was triggered
     */
    private final long timeStamp;

    /**
     * Type of GameEvent
     */
    public enum GameEventType {
        /**
         * Ball was dropped
         */
        BALL_DROPPED,
        /**
         * Cannon moved left
         */
        CANNON_MOVED_LEFT,
        /**
         * Cannon moved right
         */
        CANNON_MOVED_RIGHT,
        /**
         * Key left was pressed
         */
        PRESSED_LEFT,
        /**
         * Key right was pressed
         */
        PRESSED_RIGHT,
        /**
         * Key down was pressed
         */
        PRESSED_DOWN,
        /**
         * Game was started (is now running)
         */
        GAME_STARTED,
        /**
         * Game was paused (is now in pause)
         */
        GAME_PAUSED,
        /**
         * Game was resumed from Pause (is now playing)
         */
        GAME_RESUMED,
        /**
         * Game was stopped before Game Over, eg. by ESC
         */
        GAME_STOPPED,
        /**
         * Game was finished by Game Over
         */
        GAME_OVER,
        /**
         * Score changed
         */
        NEW_SCORE,
        /**
         * New highscore was set
         */
        NEW_HIGHSCORE
    }

    /**
     * GameEventType of this event
     */
    private final GameEventType gameEventType;

    public XSwingEvent(Object source, GameEventType gameEventType) {
        super(source);
        this.gameEventType = gameEventType;
        timeStamp = System.currentTimeMillis();
    }

    public XSwingEvent(Object source, GameEventType gameEventType, Ball ball) {
        this(source, gameEventType);
        this.ball = ball;
    }

    /**
     * @return {@link #ball}
     */
    public Ball getBall() {
        return ball;
    }

    public GameEventType getGameEventType() {
        return gameEventType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public int compareTo(XSwingEvent event) {
        if (event.getTimeStamp() > getTimeStamp()) {
            return 1;
        } else {
            return (event.getTimeStamp() < getTimeStamp() ? -1 : 0);
        }
    }
}