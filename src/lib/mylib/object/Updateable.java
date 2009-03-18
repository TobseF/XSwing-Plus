/*
 * @version 0.0 03.06.2008
 * @author Tobse F
 */
package lib.mylib.object;

/**
 * @author Tobse For thnings that should be updated
 */
public interface Updateable {
	
	/** Performs all logic which should be updated 
	 * @param delta time in ms since the last time updateded was called
	 */
	public void update(int delta);
}
