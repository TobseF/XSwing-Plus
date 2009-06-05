/*
 * @version 0.0 19.12.2008
 * @author Tobse F
 */
package lib.mylib.math;

import java.util.Random;

public class MyMath {

	private static Random random = null;

	/**
	 * Gets a (maby) new value with the given aberrance.<br>
	 * e.g.:<br>
	 * <code>getRandom(100, 5);</code> ==> value between 95 and 105
	 * 
	 * @param value value which shuld be changed
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
	 * Gets a (maby) new value with the given aberrance.<br>
	 * e.g.:<br>
	 * <code>getRandom(100, 5);</code> ==> value between 95 and 105
	 * 
	 * @param value value which shuld be changed
	 * @param percent for aberrance eg. 5 for 5%
	 * @return changed value
	 */
	public static int getRandomAberrance(int value, int percent) {
		return (int) getRandomAberrance((float) value, (float) percent);
	}

	public static int getInt(int min, int max) { // FIXME: max is never reached! , write a test
		return (int) getFloat((float) min, (float) max);
	}

	public static float getFloat(float min, float max) {
		if (random == null) {
			random = new Random();
		}
		return min + (random.nextFloat() * (max - min));
	}

	public static double getDegree(double x1, double y1, double x2, double y2) {
		double x = Math.max(x1, x2) - Math.min(x1, x2), y = Math.max(y1, y2)
				- Math.min(y1, y2);
		double degree = 0;
		if (x == 0 && y == 0)
			return 0;
		if (x == 0 && y1 > y2)
			return 90;
		if (x1 > x2 && y == 0)
			return 180;
		if (x == 0 && y1 < y2)
			return 270;
		if (y2 < y1) {
			degree = Math.atan(y / x);
		} else {
			degree = Math.atan(x / y);
		}
		degree = Math.toDegrees(degree);
		if (x1 > x2 && y1 > y2) {
			degree = 180 - degree;
		}
		if (x1 > x2 && y1 < y2) {
			degree = 270 - degree;
		}
		if (x1 < x2 && y1 < y2) {
			degree += 270;
		}
		return degree;
	}

	public static double getDegree(double x, double y) {
		return getDegree(0, 0, x, y);
	}

	public static int[] translateToCenter(int posX, int posY, int centerX, int centerY) {
		return new int[] { centerX + posX, centerY + posY };
	}

	public static double[] rotate(double x, double y, double degree) {
		if (Math.abs(degree) == 90) {
			double temp = x;
			x = y;
			y = temp;
			if (degree < 0)
				y = -y;
			else
				x = -x;
		} else {
			double angle = Math.toRadians(degree);

			double rx = Math.cos(angle) * x - Math.sin(angle) * y;
			double ry = Math.sin(angle) * x + Math.cos(angle) * y;

			x = rx;
			y = ry;
		}
		return new double[] { x, y };
	}
	/*
	 * public static int[] rotate(int x, int y, double degree) { double a[] =rotate((double)x,
	 * (double)y, degree); return new int[]{(int)a[0],(int) a[1]}; }
	 */
}
