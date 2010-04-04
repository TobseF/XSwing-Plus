/*
 * @version 0.0 19.12.2008
 * @author Tobse F
 */
package lib.mylib.math;

import java.util.Random;

public class MyMath {

	private static Random random = null;

	/**
	 * Gets a (maybe) new value with the given aberrance.<br>
	 * e.g.:<br>
	 * <code>getRandom(100, 5);</code> ==> value between 95 and 105
	 * 
	 * @param value value which should be changed
	 * @param percent for aberrance eg. 5 for 5%
	 * @return changed value
	 */
	public static float getRandomAberrance(float value, float percent) {
		if (random == null) {
			random = new Random();
		}
		boolean plus = random.nextBoolean();
		int aberrance = random.nextInt((int) ((percent / 100f) * value + 1));
		return plus ? value + aberrance : value - aberrance;
	}

	/**
	 * Gets a (maybe) new value with the given aberrance.<br>
	 * e.g.:<br>
	 * <code>getRandom(100, 5);</code> ==> value between 95 and 105
	 * 
	 * @param value value which should be changed
	 * @param percent for aberrance e.g. 5 for 5%
	 * @return changed value
	 */
	public static int getRandomAberrance(int value, int percent) {
		return (int) getRandomAberrance((float) value, (float) percent);
	}

	/**
	 * Returns a random number from min (inclusive) to max (also inclusive).
	 * 
	 * @param min minimum
	 * @param max maximum
	 * @return random number from min (inclusive) to max (also inclusive).
	 */
	public static int getInt(int min, int max) {
		return min + random.nextInt(max) + 1;
	}

	/**
	 * Returns a random number between min (exclusive) and max (also exclusive). The number can
	 * approach the bounds up to 0.001.
	 * 
	 * @param min minimum
	 * @param max maximum
	 * @return random number between min (exclusive) and max (also exclusive)
	 */
	public static float getFloat(float min, float max) {
		if (random == null) {
			random = new Random();
		}
		return min + (random.nextFloat() * (max - min));
	}

	/**
	 * Returns the angle in degree of the line from (x1/y1) to (x2/y2)
	 * 
	 * @param x1 X position of the first point
	 * @param y1 Y position of the first point
	 * @param x2 X position of the second point
	 * @param y2 Y position of the second point
	 * @return the angle in degree of the line from (x1/y1) to (x2/y2)
	 */
	public static double getDegree(double x1, double y1, double x2, double y2) {
		return new Vector2D(x2 - x1, y2 - y1).getDegree();
	}

	/**
	 * Returns the angle in degree of the line from (0/0) to (x1/y1).
	 * 
	 * @param x X position of the second point
	 * @param y Y position of the second point
	 * @return the angle in degree of the line from (0/0) to (x1/y1).
	 */
	public static double getDegree(double x, double y) {
		return new Vector2D(x, y).getDegree();
	}

	public static int[] translateToCenter(int posX, int posY, int centerX, int centerY) {
		return new int[] { centerX + posX, centerY + posY };
	}


	/*
	 * public static int[] rotate(int x, int y, double degree) { double a[] =rotate((double)x,
	 * (double)y, degree); return new int[]{(int)a[0],(int) a[1]}; }
	 */
}
