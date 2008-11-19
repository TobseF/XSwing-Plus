package xswing.gui;

import java.text.NumberFormat;

import lib.mylib.ScoreStoreable;

import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import xswing.MainGame;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.TextFieldControl;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class ScoreScreenController implements ScreenController {

	private Nifty nifty;
	private StateBasedGame game;
	private int highScore;
	private ScoreStoreable highscoreList;
	private TextFieldControl textField;

	public ScoreScreenController(StateBasedGame game, int highscore,
			ScoreStoreable highscoreList) {
		this.game = game;
		this.highScore = highscore;
		this.highscoreList = highscoreList;
	}

	/**
	 * Bind this ScreenController to a screen.
	 * 
	 * @param newNifty nifty
	 * @param newScreen screen
	 */
	public final void bind(final Nifty newNifty, final Screen newScreen) {
		nifty = newNifty;
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
		screen.findElementByName("labelScore").getRenderer(TextRenderer.class)
				.setText("Your Score: " + NumberFormat.getInstance().format(highScore));
		screen.findElementByName("name").setFocus();
	}

	
	/**
	 * Action which is called after a name was entered.
	 */
	public final void enterHighScore() {
		String name = textField.getText();
		System.out.println("Score entered: " + name + " " + highScore);
		highscoreList.addScore(highScore, name);
		((MainGame) game.getState(2)).reset();
		game.enterState(2, new FadeOutTransition(), new FadeInTransition());
	}

}