/*
 * @version 0.0 04.06.2010
 * @author Tobse F
 */
package xswing.ball;

import lib.mylib.MyTimer;
import lib.mylib.math.Point;
import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;
import xswing.HighScoreCounter;
import xswing.events.BallEvent;
import xswing.events.BallEvent.BallEventType;
import xswing.events.BallEventListener;

import java.util.*;

public class BallDisbandCollection implements Updateable, BallEventListener, Resetable {

    private final Ball initator;
    private final Point initatorPos;
    private final List<BallWithDistance> balls = new LinkedList<BallWithDistance>();
    private final HashSet<Ball> ballsIncluded = new HashSet<Ball>();
    /**
     * Time before killing starts
     */
    private final MyTimer timeBeforeKill;
    /**
     * Time steps of killing all balls with the same distance
     */
    private final MyTimer timeDuringKill;
    private static final int WAITING_BEFORE_KILL = 1200;
    private static final int WAITING_BEFORE_NEXT_STEP = 220;
    private final BallTable table;
    private PathFinder pathFinder = null;
    private final HighScoreCounter score;
    private final Mechanics mechanics;
    private boolean killingStarted = false;
    private boolean isKillingFinished = false;

    public BallDisbandCollection(Ball initator, BallTable ballTable, Collection<Ball> connectedBalls,
                                 HighScoreCounter score, Mechanics mechanics) {
        this.initator = initator;
        this.table = ballTable;
        this.score = score;
        this.mechanics = mechanics;
        pathFinder = new AStarPathFinder(ballTable, (BallTable.LINES - 1) * 2, false);

        timeBeforeKill = new MyTimer(WAITING_BEFORE_KILL, false, true) {

            @Override
            protected void timerAction() {
                kill();
            }
        };

        initatorPos = ballTable.getField(initator);

        timeDuringKill = new MyTimer(WAITING_BEFORE_NEXT_STEP, true, false) {

            @Override
            protected void timerAction() {
                if (!balls.isEmpty()) {
                    killAllBallsWithSameDistance();
                } else {
                    timeDuringKill.stop();
                    System.out.println("killed all balls");
                    isKillingFinished = true;
                }
            }
        };
        addAll(connectedBalls);
    }

    public void kill() {
        killingStarted = true;
        Collections.sort(balls);
        LinkedList<Ball> balls2 = new LinkedList<Ball>();
        for (BallWithDistance field : balls) {
            balls2.add(field.getBall());
        }
        score.score(mechanics.calculateScore(balls2));
        balls.get(0).getBall().fireBallEvent(BallEventType.BALL_EXPLODED);
        Log.debug("Started killing " + balls.size() + " Balls: " + balls);
        timeDuringKill.start();
    }

    private void killAllBallsWithSameDistance() {
        Log.debug("killAllBallsWithSameDistance");
        int distance = balls.get(0).getDistance();
        while (!balls.isEmpty() && balls.get(0).getDistance() == distance) {
            // balls.get(0).getBall().fireBallEvent(BallEventType.BALL_EXPLODED);
            balls.get(0).getBall().fireBallEvent(BallEventType.BALL_CAUGHT_BY_EXPLOSION);
            balls.remove(0);
        }
    }

    public void add(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException("Ball can't be null");
        }
        if (!killingStarted && !ballsIncluded.contains(ball)) {
            Log.debug("Added Ball for Killing " + ball);
            balls.add(new BallWithDistance(ball));
            ballsIncluded.add(ball);
            timeBeforeKill.reset();
            ball.addBallEventListener(this);
        }
    }

    public void addAll(Collection<Ball> balls) {
        for (Ball ball : balls) {
            add(ball);
        }
    }

    public boolean contains(Ball ball) {
        return ballsIncluded.contains(ball);
    }

    private class BallWithDistance extends Ball implements Comparable<BallWithDistance> {

        private final Ball ball;
        private final int distanceToInitator;

        public BallWithDistance(Ball ball) {
            super(ball.nr, ball.weight, ball.getX(), ball.getY(), null);
            this.ball = ball;
            if (ball.equals(initator)) {
                distanceToInitator = 0;
            } else {
                Point posBall = table.getField(ball);
                Path pathBall = pathFinder.findPath(initator, initatorPos.x, initatorPos.y, posBall.x, posBall.y);
                if (pathBall == null) {
                    Log.error("Ball in BallDisbandCollection (" + ball + ") on position " + posBall
                            + " is not connected with intial ball");
                    distanceToInitator = Integer.MAX_VALUE;
                } else {
                    distanceToInitator = pathBall.getLength();
                }
            }

        }

        @Override
        public int compareTo(BallWithDistance ball) {
            return distanceToInitator - ball.distanceToInitator - 1;
        }

        public Ball getBall() {
            return ball;
        }

        @Override
        public String toString() {
            return String.valueOf(distanceToInitator);
        }

        public int getDistance() {
            return distanceToInitator;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Ball && obj.equals(ball);
        }

        @Override
        public int hashCode() {
            return ball.hashCode();
        }
    }

    @Override
    public void update(int delta) {
        timeBeforeKill.update(delta);
        timeDuringKill.update(delta);
    }

    @Override
    public void ballEvent(BallEvent e) {
        if (!killingStarted) {
            switch (e.getBallEventType()) {
                case BALL_CAUGHT_BY_EXPLOSION:
                    //remove(e.getBall());
                    //System.out.println("BALL_CAUGHT_BY_EXPLOSION XXX");
                    break;
            }
        }
    }

    public boolean isKillingFinished() {
        return isKillingFinished;
    }

    @Override
    public void reset() {
        timeBeforeKill.stop();
        timeDuringKill.stop();
        balls.clear();
        ballsIncluded.clear();
    }
}
