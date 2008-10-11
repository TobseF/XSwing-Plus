package xswing;

import java.util.Arrays;
import lib.mylib.Resetable;
import lib.mylib.SObject;
import xswing.Ball;

public class BallTable
extends SObject
implements Resetable {
    private int ballA = 48;
    public static final int gap = 16;
    private int h;
    private Ball[][] balls = new Ball[8][13];

    public BallTable(int x, int y) {
        super(x, y);
        this.h = this.ballA * 8;
    }

    public Ball[][] getBalls() {
        return this.balls;
    }

    public void setBall(int x, int y, Ball ball) {
        if (ball != null) {
            ball.setPos(this.getFieldPosOnScreen(x, y));
        }
        this.balls[x][y] = ball;
    }

    public Ball getPlayFieldBall(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return this.balls[x][y];
        }
        return null;
    }

    public Ball getBall(int x, int y) {
        return this.balls[x][y];
    }

    public int[] getField(int x, int y) {
        int posX = (int)((double)(x - this.x + 16) / (double)(this.ballA + 16));
        double posYTemp = (double)(this.y + this.h - y) / (double)this.ballA;
        int posY = (int)posYTemp;
        if (posYTemp % 1.0 == 0.0) {
            // empty if block
        }
        return new int[]{posX, --posY};
    }

    public int[] getField(SObject ball) {
        return this.getField(ball.getX(), ball.getY());
    }

    public boolean isEmpty(int posX, int posY) {
        return this.balls[posX][posY] == null;
    }

    public boolean isEmpty(int[] pos) {
        return this.isEmpty(pos[0], pos[1]);
    }

    public void printBallTable() {
        BallTable.printBallTable(this.balls);
    }

    public static void printBallTable(Ball[][] balls) {
        int i = balls[0].length;
        while (i > 0) {
            int ii = 0;
            while (ii < balls.length) {
                if (balls[ii][i - 1] != null) {
                    System.out.print(" " + String.format("%02d", balls[ii][i - 1].getNr()));
                } else {
                    System.out.print(" --");
                }
                ++ii;
            }
            System.out.println("");
            --i;
        }
    }

    public boolean isOverGrid(int x, int y) {
        return y >= this.y - 48;
    }

    public int[] getFieldPosOnScreen(int posX, int posY) {
        return new int[]{this.x + 16 + posX * (this.ballA + 16), this.y + this.h - (posY + 1) * this.ballA};
    }

    public int[] getFieldPosOnScreen(int[] pos) {
        return this.getFieldPosOnScreen(pos[0], pos[1]);
    }

    public int[] getFieldPos(int posX, int posY) {
        return new int[]{16 + posX * (this.ballA + 16), this.h - posY * this.ballA};
    }

    public void clear() {
        Ball[][] ballArray = this.balls;
        int n = this.balls.length;
        int n2 = 0;
        while (n2 < n) {
            Object[] b = ballArray[n2];
            Arrays.fill(b, null);
            ++n2;
        }
    }

    public void removeBall(Ball ball) {
        int[] tabelPos = this.getField(ball.getX(), ball.getY());
        this.setBall(tabelPos[0], tabelPos[1], null);
    }

    public int getColumnWeight(int column) {
        int i = 0;
        int row = 0;
        while (row < 8) {
            if (this.balls[column][row] != null) {
                i += this.balls[column][row].getWeight();
            }
            ++row;
        }
        return i;
    }

    public int getColumnHeight(int column) {
        int i = 0;
        int row = 0;
        while (row < 8) {
            if (this.balls[column][row] != null) {
                ++i;
            }
            ++row;
        }
        return i;
    }

    @Override
    public void reset() {
        this.clear();
    }
}
