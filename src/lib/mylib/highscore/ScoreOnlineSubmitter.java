/*
 * @version 0.0 25.09.2009
 * @author Tobse F
 */
package lib.mylib.highscore;

import lib.mylib.http.EasyPostString;
import lib.mylib.ident.*;
import lib.mylib.options.DefaultArgs.Args;
import org.newdawn.slick.util.Log;

/**
 * Sends the {@link HighScoreTable} to a PHP webserver per POST in the {@link Args#highScore}
 * <code>($_POST ["highScore"])</code>. The ScoreTable will be crypted and tagged with the pc
 * version ({@link Identable}).
 * 
 * @author Tobse
 */
public class ScoreOnlineSubmitter {

	/** Seperates the HighScoreTable String and the pc identity */
	public static final String gameIdSeperator = "+";

	private HighScoreTable highScoreTable;
	private String scoreSubmitURL;

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
	 * Sends the {@link HighScoreTable} to a PHP webserver per POST in the
	 * {@link Args#highScore} <code>($_POST ["highScore"])</code>. The ScoreTable will be
	 * crypted and tagged with the pc version ({@link Identable}).
	 */
	public String submit() {
		String response = EasyPostString.send(scoreSubmitURL, Args.highScore.toString(),
				toString());
		Log.debug("Submit highscore online response: " + response);
		return response;
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
