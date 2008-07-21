package xswing;

import java.util.ArrayList;
import java.util.List;
import xswing.Ball;
import xswing.BallTable;

public class Mechanics {
    private Ball[][] balls;
    private BallTable ballTable;
    private List<Ball> ballsTemp = new ArrayList<Ball>();

    public Mechanics(BallTable ballTable) {
        this.ballTable = ballTable;
        this.balls = this.ballTable.getBalls();
    }

    public void checkOfThree() {
        int row = 0;
        while (row < 8) {
            int column = 0;
            while (column < 8) {
                Ball ball = this.balls[column][row];
                if (ball != null) {
                    this.ballsTemp.add(ball);
                    if (this.ballsTemp.size() > 1) {
                        if (!ball.compare(this.ballsTemp.get(this.ballsTemp.size() - 2))) {
                            this.ballsTemp.clear();
                            this.ballsTemp.add(ball);
                        } else {
                            this.checkRow(this.ballsTemp);
                        }
                    }
                } else {
                    this.ballsTemp.clear();
                }
                ++column;
            }
            this.ballsTemp.clear();
            ++row;
        }
    }

    public Ball[][] getBalls() {
        return this.balls;
    }

    private void checkRow(List<Ball> ballsTemp) {
        if (ballsTemp.size() > 2) {
            ballsTemp.get(0).kill(1);
        }
    }

    public void getConnectedBalls(Ball b, int i) {
        this.getConnectedBalls(this.ballTable.getBall(this.ballTable.getField(b)[0], this.ballTable.getField(b)[1]));
    }

    public List<Ball> getConnectedBalls(Ball ball) {
        List<Ball> ballsTemp = new ArrayList<Ball>();
        ballsTemp.add(ball);
        int i = 0;
        while (i < ballsTemp.size()) {
            ballsTemp = this.getSurroundings((Ball)ballsTemp.get(i), ballsTemp);
            ++i;
        }
        return ballsTemp;
    }

    private List<Ball> getSurroundings(Ball ball, List<Ball> ballsTemp) {
        int[] pos = this.ballTable.getField(ball);
        int[][] nArrayArray = new int[4][];
        int[] nArray = new int[2];
        nArray[1] = 1;
        nArrayArray[0] = nArray;
        int[] nArray2 = new int[2];
        nArray2[0] = 1;
        nArrayArray[1] = nArray2;
        int[] nArray3 = new int[2];
        nArray3[1] = -1;
        nArrayArray[2] = nArray3;
        int[] nArray4 = new int[2];
        nArray4[0] = -1;
        nArrayArray[3] = nArray4;
        int[][] positions = nArrayArray;
        int i = 0;
        while (i < positions.length) {
            Ball checkinBall = this.ballTable.getPlayFieldBall(pos[0] + positions[i][0], pos[1] + positions[i][1]);
            if (checkinBall != null && checkinBall.compare(ball) && !ballsTemp.contains(checkinBall)) {
                ballsTemp.add(checkinBall);
            }
            ++i;
        }
        return ballsTemp;
    }

    public void checkOfFive() {
        ArrayList<Ball> ballsT = new ArrayList<Ball>();
        int column = 0;
        while (column < 8) {
            int row = 0;
            while (row < 8) {
                Ball b = this.ballTable.getBall(column, row);
                if (b != null) {
                    ballsT.add(b);
                    if (ballsT.size() > 1) {
                        if (((Ball)ballsT.get(ballsT.size() - 2)).getNr() != b.getNr()) {
                            ballsT.clear();
                            ballsT.add(b);
                        } else if (ballsT.size() > 4) {
                            this.shrinkRow(ballsT);
                        }
                    }
                }
                ++row;
            }
            ballsT.clear();
            ++column;
        }
    }

    private void shrinkRow(List<Ball> ballsT) {
        int weight = 0;
        int i = ballsT.size() - 1;
        while (i > 0) {
            weight += ballsT.get(i).getWeight();
            ballsT.get(i).kill(4);
            --i;
        }
        ballsT.get(0).setWeight(weight += ballsT.get(0).getWeight());
    }

    public void performWeight(int[] weights) {
        int i = 0;
        while (true) {
            int w1 = weights[i];
            int n = weights[i + 1];
            i += 2;
        }
    }

    public int calculateScore(List<Ball> ballsTemp) {
        int score = 0;
        int i = 0;
        while (i < ballsTemp.size()) {
            score += ballsTemp.get(i).getWeight();
            ++i;
        }
        return score * ballsTemp.size();
    }
}
