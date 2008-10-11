package xswing.tools;

import lib.mylib.BasicGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.CrossStateTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class EmptyState
extends BasicGameState {
    public EmptyState(int id) {
        super(id);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("init() Empty Stage, id: " + this.getID());
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        g.setColor(Color.red);
        g.fillOval(50.0f, 50.0f, 300.0f, 300.0f);
        g.setColor(Color.white);
        g.drawString("Empty Stage,  id: " + this.getID(), 100.0f, 100.0f);
        g.drawString("Press ENTER for next stage", 100.0f, 130.0f);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input in = container.getInput();
        if (in.isKeyPressed(2) || in.isKeyPressed(28)) {
            game.enterState(this.getID() + 1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        if (container.getInput().isKeyPressed(3)) {
            GameState target = game.getState(this.getID() + 1);
            final long start = System.currentTimeMillis();
            CrossStateTransition t = new CrossStateTransition(target){

                public boolean isComplete() {
                    return System.currentTimeMillis() - start > 2000L;
                }

                public void init(GameState firstState, GameState secondState) {
                }
            };
            game.enterState(this.getID() + 1, t, new EmptyTransition());
        }
    }
}
