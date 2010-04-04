/*
 * @version 0.0 20.09.2009
 * @author Tobse F
 */
package xswing.testcases;

import static org.junit.Assert.assertEquals;
import lib.mylib.highscore.*;
import lib.mylib.util.Clock;
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
	public final void testConvertTime() {
		long timeStamp = 60*60*2+60*20+20;
		String timeFormatted = Clock.getFormattedTimeAsString(timeStamp);
		assertEquals("02:20:20", timeFormatted);
		assertEquals(timeStamp, Clock.getFormattedTimeAsInt(timeFormatted));
		
		timeStamp = 60*60*3+60*33+33;
		timeFormatted = Clock.getFormattedTimeAsString(timeStamp);
		assertEquals("03:33:33", timeFormatted);
		assertEquals(timeStamp, Clock.getFormattedTimeAsInt(timeFormatted));
	}
	
	@Test
	public final void testHighScoreTable() {
		scoreTable.clear();
		scoreTable.addScore(new HighScoreLine(20, "Tim"));
		scoreTable.addScore(new HighScoreLine(12, "Anna"));
		HighScoreLine scoreKarlo = new HighScoreLine(99, "Karlo"); 
		scoreTable.addScore(scoreKarlo);		
		System.out.println(scoreKarlo);
		HighScoreLine readKarloFromString = new HighScoreLine(scoreKarlo.toString());
		assertEquals(scoreKarlo, readKarloFromString);
		String scoreTableInOneLine = scoreTable.toString();

		HighScoreTable newScoreTable = new HighScoreTable(scoreTableInOneLine);
		assertEquals(scoreTable, newScoreTable);
	}


}
