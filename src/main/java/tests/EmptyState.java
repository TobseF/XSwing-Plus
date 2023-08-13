/*
 * @version 0.0 04.08.2008
 * @author Tobse F
 */
package tests;

import lib.mylib.object.BasicGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.CrossStateTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class EmptyState extends BasicGameState {

    public EmptyState(int id) {
        super(id);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("init() Empty Stage, id: " + getID());
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.setAntiAlias(true);
        g.setColor(Color.red);
        g.fillOval(50, 50, 300, 300);
        g.setColor(Color.white);
        g.drawString("Empty Stage,  id: " + getID(), 100, 100);
        g.drawString("Press ENTER for next stage", 100, 130);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        Input in = container.getInput();
        if (in.isKeyPressed(Input.KEY_1) || in.isKeyPressed(Input.KEY_ENTER)) {
            game.enterState(getID() + 1, new FadeOutTransition(Color.black),
                    new FadeInTransition(Color.black));
        }
        if (container.getInput().isKeyPressed(Input.KEY_2)) {
            GameState target = game.getState(getID() + 1);

            final long start = System.currentTimeMillis();
            CrossStateTransition t = new CrossStateTransition(target) {

                @Override
                public boolean isComplete() {
                    return (System.currentTimeMillis() - start) > 2000;
                }

                public void init(GameState firstState, GameState secondState) {
                }
            };

            game.enterState(getID() + 1, t, new EmptyTransition());
        }

    }

}
