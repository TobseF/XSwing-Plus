/*
 * @version 0.0 25.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

import lib.mylib.http.EasyPostString;
import lib.mylib.ident.Identable;
import lib.mylib.ident.TimeIdent;
import lib.mylib.options.DefaultArgs.Args;
import xswing.start.XSwing;

/**
 * Sends the {@link HighScoreTable} to a PHP webserver per POST in the {@link Args#highScore}
 * <code>($_POST ["highScore"])</code>. The ScoreTable will be crypted and tagged with the pc
 * version ({@link Identable}).
 *
 * @author Tobse
 */
public class ScoreOnlineSubmitter {

    /**
     * Seperates the HighScoreTable String and the pc identity
     */
    public static final String gameIdSeperator = "+";

    private HighScoreTable highScoreTable = null;
    private HighScoreLine highScoreLine = null;
    private final String scoreSubmitURL;

    private enum GameType {
        SinglePlayer, Multiplayer
    }

    /**
     * Can be used in later versions
     */
    private static final GameType gameType = GameType.SinglePlayer;

    /**
     * * Sends the {@link HighScoreTable} to a PHP webserver per POST in the
     * {@link Args#highScore} <code>($_POST ["highScore"])</code>. The ScoreTable will be
     * crypted and tagged with the pc version ({@link Identable}).
     *
     * @param highScoreTable wich schould be stored online
     * @param scoreSubmitURL url of the php submit page
     * @see #submit()
     */
    public ScoreOnlineSubmitter(HighScoreTable highScoreTable, String scoreSubmitURL) {
        this.highScoreTable = highScoreTable;
        this.scoreSubmitURL = scoreSubmitURL;
    }

    /**
     * * Sends the {@link #highScoreLine} to a PHP webserver per POST in the
     * {@link Args#highScore} <code>($_POST ["highScore"])</code>. The ScoreTable will be
     * crypted and tagged with the pc version ({@link Identable}).
     *
     * @param highScoreTable wich schould be stored online
     * @param scoreSubmitURL url of the php submit page
     * @see #submit()
     */
    public ScoreOnlineSubmitter(HighScoreLine highScoreLine, String scoreSubmitURL) {
        this.highScoreLine = highScoreLine;
        this.scoreSubmitURL = scoreSubmitURL;
    }

    /**
     * Sends the {@link HighScoreTable} to a PHP webserver per POST in the
     * {@link Args#highScore} <code>($_POST ["highScore"])</code>. The ScoreTable will be
     * crypted and tagged with the pc version ({@link Identable}).
     *
     * @see #createHighScoreLineSubmitString(HighScoreLine)
     */
    public String submit() {

        String submitData = highScoreTable == null ? createCrypedHighScoreLineSubmitString(highScoreLine)
                : createHighScoreSubmitString(highScoreTable);

        String response = EasyPostString.send(scoreSubmitURL, Args.highScore.toString(), submitData);
        //Log.debug("Submit highscore online response: " + response);
        return response;
    }

    /**
     * Creates one cryped HighScoreLine whic can be submitted online.<br>
     * [highScoreLine];Idendity;GameVersion;GameType<br>
     * name;score;time;date;Idendity;GameVersion;GameType<br>
     * e.g. Max;1200;2010-3-18 13:15:5;6554645;78822222;v0.531,0
     *
     * @param scoreLine which should be submitted
     * @return cryped HighScoreLine whic can be submitted online
     */
    public static String createHighScoreLineSubmitString(HighScoreLine scoreLine) {

        String stringBuilder = scoreLine.toStringForOnlineSumit() + HighScoreLine.VALUE_SEPERATOR +
                new TimeIdent().getIdentity() +
                HighScoreLine.VALUE_SEPERATOR +
                XSwing.VERSION.replace("v", "").substring(0, 5) +
                HighScoreLine.VALUE_SEPERATOR +
                gameType.ordinal();
        return stringBuilder;
    }

    public static String createCrypedHighScoreLineSubmitString(HighScoreLine scoreLine) {
        return new EasyCrypter().enCrypt(createHighScoreLineSubmitString(scoreLine));
    }

    public String createHighScoreSubmitString(HighScoreTable highScoreTable) {

        return null;
    }

    @Override
    public String toString() {
        String scoreTable = highScoreTable.toString();
        scoreTable = removeVorbiddenSepearator(scoreTable);
        scoreTable += gameIdSeperator + new TimeIdent().getIdentity();
        scoreTable = new EasyCrypter().enCrypt(scoreTable);
        return scoreTable;
    }

    private String removeVorbiddenSepearator(String readyForCleaning) {
        return readyForCleaning.replace(gameIdSeperator, "");
    }
}
