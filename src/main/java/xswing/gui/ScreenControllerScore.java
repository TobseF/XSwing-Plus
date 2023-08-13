package xswing.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.button.controller.ButtonControl;
import de.lessvoid.nifty.controls.textfield.controller.TextFieldControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import lib.mylib.highscore.HighScoreLine;
import lib.mylib.highscore.HighScoreTable;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.util.Clock;
import lib.mylib.util.LanguageSelector;
import lib.mylib.util.MyOptions;
import lib.mylib.util.SlickUtils;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.util.Log;
import xswing.GameStatistics;
import xswing.net.ThreadedHighScoreSubmitter;
import xswing.net.ThreadedHighScoreSubmitter.ScoreSubmitThread;
import xswing.start.XSwing;

import java.text.NumberFormat;

import static xswing.start.XSwing.SCORE_LINE_SUBMIT_FILE;
import static xswing.start.XSwing.SCORE_SERVER_PATH;

public class ScreenControllerScore implements ScreenController {

    private Nifty nifty;
    private Screen screen;
    private final StateBasedGame game;
    private int highScore;
    private final HighScoreTable highScoreList;
    private TextFieldControl textField;
    private final Clock clock;
    private final GameStatistics gameStatistics;
    private String playerName;


    public ScreenControllerScore(StateBasedGame game, HighScoreTable highScoreList, Clock clock,
                                 GameStatistics gameStatistics) {
        this.game = game;
        this.highScoreList = highScoreList;
        this.clock = clock;
        this.gameStatistics = gameStatistics;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        // VOID: no Sound and no mouse down event
    }

    /**
     * Bind this ScreenController to a screen.
     *
     * @param newNifty  nifty
     * @param newScreen screen
     */
    public final void bind(final Nifty newNifty, final Screen newScreen) {
        this.nifty = newNifty;
        this.screen = newScreen;
        // screen.findElementByName("name").addInputHandler(new KeyInputHandler() {
        //			
        // @Override
        // public boolean keyEvent(NiftyInputEvent e) {
        // if (e == NiftyInputEvent.SubmitText) {
        // enterHighScore();
        // return true;
        // }
        // return false;
        // }
        // });
    }

    /**
     * on start screen interactive.
     */
    public final void onStartScreen() {
        Screen screen = nifty.getCurrentScreen();
        SlickUtils.hideMouse(game.getContainer(), false);
        // nifty.toggleDebugConsole(true); //Debug text
        setHighScore(screen);
        textField = screen.findControl("name", TextFieldControl.class);
        textField.setMaxLength(10);
        playerName = MyOptions.getString(Args.playerName, "");
        textField.setText(playerName);
        screen.findElementByName("labelName").getRenderer(TextRenderer.class).setText(
                LanguageSelector.getString("your_name") + ": ");
        screen.findElementByName("yourScore").getRenderer(TextRenderer.class).setText(
                LanguageSelector.getString("your_score") + ": ");
        screen.findControl("buttonNext", ButtonControl.class).setText(LanguageSelector.getString("next"));
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
        String name = textField.getText();
        if (!name.equals(playerName)) {
            MyOptions.setString(Args.playerName, name);
//			MyOptions.save();
        }
        Log.info("Score entered: " + highScore + " " + name);
        Log.info("Released Balls: " + gameStatistics.getReleasedBalls() + " Destroyed Balls: " + gameStatistics.getDestroyedBalls());
        HighScoreLine newScore = new HighScoreLine(highScore, name, clock.getTimeSinceStart(), gameStatistics.getReleasedBalls(),
                gameStatistics.getDestroyedBalls());
        highScoreList.addScore(newScore);
        highScoreList.save();
        boolean submitHighscoreOnline = false;
        if (submitHighscoreOnline) {
            Log.info("Submit score online");
            ThreadedHighScoreSubmitter.submitScore(name, highScore, clock.getTimeSinceStart(), gameStatistics.getReleasedBalls(),
                    gameStatistics.getDestroyedBalls());
            new ScoreSubmitThread(newScore, XSwing.XSWING_HOST_URL + SCORE_SERVER_PATH + SCORE_LINE_SUBMIT_FILE);
        }
        nifty.getMouseInputEventQueue().reset();
        SlickUtils.hideMouse(game.getContainer(), true);
        game.enterState(XSwing.START_SCREEN, new EmptyTransition(), new EmptyTransition());
        // FIXME: score isn't submit offline
    }


}