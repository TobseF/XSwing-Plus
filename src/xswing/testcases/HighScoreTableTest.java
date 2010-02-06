/*
 * @version 0.0 20.09.2009
 * @author Tobse F
 */
package xswing.testcases;

import static org.junit.Assert.assertEquals;
import lib.mylib.highscore.*;
import lib.mylib.http.EasyPostString;
import lib.mylib.options.DefaultArgs.Args;
import org.junit.*;

public class HighScoreTableTest {

	private HighScoreTable scoreTable;

	@Before
	public void setUp() throws Exception {
		scoreTable = new HighScoreTable();
	}

	@Test
	public final void testAddScore() {
		scoreTable.addScore(new HighScoreLine(20, "Tim"));
		scoreTable.addScore(new HighScoreLine(12, "Anna"));
		scoreTable.addScore(new HighScoreLine(99, "Karlo"));
		scoreTable.addScore(new HighScoreLine(0, "XXX"));
		scoreTable.addScore(new HighScoreLine(1, "Susi"));
		assertEquals(scoreTable.size(), 5);
		scoreTable.setMaximumElements(4);

		scoreTable.addScore(new HighScoreLine(0, "Piefge"));
		assertEquals(scoreTable.size(), 4);
	}

	@Test
	public final void testRemoveHighScoreLinesWithoutName() {
		scoreTable.clear();
		scoreTable.addScore(new HighScoreLine(20, "Tim"));
		scoreTable.addScore(new HighScoreLine(999, ""));
		scoreTable.removeHighScoreLinesWithoutName();
		assertEquals(1, scoreTable.size());
	}

	@Test
	public final void testHighScoreTable() {
		scoreTable.clear();
		scoreTable.addScore(new HighScoreLine(20, "Tim"));
		scoreTable.addScore(new HighScoreLine(12, "Anna"));
		scoreTable.addScore(new HighScoreLine(99, "Karlo"));
		String scoreTableInOneLine = scoreTable.toString();
		HighScoreTable newScoreTable = new HighScoreTable(scoreTableInOneLine);
		assertEquals(scoreTable, newScoreTable);
	}

	private final static String SCORE_SUBMIT_URL = "http://localhost/submit_for_junit.php";

	@Test
	public final void testSubmitHighScore() {
		scoreTable.clear();
		scoreTable.addScore(new HighScoreLine(20, "Tim", 800));
		scoreTable.addScore(new HighScoreLine(12, "Anna", 9000));
		scoreTable.addScore(new HighScoreLine(99, "Karlo", 400));
		scoreTable.addScore(new HighScoreLine(5, "Peter", 10));
		String scoreTableInOneLine = scoreTable.toString();
		EasyCrypter easyCrypter = new EasyCrypter();
		String cryptedScoreTable = easyCrypter.enCrypt(scoreTableInOneLine);
		System.out.println(cryptedScoreTable);
		String request = EasyPostString.send(SCORE_SUBMIT_URL, Args.highScore.toString(),
				cryptedScoreTable);
		assertEquals(scoreTableInOneLine, request);
	}

}
