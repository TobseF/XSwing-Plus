package xswing.gui;

import java.text.NumberFormat;
import lib.mylib.highscore.*;
import lib.mylib.util.Clock;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.util.Log;
import xswing.GamePanel;
import xswing.start.XSwing;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.checkbox.CheckboxControl;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.*;

public class ScreenControllerScore implements ScreenController {

	private Nifty nifty;
	private Screen screen;
	private StateBasedGame game;
	private int highScore;
	private HighScoreTable highScoreList;
	private HighScoreLine newScore;
	private TextFieldControl textField;
	private Clock clock;

	private final static String SCORE_SERVER_PATH = "lib/highscore/";
	private final static String SCORE_LINE_SUBMIT_FILE = "submit_high_score_line.php";

	public ScreenControllerScore(StateBasedGame game, HighScoreTable highScoreList, Clock clock) {
		this.game = game;
		this.highScoreList = highScoreList;
		this.clock = clock;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
		//VOID: no Sound and no mouse down event
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
//		screen.findElementByName("name").addInputHandler(new KeyInputHandler() {
//			
//			@Override
//			public boolean keyEvent(NiftyInputEvent e) {
//				if (e == NiftyInputEvent.SubmitText) {
//					enterHighScore();
//					return true;
//				}
//				return false;
//			}
//		});
	}

	/**
	 * on start screen interactive.
	 */
	public final void onStartScreen() {
		Screen screen = nifty.getCurrentScreen();
		// nifty.toggleDebugConsole(true); //Debug text
		setHighScore(screen);
		textField = screen.findControl("name", TextFieldControl.class);
		textField.setMaxLength(10);
	}

	public final void onEndScreen() {
		 game.getContainer().getInput().clearControlPressedRecord();
		 game.getContainer().getInput().clearKeyPressedRecord();
		 game.getContainer().getInput().clearMousePressedRecord();
		 nifty.getMouseInputEventQueue().reset();
	}

	private final void setHighScore(Screen screen) {
		System.out.println("setHighScore " + highScore);
		screen.findElementByName("labelScore").getRenderer(TextRenderer.class).setText(
				NumberFormat.getInstance().format(highScore));
		Element nameField = screen.findElementByName("name");
		nameField.setFocus();

		textField = screen.findControl("name", TextFieldControl.class);
	}

	/**
	 * Action which is called after a name was entered.
	 */
	public final void enterHighScore() {
		int minScore = 200;
		String name = textField.getText();
		Log.info("Score entered: " + highScore + " " + name);
		newScore = new HighScoreLine(highScore, name, (int) clock.getTimeSinceStart());
		if (!name.isEmpty()) {
			highScoreList.addScore(newScore);
			highScoreList.save();
		}
		boolean submitHighscoreOnline = screen.findControl("upload-score", CheckboxControl.class).isChecked();
		if (submitHighscoreOnline && highScore > minScore && name.length() > 2) {
			Log.info("Submit score online");
			new ScoreSubmitThread(newScore, XSwing.XSWING_HOST_URL + SCORE_SERVER_PATH + SCORE_LINE_SUBMIT_FILE);
		}

		((GamePanel) game.getState(XSwing.GAME_PANEL)).reset();
		nifty.getMouseInputEventQueue().reset();
		game.enterState(XSwing.GAME_PANEL, new EmptyTransition(), new EmptyTransition());
	}

	private class ScoreSubmitThread extends Thread {
		
		private final HighScoreLine score;
		private final String url;
		
		public ScoreSubmitThread(HighScoreLine score, String url) {
			this.score = score;
			this.url = url;
			start();
		}

		@Override
		public void run() {
			new ScoreOnlineSubmitter(score, url).submit();
		}
	}

	public void toggledCheckbox() {
		Log.info("toggled Checkbox");
	}

}