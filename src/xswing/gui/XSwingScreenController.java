package xswing.gui;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.TextFieldControl;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyInputMapping;
import de.lessvoid.nifty.input.keyboard.KeyboardInputEvent;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * ScreenController for Hello World Example.
 * @author void
 */
public class XSwingScreenController implements ScreenController {

  /** nifty instance. */
  private Nifty nifty;
  
  Screen newScreen;

  private StateBasedGame game;

  public XSwingScreenController(StateBasedGame game) {
  	this.game=game;
  }
  

/**
   * Bind this ScreenController to a screen.
   * @param newNifty nifty
   * @param newScreen screen
   */
  public final void bind(final Nifty newNifty, final Screen newScreen) {
    this.nifty = newNifty; 
    this.newScreen=newScreen;
  }
  
  /**
   * on start screen interactive.
   */
  public final void onStartScreen() {
	nifty.addControls();
	
	  newScreen.findElementByName("start").setFocus();
	  newScreen.addKeyboardInputHandler(new NiftyInputMapping(){ 
		@Override
		public NiftyInputEvent convert(KeyboardInputEvent e) {
			 if (e.getKey() == Keyboard.KEY_ESCAPE) 
			return NiftyInputEvent.Escape;
			 else
				 return null;
		}
	  }, 
	  new KeyInputHandler(){
			@Override
			public boolean keyEvent(NiftyInputEvent e) {
				if(e!=null&&e==NiftyInputEvent.Escape){
					System.out.println("ESC pressed");
					exitXSwing();
				}
				return false;
			}
	  }
	  );
	  game.getContainer().getInput().resume();
	 }

  /**
   * on end screen.
   */
  public final void onEndScreen() {
	  newScreen.findElementByName("Start").setFocus();
  }


  public final void startXSwing() {
	  System.out.println("start XSing()");
	  game.enterState(2);
  }
  
  public final void setHighScore() {
	  System.out.println("entered HighScore");
  }
  
  public final void enterHighScore() {
	  System.out.println("entered HighScore 2");
	  Screen screen=nifty.getCurrentScreen();

	  screen.findElementByName("labelScore").getRenderer(TextRenderer.class).setText("Your Score: 2200");
      String t=((screen.findElementByName("name")).getControl(TextFieldControl.class)).getText();
      System.out.println(t);
  }
  
  public void exitXSwing(){
	  game.getContainer().exit();
	  nifty.exit();
  }

}
