/*
 * @version 0.0 21.03.2010
 * @author Tobse F
 */
package xswing.testcases;

import lib.mylib.highscore.HighScoreLine;
import lib.mylib.highscore.ScoreOnlineSubmitter;
import org.junit.*;

import java.util.Date;
import java.util.GregorianCalendar;

public class ScoreOnlineSubmitterTest {

    private final static String SCORE_SERVER_ONLINE = "http://xswing.net/";

    private final static String SCORE_SERVER_LOCAL = "http://localhost/XSwing/";
    private final static String SCORE_SERVER_PATH = "";
    private final static String SCORE_LINE_SUBMIT_FILE = "submit_high_score_line_junit.php";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testScoreOnlineSubmitterLocal() {
        submitScoreLineTest(SCORE_SERVER_LOCAL);
    }

    @Ignore
    @Test
    public final void testScoreOnlineSubmitterOnline() {
        submitScoreLineTest(SCORE_SERVER_ONLINE);
    }

    private void submitScoreLineTest(String server) {
        long gemeTime = 1000 * 60 * 5; // 5min
        Date date = new GregorianCalendar(2010, 4, 21).getTime();
        HighScoreLine scoreLine = new HighScoreLine(5000, "Tom", gemeTime, 240, 221, date);
        String scoreLineToSubmit = ScoreOnlineSubmitter.createHighScoreLineSubmitString(scoreLine);
        String url = server + SCORE_SERVER_PATH + SCORE_LINE_SUBMIT_FILE;
        ScoreOnlineSubmitter submitter = new ScoreOnlineSubmitter(scoreLine, url);
        String response = submitter.submit();
        System.out.println("Server response: " + response);
        Assert.assertEquals(scoreLineToSubmit, response);
    }

    @Test
    public final void testToString() {

    }

}
