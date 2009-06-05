/*
 * @version 0.0 24.02.2009
 * @author Tobse F
 */
package lib.mylib.object;

public interface Positionable {

	/**
	 * Sets the X-Position on the Screen
	 * 
	 * @param x -in pixels
	 */
	public void setX(int x);

	/**
	 * Sets the Y-Position on the Screen
	 * 
	 * @param y -in pixels
	 */
	public void setY(int y);

	/**
	 * @return x position
	 */
	public int getX();

	/**
	 * @return y position
	 */
	public int getY();

}
