/*
 * @version 0.0 30.05.2008
 * @author Tobse F
 */
package xswing.ball;

import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;
import org.newdawn.slick.util.Log;
import xswing.HighScoreCounter;
import xswing.events.BallEvent;
import xswing.events.BallEvent.BallEventType;
import xswing.events.BallEventListener;

import java.util.LinkedList;
import java.util.List;

public class BallKiller implements Resetable, Updateable, BallEventListener {

    private final Mechanics mechanics;
    private final HighScoreCounter score;
    private final List<BallDisbandCollection> disbandCollections = new LinkedList<BallDisbandCollection>();

    private final BallTable ballTable;

    public BallKiller(Mechanics mechanics, HighScoreCounter score, BallTable ballTable) {
        this.mechanics = mechanics;
        this.score = score;
        this.ballTable = ballTable;
        reset();
    }

    public void addDisbandCollection(BallDisbandCollection disbandCollection) {
        disbandCollections.add(disbandCollection);
    }

    @Override
    public void reset() {
        for (Resetable ballSet : disbandCollections) {
            ballSet.reset();
        }
        disbandCollections.clear();
    }

    @Override
    public void update(int delta) {
        LinkedList<BallDisbandCollection> setsToRemove = new LinkedList<BallDisbandCollection>();
        for (BallDisbandCollection ballSet : disbandCollections) {
            ballSet.update(delta);
            if (ballSet.isKillingFinished()) {
                setsToRemove.add(ballSet);
            }
        }
        disbandCollections.removeAll(setsToRemove);
    }

    @Override
    public void ballEvent(BallEvent e) {
        if (e.getBallEventType().equals(BallEventType.ADDED_TO_PLAY_FIELD)) {
            System.out.println("Added to balltable");
            if (!addToDisbandRowIfConnected(e.getBall()) && mechanics.isInRowWithThree(e.getBall())) {
                System.out.println("ball with three in a row");
                List<Ball> connectedBalls = mechanics.getConnectedBalls(e.getBall());
                if (connectedBalls.size() >= 3) {
                    System.out.println("Found connected balls " + connectedBalls.size() + " " + connectedBalls);
                    addDisbandCollection(new BallDisbandCollection(e.getBall(), ballTable, connectedBalls, score,
                            mechanics));
                }
            }
        }
    }

    private boolean addToDisbandRowIfConnected(Ball ball) {
        boolean addBall = false;
        if (!disbandCollections.isEmpty()) {
            List<Ball> sorroundings = new LinkedList<Ball>();
            mechanics.getSurroundings(ball, sorroundings);
            for (BallDisbandCollection ballSet : disbandCollections) {
                for (Ball foundBall : sorroundings) {
                    if (ballSet.contains(foundBall)) {
                        Log.debug("Added ball while waiting for kill");
                        ballSet.add(ball);
                        addBall = true;
                        break;
                    }
                }
            }
        }
        return addBall;
    }
}