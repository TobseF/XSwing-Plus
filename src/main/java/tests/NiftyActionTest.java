/*
 * @version 0.0 08.09.2009
 * @author Tobse F
 */
package tests;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.slick.NiftyGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class NiftyActionTest extends StateBasedGame implements ScreenController {

    private static final int ID_MAIN_MENU = 0;
    private static final int ID_IN_GAME = 1;

    /**
     * Counts how often the start button was pressed
     */
    public static int callingCounter = 0;

    public NiftyActionTest() {
        super("Main");
    }

    private static NiftyActionTest test;

    public static void main(String[] args) {
        try {
            test = new NiftyActionTest();
            AppGameContainer game = new AppGameContainer(test);
            game.setShowFPS(true);
            game.setDisplayMode(800, 600, false);
            game.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by pressing start in main_menu_test.xml
     */
    public void enterGame() {
        callingCounter++;
        System.out.println("enterGame() " + callingCounter);
        test.enterState(1);
    }

    public class InGame extends BasicGameState {

        @Override
        public void render(GameContainer container, StateBasedGame game, Graphics g)
                throws SlickException {
            g.drawString("In Game, press ESC to go back", 100, 100);
            g.drawString("Start pressed: " + callingCounter + " times!", 100, 120);
        }

        @Override
        public void update(GameContainer container, StateBasedGame game, int delta)
                throws SlickException {
            if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
                // Go back to Main Menu
                game.enterState(ID_MAIN_MENU);
            }
        }

        @Override
        public int getID() {
            return ID_IN_GAME;
        }

        @Override
        public void init(GameContainer container, StateBasedGame game) throws SlickException {
        }
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

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        NiftyGameState mainMenu = new NiftyGameState(ID_MAIN_MENU);
        mainMenu.fromXml("tests/main_menu_test.xml", test);
        addState(mainMenu);

        BasicGameState inGame = new InGame();
        addState(inGame);
    }
}