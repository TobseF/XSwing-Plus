/*
 * @version 0.0 31.08.2008
 * @author Tobse F
 */
package tests;

import de.lessvoid.nifty.slick.NiftyGameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SlickNiftyTest extends StateBasedGame {

    public SlickNiftyTest() {
        super("SlickNiftyTest");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            AppGameContainer game = new AppGameContainer(new SlickNiftyTest());
            game.setMinimumLogicUpdateInterval(20);
            game.setMaximumLogicUpdateInterval(20);
            game.setDisplayMode(800, 600, false);
            game.setClearEachFrame(false);
            game.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        NiftyGameState state = new NiftyGameState(0);
        state.fromXml("tests/helloworld.xml", new MyScreenController());
        addState(state);
    }

}
