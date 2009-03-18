/*
 * @version 0.0 24.07.2008
 * @author Tobse F
 */
package lib.mylib.object;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BasicGameState extends org.newdawn.slick.state.BasicGameState {
	private int id = 0;

	public BasicGameState(int id) {
		this.id = id;
	}
	
	public BasicGameState() {
	
	}

	@Override
	public int getID() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {}

}
