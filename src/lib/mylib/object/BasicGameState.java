/*
 * @version 0.0 24.07.2008
 * @author Tobse F
 */
package lib.mylib.object;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public abstract class BasicGameState extends org.newdawn.slick.state.BasicGameState {

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



}
