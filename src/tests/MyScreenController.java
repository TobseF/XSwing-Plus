package tests;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * ScreenController for Hello World Example.
 * @author void
 */
public class MyScreenController implements ScreenController {

  /** nifty instance. */
  @SuppressWarnings("unused")
private Nifty nifty;

  /**
   * Bind this ScreenController to a screen.
   * @param newNifty nifty
   * @param newScreen screen
   */
  public final void bind(final Nifty newNifty, final Screen newScreen) {
    this.nifty = newNifty;
  }

  /**
   * on start screen interactive.
   */
  public final void onStartScreen() {
  }

  /**
   * on end screen.
   */
  public final void onEndScreen() {
  }

  /**
   * quit method called from the xml-Fil
   */
  public final void StartXSwing() {
	  System.out.println("start XSing()");
  }
  
  /** GUI Test   */
  public final void guiTest() {
	 /* System.out.println("guiTest()");

		
		GameState target = game.getState(1);
		
		final long start = System.currentTimeMillis();
		CrossStateTransition t = new CrossStateTransition(target) {				
			public boolean isComplete() {
				return (System.currentTimeMillis() - start) > 2000;
			}
			public void init(GameState firstState, GameState secondState) {	}
		};
		
		game.enterState(1, t, new EmptyTransition());*/
		//game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	  
	  //nifty.exit();
  }

}
