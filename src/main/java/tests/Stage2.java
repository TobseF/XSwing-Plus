/*
 * @version 0.0 19.07.2009
 * @author Tobse F
 */
package tests;

import lib.mylib.object.BasicGameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Stage2 extends BasicGameState {

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.setColor(Color.blue);
        g.fillOval(100, 100, 100, 100);
        g.setColor(Color.white);
        g.drawString("State 1", 10, 10);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // TODO Auto-generated method stub

    }

}
