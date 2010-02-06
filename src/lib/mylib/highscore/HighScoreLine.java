/*
 * @version 0.0 20.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

import java.text.*;
import java.util.Date;

/**
 * @author Tobse On line in a HighScore Table with: #score, #name and #time
 */
public class HighScoreLine implements Comparable<HighScoreLine> {

	/** Score of this highscore */
	private int score = 0;
	/** Name of the player with this highscore */
	private String name = "";
	/** Time of the current game with this highscore in ms */
	private long time = 0;
	/** Time of the highscore submit in ms */
	private Date date;

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * Used to seperate the values in #toString(). It's a disallowed character in the #name.
	 * have to be different from HighScoreTable#VALUE_SEPERATOR
	 */
	private static char VALUE_SEPERATOR = ';';

	/**
	 * @param score point of this high score
	 * @param name of the player
	 * @param gameTime time of the current game in ms
	 * @param date time of the highscore submit in ms
	 * @see #HighScoreLine(int, String, long)
	 */
	public HighScoreLine(int score, String name, long gameTime, long date) {
		setName(name);
		setScore(score);
		setTime(gameTime);
		this.date = new Date(date);
	}

	/**
	 * @param score point of this high score
	 * @param name of the player
	 * @param gameTime time of the current game in ms
	 */
	public HighScoreLine(int score, String name, long gameTime) {
		this(score, name, gameTime, System.currentTimeMillis());
	}

	public HighScoreLine(int score, String name) {
		this(score, name, 0);
	}

	public HighScoreLine(String scoreInOneLine) {
		if (scoreInOneLine == null || scoreInOneLine.split(VALUE_SEPERATOR + "").length != 4) {
			throw new IllegalArgumentException("Scoreline '" + scoreInOneLine
					+ "' is not formatted correctly");
		}
		try {
			String[] values = scoreInOneLine.split(VALUE_SEPERATOR + "");
			name = values[0];
			score = Integer.valueOf(values[1]);
			time = Long.parseLong(values[2]);
			date = DATE_FORMAT.parse(values[3]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Scoreline is not formatted correctly");
		} catch (ParseException e) {
			throw new IllegalArgumentException("Scoreline is not formatted correctly");
		}
	}

	@Override
	public int compareTo(HighScoreLine line) {
		return line.getScore() - score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if (score < 0) {
			throw new IllegalArgumentException("Score can not be < 0");
		}
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name.replace(VALUE_SEPERATOR, '_');
		name.replace(HighScoreTable.VALUE_SEPERATOR, '_');
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return name + VALUE_SEPERATOR + score + VALUE_SEPERATOR + time + VALUE_SEPERATOR
				+ DATE_FORMAT.format(date);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof HighScoreLine) {
			return (score == ((HighScoreLine) obj).score
					&& name.equals(((HighScoreLine) obj).name) && time == ((HighScoreLine) obj).time);
		}
		return super.equals(obj);
	}

}
