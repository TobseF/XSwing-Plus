/*
 * @version 0.0 19.12.2008
 * @author Tobse F
 */
package xswing.testcases;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import xswing.ball.Ball;
import xswing.ball.BallTable;
import xswing.ball.Mechanics;
import xswing.testcases.util.BallTableCreator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BallTableBenchmark {

    private static BallTable balltable1;
    private static final List<Integer> listData = new ArrayList<Integer>(8 * 8);
    private static final HashSet<Integer> hashData = new HashSet<Integer>(8 * 8);
    private static final List<TestBall> ballData = new ArrayList<TestBall>(8 * 8);

    static {
        init();
    }

    @BeforeClass
    public static void init() {
        String field = "-- -- -- -- -- -- -- -- " + //
                "-- -- -- -- -- -- -- -- " + //
                "-- -- -- -- -- -- -- -- " + //
                "-- -- -- -- -- -- -- -- " + //
                "-- -- -- -- -- -- -- -- " + //
                "01 -- -- -- -- -- -- -- " + //
                "01 01 01 -- -- -- -- -- " + //
                "01 02 01 01 01 01 -- 03";
        balltable1 = BallTableCreator.getBallTable(field, 8, 8);
        System.out.println(balltable1);
        int balls = 5;
        System.out.println(balls);
        for (int i = 0; i < balls; i++) {
            listData.add(i);
            hashData.add(i);
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8 && (y * 8 + x) < balls; x++) {
                ballData.add(new TestBall(x, y, 3));
            }
        }
    }


    public void listTest() {
        for (int t = 0; t < 1000; t++) {
            List<Integer> list = new ArrayList<Integer>(8 * 8);
            for (Integer i : listData) {
                if (!list.contains(i)) {
                    list.add(i);
                }
            }
        }
    }

    @Test()
    public void ballListTest() {
        for (int t = 0; t < 1000; t++) {
            List<TestBall> list = new ArrayList<TestBall>(8 * 8);
            for (TestBall i : ballData) {
                if (!list.contains(i)) {
                    list.add(i);
                }
            }
        }
    }


    public void hashTest() {
        for (int t = 0; t < 1000; t++) {
            List<Integer> list = new ArrayList<Integer>(8 * 8);
            HashSet<Integer> hashSet = new HashSet<Integer>();
            for (Integer i : listData) {
                if (!hashSet.contains(i)) {
                    list.add(i);
                    hashSet.add(i);
                }
            }
        }
    }

    @Test
    public void ballHashTest() {
        for (int t = 0; t < 1000; t++) {
            List<TestBall> list = new ArrayList<TestBall>(8 * 8);
            BallListSet ballSet = new BallListSet(8, 8);
            for (TestBall i : ballData) {
                if (!ballSet.contains(i)) {
                    list.add(i);
                    ballSet.add(i);
                }
            }
        }
    }

    @Ignore
    @Test
    public void getConnectedBalls() {
        Mechanics mechanics = new Mechanics(balltable1);
        for (int i = 0; i < 1000; i++) {
            List<Ball> connected = mechanics.getConnectedBalls(balltable1.getBall(4, 0));
        }
        // System.out.println(connected);
    }

    private static class TestBall {

        int id;
        int color;
        private int x, y;
        static int idCounter;

        public TestBall(int x, int y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
            id = idCounter++;
        }

        public TestBall(int color) {
            this.color = color;
            id = idCounter++;
        }

        public int getColor() {
            return color;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + id;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            TestBall other = (TestBall) obj;
            return id == other.id;
        }
    }

    private static class BallListSet {

        private final TestBall[][] balls;

        public BallListSet(int width, int height) {
            balls = new TestBall[width][height];

        }

        public void add(TestBall ball) {
            balls[ball.getX()][ball.getY()] = ball;
        }

        public boolean contains(TestBall ball) {
            //int a = ball.getX() * 123 / 324 +234;
            //int b = ball.getY() * 143 / 3 -234;
            TestBall ballInFiel = balls[ball.getX()][ball.getY()];
            return ballInFiel != null && ballInFiel.equals(ball);
        }

    }

}
