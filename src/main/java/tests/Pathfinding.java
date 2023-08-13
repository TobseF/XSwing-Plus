/*
 * @version 0.0 04.06.2010
 * @author Tobse F
 */
package tests;


import lib.mylib.math.Point;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.PathFinder;
import xswing.ball.Ball;
import xswing.ball.BallDisbandCollection;
import xswing.ball.BallTable;

import java.util.LinkedList;

public class Pathfinding {

    public Pathfinding() {
        BallTable ballTable = new BallTable();
        ballTable.addBall(new Ball(0), 0);
        ballTable.addBall(new Ball(0), 0);
        ballTable.addBall(new Ball(0), 0);
        ballTable.addBall(new Ball(1), 1);
        ballTable.addBall(new Ball(1), 1);
        ballTable.addBall(new Ball(0), 1);
        ballTable.addBall(new Ball(0), 1);
        ballTable.addBall(new Ball(1), 2);
        ballTable.addBall(new Ball(1), 2);
        ballTable.addBall(new Ball(1), 2);
        ballTable.addBall(new Ball(0), 2);
        ballTable.addBall(new Ball(0), 3);
        ballTable.addBall(new Ball(0), 3);
        ballTable.addBall(new Ball(0), 3);
        ballTable.addBall(new Ball(0), 3);
        System.out.println(ballTable);

        PathFinder finder = new AStarPathFinder(ballTable, (BallTable.LINES - 1) * 2, false);
        Ball ball = ballTable.getBall(0, 0);
        Point start = ballTable.getField(ball);
        Point dest = new Point(0, 1);
        System.out.println(finder.findPath(ball, start.x, start.y, dest.x, dest.y).getLength());
        dest = new Point(1, 2);
        System.out.println(finder.findPath(ball, start.x, start.y, dest.x, dest.y).getLength());
        dest = new Point(1, 3);
        System.out.println(finder.findPath(ball, start.x, start.y, dest.x, dest.y).getLength());
        dest = new Point(3, 0);
        System.out.println(finder.findPath(ball, start.x, start.y, dest.x, dest.y).getLength());
        dest = new Point(3, 3);
        System.out.println(finder.findPath(ball, start.x, start.y, dest.x, dest.y).getLength());

        LinkedList<Ball> collection = new LinkedList<Ball>();
        collection.add(ballTable.getBall(0, 2));
        collection.add(ballTable.getBall(0, 1));
        collection.add(ballTable.getBall(1, 3));
        collection.add(ballTable.getBall(3, 3));
        collection.add(ballTable.getBall(0, 0));
        collection.add(ballTable.getBall(3, 0));
        collection.add(ballTable.getBall(2, 3));
        collection.add(ballTable.getBall(3, 2));
        collection.add(ballTable.getBall(3, 1));
        collection.add(ballTable.getBall(1, 2));
        BallDisbandCollection col = new BallDisbandCollection(ball, ballTable, collection, null, null);
        col.kill();
    }

    public static void main(String[] args) {
        new Pathfinding();
    }
}
