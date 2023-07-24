/*
 * @version 0.0 30.05.2010
 * @author Tobse F
 */
package xswing.testcases;

import java.awt.Point;
import java.util.*;
import org.junit.*;
import xswing.*;
import xswing.ball.*;

public class BallTableTest {

	private BallTable ballTable = new BallTable();
	private List<Ball>balls = new ArrayList<Ball>();
	private	Ball ball1 = new Ball(0);
	private Ball ball2 = new Ball(1);
	private Ball ball3 = new Ball(2);
	private Ball ball4 = new Ball(3);

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
	}

	@Test
	public void setFieldPos(){
		Ball ball1 = new Ball(0);
		Point pos = ballTable.getFieldPosOnScreen(0, 0);
		ball1.setPos(pos);
		Assert.assertEquals(new Point(0, 0), ballTable.getField(ball1));
	}
	
	@Test
	public void testAddBall() {
		
		Assert.assertNotNull(ballTable.getBall(0, 0));
		Assert.assertNotNull(ballTable.getBall(0, 1));
		
		Assert.assertEquals(ball1,ballTable.getBall(0, 0));
		Assert.assertEquals(ball2,ballTable.getBall(0, 1));
		
		Assert.assertEquals(new Point(0, 0),ballTable.getField(ball1));
		Assert.assertEquals(new Point(0, 1),ballTable.getField(ball2));
	}
	
	@Test
	public void testGetBall(){
		Assert.assertEquals(balls, ballTable.getBalls(0));
		Assert.assertEquals(balls.subList(1, 4), ballTable.getBalls(0,1,3));
	}
}
