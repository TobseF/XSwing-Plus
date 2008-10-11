package xswing.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.textfield.TextFieldControl;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import xswing.MainGame;

public class ScoreScreenController
implements ScreenController {
    private Nifty nifty;
    private StateBasedGame game;
    private int highScore;
    int s = 0;

    public ScoreScreenController(StateBasedGame game, int highscore) {
        this.game = game;
        this.highScore = highscore;
    }

    public final void bind(Nifty newNifty, Screen newScreen) {
        this.nifty = newNifty;
    }

    public final void onStartScreen() {
        Screen screen = this.nifty.getCurrentScreen();
        this.setHighScore(screen);
    }

    public final void onEndScreen() {
    }

    private final void setHighScore(Screen screen) {
        System.out.println("entered HighScore");
        System.out.println("highscorestart");
        ((TextRenderer)((Object)screen.findElementByName("labelScore").getRenderer(TextRenderer.class))).setText("Your Score: " + this.highScore);
        screen.setFocus(screen.findElementByName("name"));
        screen.lostFocus(screen.findElementByName("name"));
    }

    public final void enterHighScore() {
        Screen screen = this.nifty.getCurrentScreen();
        String t = ((TextFieldControl)((Object)screen.findElementByName("name").getControl(TextFieldControl.class))).getText();
        System.out.println("Score entered: " + t + " " + this.highScore);
        ((MainGame)this.game.getState(2)).reset();
        this.game.enterState(2, new FadeOutTransition(), new FadeInTransition());
    }
}
