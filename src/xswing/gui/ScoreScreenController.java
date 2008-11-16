package xswing.gui;

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

/**
 * ScreenController for Hello World Example.
 * @author void
 */
public class ScoreScreenController implements ScreenController{

  /** nifty instance. */
  private Nifty nifty;
  private StateBasedGame game;
  private int highScore;
  ScoreStoreable highscoreList;

  public ScoreScreenController(StateBasedGame game,int highscore, ScoreStoreable highscoreList) {
  	this.game=game;
  	this.highScore=highscore;
  	this.highscoreList=highscoreList;
  }

/**
   * Bind this ScreenController to a screen.
   * @param newNifty nifty
   * @param newScreen screen
   */
  public final void bind(final Nifty newNifty, final Screen newScreen) {
    this.nifty = newNifty;

  }
int s=0;
  /**
   * on start screen interactive.
   */
  public final void onStartScreen() {
	  Screen screen=nifty.getCurrentScreen();
	  //nifty.toggleDebugConsole(true); //Debug text 
	  setHighScore(screen);	  
	  screen.findElementByName("name").addInputHandler(new KeyInputHandler(){
		@Override
		public boolean keyEvent(NiftyInputEvent e) {
			  if (e == NiftyInputEvent.SubmitText) {
				  enterHighScore();
				  return true;
				 }
				  return false;
				 }
		});
  }

  /**
   * on end screen.
   */
  public final void onEndScreen() {
  }

  
  private final void setHighScore(Screen screen) {
	 System.out.println("entered HighScoreFormatter");
	  
	 System.out.println("highscorestart");
	 screen.findElementByName("labelScore").getRenderer(TextRenderer.class).setText("Your Score: "+highScore);
	 screen.findElementByName("name").setFocus();
  }
  
  public final void enterHighScore() {
	  Screen screen=nifty.getCurrentScreen();
      String t=((screen.findElementByName("name")).getControl(TextFieldControl.class)).getText();
      System.out.println("Score entered: "+t+" "+highScore);
      highscoreList.addScore(highScore, t);
      ((MainGame)game.getState(2)).reset();
      game.enterState(2, new FadeOutTransition(), new FadeInTransition());
  }



}
