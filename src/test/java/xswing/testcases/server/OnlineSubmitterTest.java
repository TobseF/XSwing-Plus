/*
 * @version 0.0 27.09.2009
 * @author Tobse F
 */
package xswing.testcases.server;

import lib.mylib.highscore.EasyCrypter;
import lib.mylib.highscore.HighScoreLine;
import lib.mylib.highscore.HighScoreTable;
import lib.mylib.highscore.ScoreOnlineSubmitter;
import lib.mylib.http.EasyPostString;
import lib.mylib.options.DefaultArgs.Args;
import org.junit.Before;
import org.junit.Test;
import xswing.LocalXSwingStatistics;

import static org.junit.Assert.assertEquals;

public class OnlineSubmitterTest {

    @Before
    public void setUp() throws Exception {

    }

    // private final static String SCORE_SUBMIT_URL = "http://localhost/submit_score.php";
    private final static String SCORE_SUBMIT_URL_ONLINE = "http://xswing.net/submit_score_junit.php";
    private final static String SCORE_SUBMIT_URL_LOCAL = "http://localhost/XSwing/submit_score_junit.php";

    private final static String STATSTIC_SUBMIT_URL_LOCAL = "http://localhost/XSwing/submit_statistic_junit.php";
    private final static String STATSTIC_SUBMIT_URL_ONLINE = "http://xswing.net/submit_statistic_junit.php";

    @Test
    public final void testScoreOnlineSubmitterOnlineReal() {
        HighScoreTable scoreTable = getTable();
        ScoreOnlineSubmitter submitter = new ScoreOnlineSubmitter(scoreTable,
                "http://localhost/XSwing/submit_score.php");
        System.out.println(submitter);
        String response = submitter.submit();
        // assertEquals(scoreTable.toString(), response);

        System.out.println("Response: '" + response + "'");
    }

    @Test
    public final void testScoreOnlineSubmitterLocal() {
        submitScore(SCORE_SUBMIT_URL_LOCAL);
    }

    @Test
    public final void testScoreOnlineSubmitterOnline() {
        submitScore(SCORE_SUBMIT_URL_ONLINE);
    }

    @Test
    public final void testStatisticOnlineSubmitterLocal() {
        submitStatistic(STATSTIC_SUBMIT_URL_LOCAL);
    }

    @Test
    public final void testStatisticOnlineSubmitterOnline() {
        submitStatistic(STATSTIC_SUBMIT_URL_ONLINE);
    }

    public final void submitScore(String scoreSubmitURL) {
        HighScoreTable scoreTable = getTable();
        ScoreOnlineSubmitter submitter = new ScoreOnlineSubmitter(scoreTable, scoreSubmitURL);
        String response = submitter.submit();
        assertEquals(scoreTable.toString(), response);
    }

    private HighScoreTable getTable() {
        HighScoreTable scoreTable = new HighScoreTable();
        scoreTable.addScore(new HighScoreLine(20, "Tim", 800, 50, 42));
        scoreTable.addScore(new HighScoreLine(12, "Anna", 9000, 50, 42));
        scoreTable.addScore(new HighScoreLine(99, "Karlo", 400, 120, 99));
        scoreTable.addScore(new HighScoreLine(5, "Peter", 10, 130, 110));
        return scoreTable;
    }

    public final void submitStatistic(String scoreSubmitURL) {
        LocalXSwingStatistics statistics = new LocalXSwingStatistics();
        String statisticsInOneLine = statistics.getScoreInOneHTTPLine();
        // System.out.println("statistic: " + statisticsInOneLine);
        String cryptedStatistic = new EasyCrypter()
                .enCrypt(statisticsInOneLine);
        // System.out.println("cryptedStatistic: " + cryptedStatistic);
        String response = EasyPostString.send(scoreSubmitURL, Args.statistic.toString(),
                cryptedStatistic);
        assertEquals(statisticsInOneLine, response);
        // System.out.println(response);
    }

}
