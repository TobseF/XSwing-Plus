/*
 * @version 0.0 09.12.2008
 * @author Tobse F
 */
package tests;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class NiftyXMLStateTester extends StateBasedGame implements ScreenController {

    public static final String XML_TO_START = "gui/high_score.xml";
    private static final String RES_FOLDER = "restest/";

    public NiftyXMLStateTester() {
        super("NiftyXMLTester");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            AppGameContainer game = new AppGameContainer(new NiftyXMLStateTester());
            game.setMinimumLogicUpdateInterval(26);
            game.setMaximumLogicUpdateInterval(26);
            game.setDisplayMode(1024, 768, false);
            game.setClearEachFrame(false);
            game.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        NiftyGameState niftyGameState = new NiftyGameState(0);

        niftyGameState.fromXml(RES_FOLDER + XML_TO_START, this);
        addState(niftyGameState);
    }

    public final void buttonPressed() {
        System.out.println("button pressed");
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
    }

    @Override
    public void onEndScreen() {
    }

    @Override
    public void onStartScreen() {
    }

    public void quit() {
        System.out.println("quit");
    }

}