/*
 * @version 0.0 18.06.2011
 * @author 	Tobse F
 */
package xswing.net;

import lib.mylib.highscore.HighScoreLine;
import lib.mylib.highscore.ScoreOnlineSubmitter;
import org.newdawn.slick.util.Log;
import xswing.start.XSwing;


public class ThreadedHighScoreSubmitter {
    public static class ScoreSubmitThread extends Thread {

        private final HighScoreLine score;
        private final String url;

        public ScoreSubmitThread(HighScoreLine score, String url) {
            this.score = score;
            this.url = url;
            start();
        }

        @Override
        public void run() {
            new ScoreOnlineSubmitter(score, url).submit();
        }
    }

    public void toggledCheckbox() {
        Log.info("toggled Checkbox");
    }

    public static void submitScore(String name, int score, long timeSinceStart, int realeasedBalls, int destroyedBalls) {
        HighScoreLine newScore = new HighScoreLine(score, name, timeSinceStart, realeasedBalls,
                destroyedBalls);
        Log.info("Submit score online: " + newScore);
        new ScoreSubmitThread(newScore, XSwing.XSWING_HOST_URL + XSwing.SCORE_SERVER_PATH + XSwing.SCORE_LINE_SUBMIT_FILE);
        System.out.println(XSwing.SCORE_LINE_SUBMIT_FILE);
    }
}
