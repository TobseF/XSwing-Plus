/*
 * @version 0.0 19.07.2009
 * @author 	Tobse F
 */
package lib.mylib.ident;

import java.io.IOException;
import org.newdawn.slick.*;


/**
 * Calculates the Ident with the current time in ms
 * @see Identable
 * @author Tobse
 */
public class TimeIdent implements Identable{
	private SavedState state;
	
	public TimeIdent() {
		try {
			state = new SavedState(IDENT_NAME + IDENT_EXTENSION);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getIdentity() {
		int id = (int) state.getNumber(IDENT_NAME);
		if(id == 0){
			id = Math.abs((int)System.currentTimeMillis());
			state.setNumber(IDENT_NAME, id);
			try {
				state.save();
			} catch (IOException e) {}
		}
		return id;
	}
}
