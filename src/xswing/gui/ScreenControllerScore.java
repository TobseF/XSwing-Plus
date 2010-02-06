package xswing.gui;

import java.text.NumberFormat;
import lib.mylib.highscore.*;
import lib.mylib.http.EasyPostString;
import lib.mylib.ident.Identable;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.util.Clock;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.util.Log;
import xswing.*;
import xswing.start.XSwing;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.checkbox.CheckboxControl;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.*;

public class ScreenControllerScore implements ScreenController {

	private Nifty nifty;
	private Screen screen;
	private StateBasedGame game;
	private int highScore;
	private HighScoreTable highScoreList;
	private GameStatistics statistics;
	private TextFieldControl textField;
	private final static String SCORE_SUBMIT_URL = "http://xswing.net/submit_score.php";
	private final static String STATISTIC_SUBMIT_URL = "http://xswing.net/submit_statistic.php";
	private Clock clock;

	public ScreenControllerScore(StateBasedGame game, HighScoreTable highScoreList,
			GameStatistics statistics, Clock clock) {
		this.game = game;
		this.highScoreList = highScoreList;
		this.statistics = statistics;
		this.clock = clock;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	/**
	 * Bind this ScreenController to a screen.
	 * 
	 * @param newNifty nifty
	 * @param newScreen screen
	 */
	public final void bind(final Nifty newNifty, final Screen newScreen) {
		this.nifty = newNifty;
		this.screen = newScreen;
	}

	/**
	 * on start screen interactive.
	 */
	public final void onStartScreen() {
		Screen screen = nifty.getCurrentScreen();
		// nifty.toggleDebugConsole(true); //Debug text
		setHighScore(screen);
		screen.findElementByName("name").addInputHandler(new KeyInputHandler() {

			@Override
			public boolean keyEvent(NiftyInputEvent e) {
				if (e == NiftyInputEvent.SubmitText) {
					enterHighScore();
					return true;
				}
				return false;
			}
		});
		textField = screen.findControl("name", TextFieldControl.class);
		textField.setMaxLength(10);
	}

	public final void onEndScreen() {}

	private final void setHighScore(Screen screen) {
		System.out.println("setHighScore " + highScore);
		screen.findElementByName("labelScore").getRenderer(TextRenderer.class).setText(
				NumberFormat.getInstance().format(highScore));
		Element nameField = screen.findElementByName("name");
		nameField.setFocus();

		textField = screen.findControl("name", TextFieldControl.class);
		game.getContainer().setMouseGrabbed(false);
	}

	/**
	 * Action which is called after a name was entered.
	 */
	public final void enterHighScore() {
		String name = textField.getText();
		Log.info("Score entered: " + " " + highScore + " " + name);
		if (!name.isEmpty()) {
			highScoreList.addScore(new HighScoreLine(highScore, name, (int) clock
					.getTimeSinceStart()));
			highScoreList.save();
		}
		boolean submitHighscoreOnline = screen.findControl("upload-score",
				CheckboxControl.class).isChecked();
		if (submitHighscoreOnline) {
			new Thread() {

				@Override
				public void run() {
					new ScoreOnlineSubmitter(highScoreList, SCORE_SUBMIT_URL).submit();
					// submitStatistic();
				};
			}.start();
		}

		((GamePanel) game.getState(XSwing.GAME_PANEL)).reset();
		game.enterState(XSwing.GAME_PANEL, new EmptyTransition(), new EmptyTransition());
	}

	/**
	 * Sends the {@link GameStatistics} to a PHP webserver per POST in the
	 * {@link Args#statistic} <code>($_POST ["statistic"])</code>. The Statistics will be
	 * crypted and tagged with the pc version ({@link Identable}).
	 */
	private String submitStatistic() {
		String cryptedStatistic = new EasyCrypter()
				.enCrypt(statistics.getScoreInOneHTTPLine());
		String response = EasyPostString.send(STATISTIC_SUBMIT_URL, Args.statistic.toString(),
				cryptedStatistic);
		Log.info("Submit statistic online, response: " + response);
		return response;
	}

	public void toggledCheckbox() {
		Log.info("toggled Checkbox");
	}

}