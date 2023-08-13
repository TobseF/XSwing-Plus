/*
 * @version 0.0 22.01.2009
 * @author Tobse F
 */
package xswing.ai;


import lib.mylib.math.Point;
import xswing.ball.Ball;
import xswing.ball.BallTable;
import xswing.ball.Mechanics;

import java.util.List;

public class Simulator {

    private int score = 0;
    private static long simTimes = 0;

    public Simulator() {
    }

    public BallTable simulate(BallTable ballTable) {
        return simulate(ballTable, null, 0);
    }

    public BallTable simulate(BallTable ballTable, Ball ballToRelease, int pos) {
        // public BallTable simStep(BallTable ballTable){
        BallTable ballTableCopy = ballTable.clone();
        if (ballToRelease != null) {
            ballTableCopy = dropBall(ballTableCopy, ballToRelease.clone(), pos);
        }
        // ballTableCopy = ballTable.clone();
        // dropAllBalls(ballTable);

        Mechanics mechanics = new Mechanics(ballTableCopy);
        // mechanics.checkOfFive();
        //mechanics.checkOfThree();
        List<Ball> ballsToKill = null;
        if (true/*mechanics.getWaitingForKill() != null*/) {
            // System.out.println("found three connected balls!");
            //	ballsToKill = mechanics.getConnectedBalls(mechanics.getWaitingForKill());
            score += mechanics.calculateScore(ballsToKill);
            for (Ball ball : ballsToKill) {
                Point position = ballTableCopy.getField(ball);
                ballTableCopy.getBalls()[position.x][position.y] = null;
            }
        }

        ballTableCopy = dropAllBalls(ballTableCopy);
        simTimes++;
        // if(simTimes%100 == 0)System.out.println("sim: "+simTimes);
        return ballTableCopy;
    }

    public BallTable dropBall(BallTable ballTable, Ball ballToRelease, int pos) {
        int columnHeight = ballTable.getColumnHeight(pos);
        if (columnHeight <= 7) {
            // System.out.println("bt: "+columnHeight + ballToRelease);
            // ballTable.getBalls()[pos][columnHeight] = ballToRelease;
            ballTable.setBall(pos, columnHeight, ballToRelease);
            // System.out.println(ballTable.printBallTable());
        }
        return ballTable;
    }

    public BallTable simulateAll(BallTable ballTable, Ball ballToRelease, int pos) {
        ballTable = simulate(ballTable, ballToRelease, pos);
        BallTable copy = simulate(ballTable);
        while (!ballTable.equals(copy)) {
            ballTable = copy;
            copy = simulate(ballTable);
        }
        return copy;
    }

    public BallTable simulateAll(BallTable ballTable) {
        return simulateAll(ballTable, null, 0);
    }

    public BallTable dropAllBalls(BallTable ballTable) {
        for (int y = 1; y < 9; y++) {
            for (int x = 0; x < 8; x++) {
                int height = y;
                while (height > 0 && ballTable.isEmpty(x, height - 1)) {
                    height--;
                }
                if (height != y) {
                    ballTable.setBall(x, height, ballTable.getBall(x, y));
                    ballTable.setBall(x, y, null);
                }
            }
        }
        return ballTable;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

}