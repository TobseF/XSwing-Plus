/*
 * @version 0.0 15.04.2008
 * @author Tobse F
 */
package lib.mylib.util;

import lib.mylib.MyTimer;
import lib.mylib.object.*;
import org.newdawn.slick.*;

/** The timer which counts the seconds since the game was started */
public class Clock extends SObject implements Resetable, Updateable {

	/** The Clock Font */
	protected Font font;
	/** The current time after timer was started in seconds */
	private long timeSinceStart;
	/** Timer which counts the seconds */
	private MyTimer timer;

	public Clock(Font font, int x, int y) {
		super(x, y);
		if (font == null) {
			throw new IllegalArgumentException("no font is set");
		}
		this.font = font;
		initTimer();
	}

	public Clock(Font font) {
		this(font, 0, 0);
	}

	public Clock() {
		initTimer();
	}

	private void initTimer() {
		timer = new MyTimer(1000, true) {

			@Override
			protected void timerAction() {
				timeSinceStart++;
			}
		};
	}

	@Override
	public void reset() {
		timer.reset();
		timeSinceStart = 0;
	}

	/**
	 * Returns the current time in seconds since the game was started
	 * 
	 * @see #getFormattedTime()
	 */
	public double getTimeSinceStart() {
		return timeSinceStart;
	}

	@Override
	public void render(Graphics g) {
		if (font != null && isVisible) {
			font.drawString(x, y, getFormattedTimeAsString(timeSinceStart));
		}
	}

	@Override
	public void update(int delta) {
		timer.update(delta);
	}

	/**
	 * @return time formatted in hh:mm:ss
	 * @see #getFormattedTime()
	 */
	public static String getFormattedTimeAsString(long timeSinceStart) {
		String time[] = getFormattedTime(timeSinceStart);
		String h = time[0];
		String m = time[1];
		String s = time[2];
		return h + ":" + m + ":" + s;
	}

	/**
	 * @return array with the curret tme.<br>
	 *         array[0] = hh <br>
	 *         array[1] = mm <br>
	 *         array[2] = ss
	 * @see #getFormattedTimeAsString()
	 */
	public static String[] getFormattedTime(long timeSinceStart) {
		String s = String.format("%02d", (int) timeSinceStart % 60);
		String m = String.format("%02d", (int) (timeSinceStart / 60) % 60);
		String h = String.format("%02d", (int) ((timeSinceStart / 60) / 60) % 60);
		return new String[] { h, m, s };
	}
}