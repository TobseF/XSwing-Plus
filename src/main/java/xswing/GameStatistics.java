/*
 * @version 0.0 30.05.2010
 * @author 	Tobse F
 */
package xswing;

import lib.mylib.object.Resetable;
import xswing.events.BallEvent;
import xswing.events.BallEventListener;
import xswing.events.XSwingEvent;
import xswing.events.XSwingListener;


public class GameStatistics implements XSwingListener, BallEventListener, Resetable {
    /**
     * The number of released balls
     */
    public int releasedBalls;

    /**
     * The number of destroyed balls (disbanded or shrinked by a tower of five)
     */
    public int destroyedBalls;

    @Override
    public void gameEvent(XSwingEvent e) {
        switch (e.getGameEventType()) {
            case BALL_DROPPED:
                releasedBalls++;
                break;
            default:
                break;
        }

    }

    @Override
    public void reset() {
        releasedBalls = 0;
        destroyedBalls = 0;
    }

    @Override
    public void ballEvent(BallEvent e) {
        switch (e.getBallEventType()) {
            case BALL_CAUGHT_BY_EXPLOSION:
                destroyedBalls++;
                break;
        }
    }

    public int getDestroyedBalls() {
        return destroyedBalls;
    }


    public int getReleasedBalls() {
        return releasedBalls;
    }

}
