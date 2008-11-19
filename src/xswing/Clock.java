/*
 * @version 0.0 15.04.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.MyTimer;
import lib.mylib.Resetable;
import lib.mylib.SObject;
import lib.mylib.Updateable;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/** The timer which counts the seconds since the game was started */
public class Clock extends SObject implements Resetable, Updateable {
	/** The Clock Font */
	private Font font;
	/** The Current time after timer was started in seconds */
	private double timeSinceStart;
	private MyTimer timer;

	public Clock(Font font, int x, int y) {
		super(x, y);
		this.font = font;
		timer = new MyTimer(1000, true) {
			@Override
			protected void timerAction() {
				timeSinceStart++;
			}
		};
	}

	/** Set the font of the component */
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public void reset() {
		timer.reset();
		timeSinceStart = 0;
	}

	/** Returns the current time in seconds since the game was started */
	public double getTimeSinceStart() {
		return timeSinceStart;
	}

	@Override
	public void draw(Graphics g) {
		font.drawString(x, y, formatTime(timeSinceStart));
	}

	@Override
	public void update(int delta) {
		timer.update(delta);
	}

	/** Formats the Time in hh:mm:ss */
	private String formatTime(double timeSinceStart) {
		String s = String.format("%02d", (int) timeSinceStart % 60);
		String m = String.format("%02d", (int) (timeSinceStart / 60) % 60);
		String h = String.format("%02d", (int) ((timeSinceStart / 60) / 60) % 60);
		return h + ":" + m + ":" + s;
	}
}