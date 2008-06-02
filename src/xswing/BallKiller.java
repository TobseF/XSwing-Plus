package xswing;

import java.util.ArrayList;
import java.util.List;
import xswing.Ball;
import xswing.HighScoreCounter;
import xswing.Mechanics;

public class BallKiller {
    List<Ball> ballsToKill = new ArrayList<Ball>();
    private static int WAITING_BEFORE_KILL = 260;
    private int timeBeforeKill = WAITING_BEFORE_KILL;
    private Mechanics mechanics;
    private HighScoreCounter score;

    public BallKiller(Mechanics mechanics, HighScoreCounter score) {
        this.mechanics = mechanics;
        this.score = score;
    }

    public void addBall(Ball ball) {
        this.ballsToKill.add(ball);
    }

    public void reset() {
        this.timeBeforeKill = WAITING_BEFORE_KILL;
    }

    public void update(int delta) {
        if (this.ballsToKill.size() > 0) {
            this.timeBeforeKill -= delta;
            if (this.timeBeforeKill <= 0) {
                this.killBalls();
            }
        }
    }

    private void killBalls() {
        Ball bTemp = this.ballsToKill.get(0);
        this.ballsToKill = this.mechanics.getConnectedBalls(bTemp);
        this.score.score(this.mechanics.calculateScore(this.ballsToKill));
        int i = 0;
        while (i < this.ballsToKill.size()) {
            Ball b = this.ballsToKill.get(i);
            b.kill(2);
            ++i;
        }
        this.reset();
        this.ballsToKill.clear();
    }
}
