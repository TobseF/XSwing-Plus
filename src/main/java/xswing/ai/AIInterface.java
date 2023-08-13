/*
 * @version 0.0 08.01.2009
 * @author Tobse F
 */
package xswing.ai;

import lib.mylib.object.Updateable;
import xswing.Cannon;
import xswing.ball.Ball;
import xswing.ball.BallTable;
import xswing.events.XSwingEvent;
import xswing.events.XSwingEvent.GameEventType;
import xswing.events.XSwingListener;

import java.util.LinkedList;

public class AIInterface implements Updateable {

    private final BallTable ballTable;
    private final Cannon cannon;
    private final Simulator simulator;
    private int timeAfterLastUpdate = 0;
    private final LinkedList<Integer> zuege = new LinkedList<Integer>();
    private final Ball lastBall = null;
    private final XSwingListener listener;

    public AIInterface(XSwingListener listener, BallTable ballTable, Cannon cannon) {
        this.listener = listener;
        this.ballTable = ballTable;
        this.cannon = cannon;
        simulator = new Simulator();
        zuege.add(cannon.getPos());
    }

    public synchronized void runAI(int delta) {
        timeAfterLastUpdate += delta;
        int minTime = 180;
        if (zuege.isEmpty()) {
            if (lastBall != null && lastBall.isMoving()) {
                return;
            }
            bestePositionBerechnen();
        } else if (timeAfterLastUpdate >= minTime && zuege.getFirst() < cannon.getPos()) { // Kanone
            // nach
            // links
            // bewegen
            timeAfterLastUpdate = 0;
            listener.gameEvent(new XSwingEvent(this, GameEventType.CANNON_MOVED_LEFT));
        } else if (timeAfterLastUpdate >= minTime && zuege.getFirst() > cannon.getPos()) { // Kanone
            // nach
            // rechts
            // bewegen
            timeAfterLastUpdate = 0;
            listener.gameEvent(new XSwingEvent(this, GameEventType.CANNON_MOVED_RIGHT));
        } else if (timeAfterLastUpdate >= minTime && zuege.getFirst() == cannon.getPos()) { // Ball
            // fallen
            // lassen
            timeAfterLastUpdate = 0;
            zuege.removeFirst();
            listener.gameEvent(new XSwingEvent(this, GameEventType.PRESSED_DOWN));
        }
    }

    private void bestePositionBerechnen() {
        Ball ball;
        BallTable t1, t2, t3;
        int min = 10000, baelle = 0;
        for (int i = 0; i < 8; i++) {
            baelle += ballTable.getColumnHeight(i);
        }
        // System.out.println("Debug - " + baelle + " B�lle:\n" + ballTable.printBallTable());
        zuege.add(0);
        zuege.add(0);
        zuege.add(0);
        for (int i = 0; i < 8; i++) {
            if (ballTable.getColumnHeight(i) == 8) {
                continue; // Game over, nicht ber�cksichtigen
            }
            t1 = simulator.simulateAll(ballTable, cannon.getBall(), i);
            for (int j = 0; j < 8; j++) {
                if (t1.getColumnHeight(j) == 8) {
                    continue; // Game over, nicht ber�cksichtigen
                }
                t2 = simulator.simulateAll(t1, ballTable.getBall(i, 11), j);
                if (i == j) {
                    ball = ballTable.getBall(j, 12);
                } else {
                    ball = ballTable.getBall(j, 11);
                }
                for (int k = 0; k < 8; k++) {
                    if (t2.getColumnHeight(k) == 8) {
                        continue; // Game over, nicht ber�cksichtigen
                    }
                    t3 = simulator.simulateAll(t2, ball, k);
                    int tmp = spielfeldBewerten(t3, baelle);
                    if (tmp < min) {
                        min = tmp;
                        zuege.set(0, i);
                        zuege.set(1, j);
                        zuege.set(2, k);
                    }
                }
            }
        }
    }

    private int spielfeldBewerten(BallTable spielfeld, int alteBallzahl) {
        int wert = 0, baelle = 0;
        for (int i = 0; i < 8; i++) {
            wert += spielfeld.getColumnHeight(i);
            wert += spielfeld.getColumnWeight(i);
            baelle += spielfeld.getColumnHeight(i);
            for (int b = 0; b < spielfeld.getColumnHeight(i); b++) {
                Ball ballMitte = spielfeld.getBall(i, b);
                Ball ballLinks = spielfeld.getBall(i - 1, b);
                Ball ballRechts = spielfeld.getBall(i + 1, b);
                Ball ballOben = spielfeld.getBall(i, b + 1);
                Ball ballUnten = spielfeld.getBall(i, b - 1);
                if (ballMitte == null) {
                    continue;
                }
                if (ballLinks != null && ballLinks.compare(ballMitte)) {
                    wert -= 1;
                }
                if (ballRechts != null && ballRechts.compare(ballMitte)) {
                    wert -= 1;
                }
                if (ballOben != null && ballOben.compare(ballMitte)) {
                    wert -= 1;
                }
                if (ballUnten != null && ballUnten.compare(ballMitte)) {
                    wert -= 1;
                }
                if (ballLinks != null && ballRechts != null && ballLinks.compare(ballRechts)
                        && !ballLinks.compare(ballMitte)) {
                    wert += 10;
                }
            }
        }
        wert += Math.pow(baelle - alteBallzahl, 3);
        return wert;
    }

    @Override
    public void update(int delta) {
        runAI(delta);
    }
}