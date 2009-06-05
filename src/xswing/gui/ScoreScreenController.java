package xswing.gui;

import java.text.NumberFormat;
import lib.mylib.highscore.ScoreStoreable;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import xswing.GamePanel;
import xswing.start.XSwing;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.*;

public class ScoreScreenController implements ScreenController {

	private Nifty nifty;
	private StateBasedGame game;
	private int highScore;
	private ScoreStoreable highScoreList;
	private TextFieldControl textField;

	public ScoreScreenController(StateBasedGame game,
			ScoreStoreable highscoreList) {
		this.game = game;
		this.highScoreList = highscoreList;
	}

	public void setHighScore(int highScore){
		this.highScore = highScore;		
	}

	/**
	 * Bind this ScreenController to a screen.
	 * 
	 * @param newNifty nifty
	 * @param newScreen screen
	 */
	public final void bind(final Nifty newNifty, final Screen newScreen) {
		nifty = newNifty;
		// nifty.render(false);
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
		System.out.println("setHighScore " +highScore);
		screen.findElementByName("labelScore").getRenderer(TextRenderer.class).setText(NumberFormat.getInstance().format(highScore));
		Element nameField = screen.findElementByName("name");
		nameField.setFocus();
		
		textField = screen.findControl("name", TextFieldControl.class);
	}

	/**
	 * Action which is called after a name was entered.
	 */
	public final void enterHighScore() {
		String name = textField.getText();
		System.out.println("Score entered: " + " " + highScore+ " "+ name);
		//VOID: textField.getText() can return the old value
		if(!name.isEmpty()){
			highScoreList.addScore(highScore, name);
		}
		
		((GamePanel) game.getState(XSwing.GAME_PANEL)).reset();

		game.enterState(XSwing.GAME_PANEL, new EmptyTransition(), new EmptyTransition());
		
	}

}