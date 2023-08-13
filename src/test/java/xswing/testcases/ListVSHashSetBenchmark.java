package xswing.testcases;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import org.junit.BeforeClass;
import org.junit.Test;
import xswing.ball.Ball;

import java.util.ArrayList;
import java.util.List;

public class ListVSHashSetBenchmark extends AbstractBenchmark {
    static List<Ball> ballsList;
    static Ball ball;

    @BeforeClass
    public static void beforeClass() {
        ballsList = new ArrayList<Ball>(8 * 8);
        for (int i = 0; i < 15; i++) {
            ballsList.add(new Ball(i));
        }
        ball = new Ball(9);
    }


    @BenchmarkOptions(benchmarkRounds = 20, warmupRounds = 2)
    @Test
    public void testBEnch() throws Exception {
        ballsList.contains(ball);
    }
}