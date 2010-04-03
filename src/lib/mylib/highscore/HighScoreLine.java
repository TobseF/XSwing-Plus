/*
 * @version 0.0 20.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

import java.text.*;
import java.util.Date;
import lib.mylib.util.Clock;

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
	public static char VALUE_SEPERATOR = ';';

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

	/**
	 * Creates a {@link HighScoreLine} out of a with {@link #wrapHighscoreLineToOneString()}
	 * created String
	 * 
	 * @param scoreInOneLine a with {@link #wrapHighscoreLineToOneString()} created String
	 * @see #wrapHighscoreLineToOneString()
	 */
	public HighScoreLine(String scoreInOneLine) {
		if (scoreInOneLine == null || scoreInOneLine.split(VALUE_SEPERATOR + "").length != 4) {
			throw new IllegalArgumentException("Scoreline '" + scoreInOneLine + "' is not formatted correctly");
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
		name.replace(VALUE_SEPERATOR + "", "");
		// name.replace(HighScoreTable.VALUE_SEPERATOR + "", "");
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Creates a String which contains all ScoreLineValues seperated with
	 * {@link #VALUE_SEPERATOR}. Can be re wrapped with {@link #HighScoreLine(String)}<br>
	 * Syntax is:<br>
	 * name;score;time;date<br>
	 * eg.:<br>
	 * Max;1200;2010-3-18 13:15:5;6554645
	 * 
	 * @return a String which contains all ScoreLineValues seperated with
	 *         {@value #VALUE_SEPERATOR}.
	 * @see {@link #HighScoreLine(String)}
	 */
	private String wrapHighscoreLineToOneString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append(VALUE_SEPERATOR);
		builder.append(score);
		builder.append(VALUE_SEPERATOR);
		builder.append(Clock.getFormattedTimeAsString(time));
		builder.append(VALUE_SEPERATOR);
		builder.append(DATE_FORMAT.format(date));
		return builder.toString();
	}

	@Override
	public String toString() {
		return wrapHighscoreLineToOneString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof HighScoreLine) {
			return (score == ((HighScoreLine) obj).score && name.equals(((HighScoreLine) obj).name) && time == ((HighScoreLine) obj).time);
		}
		return super.equals(obj);
	}

}
