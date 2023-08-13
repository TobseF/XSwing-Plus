/*
 * @version 0.0 02.05.2009
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class TransitionTest extends StateBasedGame {

    public TransitionTest() {
        super("TransitionTest");
    }

    public static void main(String[] args) {
        AppGameContainer test;
        try {
            test = new AppGameContainer(new TransitionTest());
            test.setDisplayMode(800, 600, false);
            test.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    class Transition1 extends BasicGameState {

        @Override
        public int getID() {
            return 0;
        }

        @Override
        public void init(GameContainer container, StateBasedGame game) throws SlickException {
        }

        @Override
        public void render(GameContainer container, StateBasedGame game, Graphics g)
                throws SlickException {
            g.setColor(Color.green);
            g.fillOval(100, 100, 200, 200);
        }

        @Override
        public void update(GameContainer container, StateBasedGame game, int delta)
                throws SlickException {
            if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
                game.enterState(1, new FadeOutTransition(), new FadeInTransition());
            }
        }

    }

    class Transition2 extends BasicGameState {

        @Override
        public int getID() {
            return 1;
        }

        @Override
        public void init(GameContainer container, StateBasedGame game) throws SlickException {
        }

        @Override
        public void render(GameContainer container, StateBasedGame game, Graphics g)
                throws SlickException {
            g.setColor(Color.red);
            g.fillOval(100, 100, 200, 200);
        }

        @Override
        public void update(GameContainer container, StateBasedGame game, int delta)
                throws SlickException {

        }

    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new Transition1());
        addState(new Transition2());
    }

}
