package lib.mylib;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BasicGameState
extends org.newdawn.slick.state.BasicGameState {
    private final int id;

    public BasicGameState(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
    }

    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }
}
