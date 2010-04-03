/*
 * @version 0.0 21.03.2010
 * @author Tobse F
 */
package xswing.testcases;

import lib.mylib.highscore.*;
import org.junit.*;

public class ScoreOnlineSubmitterTest {

	private final static String SCORE_SERVER_ONLINE = "http://xswing.net/";

	private final static String SCORE_SERVER_LOCAL = "http://localhost/XSwing/";
	private final static String SCORE_SERVER_PATH = "lib/highscore/";
	private final static String SCORE_LINE_SUBMIT_FILE = "submit_high_score_line.php";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public final void testScoreOnlineSubmitterLocal() {
		submitScoreLineTest(SCORE_SERVER_LOCAL);
	}

	@Test
	public final void testScoreOnlineSubmitterOnline() {
		submitScoreLineTest(SCORE_SERVER_ONLINE);
	}

	private void submitScoreLineTest(String server) {
		long gemeTime = 1000 * 60 * 5; // 5min
		long date = 1269187815854l; // 21.03.2010
		HighScoreLine scoreLine = new HighScoreLine(5000, "Tom", gemeTime, date);
		String url = server + SCORE_SERVER_PATH + SCORE_LINE_SUBMIT_FILE;
		System.out.println(url);
		ScoreOnlineSubmitter submitter = new ScoreOnlineSubmitter(scoreLine, url);
		String response = submitter.submit();
	}

	@Test
	public final void testToString() {

	}

}
