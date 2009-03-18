/*
 * @version 0.0 01.09.2008
 * @author Tobse F
 */
package xswing;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import lib.mylib.MyTimer;
import lib.mylib.color.TransparancySlider;
import lib.mylib.highscore.HighScoreFormatter;
import lib.mylib.highscore.ScoreStoreable;
import lib.mylib.object.Resetable;
import lib.mylib.object.SObject;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

/**
 * Loads, saves and draws the HighScore table
 * 
 * @author Tobse
 */
public class HighScore extends SObject implements ScoreStoreable, Resetable {
	private SavedState localFile;
	private HighScoreFormatter scoreFormatter;
	private String[][] highScoreTable;
	private MyTimer timer;
	/** Index of the current displyed score value */
	private int index = 0;
	private AngelCodeFont font;
	private TransparancySlider fadeIn, fadeOut;

	public HighScore(AngelCodeFont font, String fileName) {
		this.font = font;
		try {
			localFile = new SavedState(fileName);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		scoreFormatter = new HighScoreFormatter();
		fadeIn = new TransparancySlider(20, 1000, TransparancySlider.LINEAR_FADE_IN);
		fadeOut = new TransparancySlider(20, 1000, TransparancySlider.LINEAR_FADE_OUT);
		readScore();
		timer = new MyTimer(5000, true) {
			@Override
			protected void timerAction() {
				switchIndex();
			}
		};
	}

	private void saveScore() {
		String[][] cryptedHighScoreTable = scoreFormatter.cryptScore(highScoreTable);
		scoreFormatter.printTable(highScoreTable);
		scoreFormatter.printTable(cryptedHighScoreTable);
		localFile.setString("score", scoreFormatter
				.shrincScoreInOneLine(cryptedHighScoreTable));
		try {
			localFile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readScore() {
		String scoreInOneLine = localFile.getString("score");
		if (scoreInOneLine != null && !scoreInOneLine.isEmpty()) {
			highScoreTable = scoreFormatter.decryptScore(scoreFormatter
					.deShrincHighScoreFromOneLine(localFile.getString("score")));
		} else {
			highScoreTable = new String[0][0];
		}
	}

	@Override
	public void render(Graphics g) {
		if (isVisible) {
			drawScoreTableFading();
		}
	}

	private void drawScoreTableFading() {
		Color fadeInColor = fadeIn.getTranparency();
		// Color fadeOutColor = fadeOut.getTranparency();

		int gap = font.getLineHeight() - 2;
		if (highScoreTable != null && highScoreTable.length >= 1) {
			drawScoreTable(index, 0, fadeInColor);
			if (highScoreTable.length >= 2) {
				drawScoreTable(index + 1, gap, fadeInColor);
				/*
				 * if (highScoreTable.length >= 4) { drawScoreTable( getNextIndex() + 1, 0,
				 * fadeOutColor); drawScoreTable( getNextIndex() + 2, gab_between_balls, fadeOutColor); }
				 */
			}
		}
	}

	private void drawScoreTable(int index, int gapBetweenLines, Color transparency) {
		int maxScoreLength = font.getWidth("000000");
		
		// Position
		font.drawString(x - 150, y + gapBetweenLines, (index + 1) + "", transparency);
		// Name
		font.drawString(x - 5 - font.getWidth(highScoreTable[index][1]) - maxScoreLength, y
				+ gapBetweenLines, highScoreTable[index][1], transparency);
		// Score
		int score = Integer.parseInt(highScoreTable[index][0]);
		String formattesScore = NumberFormat.getInstance().format(score);

		font.drawString(x - font.getWidth(formattesScore), y + gapBetweenLines,
				formattesScore, transparency);
	}

	
	/**
	 * Switch to the next element in the highScoreList
	 */
	private void switchIndex() {
		index = getNextIndex();
		fadeIn.reset();
		fadeOut.reset();
		// fadeOut.invert();
		// fadeIn.invert();
	}

	
	/**
	 * @return nextIndexInHighScoreList
	 */ 
	private int getNextIndex() {
		int indexNew = (index + 1) < (highScoreTable.length - 1) ? index + 1 : 0;
		return indexNew;
	}

	@Override
	public void update(int delta) {
		fadeIn.update(delta);
		fadeOut.update(delta);
		timer.update(delta);
	}

	/** Ads a new Score value to the highscore table*/
	@Override
	public void addScore(int score, String name) {
		highScoreTable = scoreFormatter.addScore(highScoreTable, name, score, 50);
		saveScore();
	}

	@Override
	public void reset() {
		index = 0;
		timer.reset();
		fadeIn.reset();
		fadeOut.reset();
	}
}