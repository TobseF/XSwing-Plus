/*
 * @version 0.0 19.07.2009
 * @author Tobse F
 */
package lib.mylib.ident;

import java.io.IOException;
import org.newdawn.slick.*;
import org.newdawn.slick.util.Log;

/**
 * Calculates the Ident with the current time in ms
 * 
 * @see Identable
 * @author Tobse
 */
public class TimeIdent implements Identable {

	private SavedState state;

	public TimeIdent() {
		try {
			state = new SavedState(IDENT_NAME + IDENT_EXTENSION);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getIdentity() {
		String id = state.getString(IDENT_NAME);
		if (id == null || id.isEmpty()) {
			id = String.valueOf(System.currentTimeMillis());
			Log.debug("Created Idendity");
			state.setString(IDENT_NAME, id);
			try {
				state.save();
			} catch (IOException e) {}
		}
		return id;
	}

	@Override
	public void newIdentity() {
		state.setString(IDENT_NAME, "");
		getIdentity();
	}
}
