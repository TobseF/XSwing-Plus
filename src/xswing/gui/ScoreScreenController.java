package xswing.gui;

import java.text.NumberFormat;

import lib.mylib.highscore.ScoreStoreable;

import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;

import xswing.GamePanel;
import xswing.start.XSwing;
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
		//nifty.render(true);
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
		((GamePanel) game.getState(XSwing.GAME_PANEL)).reset();
		//game.enterState(XSwingMenu.GAME_PANEL, new FadeOutTransition(), new FadeInTransition());
		game.enterState(XSwing.GAME_PANEL, new EmptyTransition(), new EmptyTransition());
	}

}