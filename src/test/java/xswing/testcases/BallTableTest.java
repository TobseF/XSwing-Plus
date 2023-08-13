/*
 * @version 0.0 30.05.2010
 * @author Tobse F
 */
package xswing.testcases;

import lib.mylib.math.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xswing.ball.Ball;
import xswing.ball.BallTable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BallTableTest {

    private final BallTable ballTable = new BallTable();
    private final List<Ball> balls = new ArrayList<Ball>();
    private final Ball ball1 = new Ball(0);
    private final Ball ball2 = new Ball(1);
    private final Ball ball3 = new Ball(2);
    private final Ball ball4 = new Ball(3);

    @Before
    public void setUp() throws Exception {
        balls.add(ball1);
        balls.add(ball2);
        balls.add(ball3);
        balls.add(ball4);
        ballTable.addBall(ball1, 0);
        ballTable.addBall(ball2, 0);
        ballTable.addBall(ball3, 0);
        ballTable.addBall(ball4, 0);
        System.out.println(ballTable);
    }

    @Test
    public void setFieldPos() {
        Ball ball1 = new Ball(0);
        Point pos = ballTable.getFieldPosOnScreen(0, 0);
        assertEquals(new Point(0, 0), pos);
        ball1.setPos(pos);
        assertEquals(new Point(0, 0), ballTable.getField(ball1));
    }

    @Test
    public void testAddBall() {

        Assert.assertNotNull(ballTable.getBall(0, 0));
        Assert.assertNotNull(ballTable.getBall(0, 1));

        assertEquals(ball1, ballTable.getBall(0, 0));
        assertEquals(ball2, ballTable.getBall(0, 1));

        assertEquals(new Point(0, 0), ballTable.getField(ball1));
        assertEquals(new Point(0, 1), ballTable.getField(ball2));
    }

    @Test
    public void testGetBall() {
        assertEquals(balls, ballTable.getBalls(0));
        assertEquals(balls.subList(1, 4), ballTable.getBalls(0, 1, 3));
    }
}
