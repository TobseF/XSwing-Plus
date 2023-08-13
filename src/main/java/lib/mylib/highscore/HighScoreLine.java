/*
 * @version 0.0 20.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

import lib.mylib.util.Clock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Tobse On line in a HighScore Table with: #score, #name and #time
 */
public class HighScoreLine implements Comparable<HighScoreLine> {

    /**
     * Score of this highscore
     */
    private int score = 0;
    /**
     * Name of the player with this highscore
     */
    private String name = "";
    /**
     * Time of the current game with this highscore in ms
     */
    private long time = 0;
    /**
     * Time of the highscore submit in ms
     */
    private Date date;

    /**
     * Number of balls which were released
     */
    private int ballsReleased = 0;
    /**
     * Number of balls which were disbanded
     */
    private int ballsDisbanded = 0;
    /**
     * Number of values (score, name etc.) in this HighScoreLine
     */
    public static final int VALUE_COUNT = 6;
    /**
     * MySQL Date format which is used to format {@link #date}.
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * Used to separate the values in #toString(). It's a disallowed character in the #name.
     * have to be different from HighScoreTable#VALUE_SEPERATOR
     */
    public static char VALUE_SEPERATOR = ';';

    /**
     * @param score          {@link #score}
     * @param name           {@link #name}
     * @param gameTime       {@link #time}
     * @param releasedBalls  {@link #ballsReleased}
     * @param disbandedBalls {@link #ballsDisbanded}
     * @param date           {@link #date}
     * @see #HighScoreLine(int, String, long)
     */
    public HighScoreLine(int score, String name, long gameTime, int releasedBalls, int disbandedBalls, Date date) {
        setName(name);
        setScore(score);
        setTime(gameTime);
        setReleasedBalls(releasedBalls);
        setDispandedBalls(disbandedBalls);
        setDate(date);
    }

    /**
     * @param score          {@link #score}
     * @param name           {@link #name}
     * @param gameTime       {@link #time}
     * @param releasedBalls  {@link #ballsReleased}
     * @param disbandedBalls {@link #ballsDisbanded}
     */
    public HighScoreLine(int score, String name, long gameTime, int releasedBalls, int disbandedBalls) {
        this(score, name, gameTime, releasedBalls, disbandedBalls, new Date());
    }

    /**
     * @param score    {@link #score}
     * @param name     {@link #name}
     * @param gameTime {@link #time}
     */
    public HighScoreLine(int score, String name, long gameTime) {
        this(score, name, gameTime, 0, 0, new Date());
    }

    /**
     * @param score {@link #score}
     * @param name  {@link #name}
     */
    public HighScoreLine(int score, String name) {
        this(score, name, 0, 0, 0);
    }

    /**
     * Creates a {@link HighScoreLine} out of a with {@link #wrapHighscoreLineToOneString()}
     * created String
     *
     * @param scoreInOneLine a with {@link #wrapHighscoreLineToOneString()} created String
     * @see #wrapHighscoreLineToOneString()
     */
    public HighScoreLine(String scoreInOneLine) {
        if (scoreInOneLine == null || scoreInOneLine.split(VALUE_SEPERATOR + "").length != VALUE_COUNT) {
            throw new IllegalArgumentException("Scoreline '" + scoreInOneLine + "' is not formatted correctly");
        }
        try {
            String[] values = scoreInOneLine.split(VALUE_SEPERATOR + "");
            name = values[0];
            score = Integer.valueOf(values[1]);
            ballsReleased = Integer.valueOf(values[2]);
            ballsDisbanded = Integer.valueOf(values[3]);
            time = Long.parseLong(values[4]);
            date = DATE_FORMAT.parse(values[5]);
        } catch (Exception e) {
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

    public int getDispandedBalls() {
        return ballsDisbanded;
    }

    public void setDispandedBalls(int dispandedBalls) {
        this.ballsDisbanded = dispandedBalls;
    }

    public int getReleasedBalls() {
        return ballsReleased;
    }

    public void setReleasedBalls(int releasedBalls) {
        this.ballsReleased = releasedBalls;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Creates a String which contains all ScoreLineValues seperated with
     * {@link #VALUE_SEPERATOR}. Can be re wrapped with {@link #HighScoreLine(String)}<br>
     * Syntax is:<br>
     * name;score;releasedBalls;dispandedBalls;time;date<br>
     * e.g.:<br>
     * Max;1200;220;208;2010-3-18 13:15:5;6554645
     *
     * @return a String which contains all ScoreLineValues seperated with
     * {@value #VALUE_SEPERATOR}.
     * @see {@link #HighScoreLine(String)}
     */
    private String wrapHighscoreLineToOneString(boolean forOnlineSubmit) {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(VALUE_SEPERATOR);
        builder.append(score);
        builder.append(VALUE_SEPERATOR);
        builder.append(ballsReleased);
        builder.append(VALUE_SEPERATOR);
        builder.append(ballsDisbanded);
        builder.append(VALUE_SEPERATOR);
        if (forOnlineSubmit) {
            builder.append(Clock.getFormattedTimeAsString(time));
        } else {
            builder.append(time);
        }
        builder.append(VALUE_SEPERATOR);
        builder.append(DATE_FORMAT.format(date));
        return builder.toString();
    }

    /**
     * Same as {@link #wrapHighscoreLineToOneString(boolean)} but the time is formatted with
     * "hh:mm:ss" instead of an single integer.
     *
     * @return string which can be submitted only
     */
    public String toStringForOnlineSumit() {
        return wrapHighscoreLineToOneString(true);
    }

    @Override
    public String toString() {
        return wrapHighscoreLineToOneString(false);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof HighScoreLine) {
            HighScoreLine scoreLine = (HighScoreLine) obj;
            return (score == scoreLine.score && name.equals(scoreLine.name) && ballsReleased == scoreLine.ballsReleased
                    && ballsDisbanded == scoreLine.ballsDisbanded && time == scoreLine.time);
        }
        return super.equals(obj);
    }

}
