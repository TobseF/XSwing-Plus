/*
 * @version 0.0 22.11.2008
 * @author Tobse F
 */
package lib.mylib;

/**
 * Timer wich provides a {@link #timerAction()} function. It's called after the
 * <code>duration</code> .
 * 
 * @author Tobse
 * @see #timerAction()
 */
public class MyTimer implements Updateable, Resetable {
	private int duration, timeSinceStart;
	/** Whether the timer schould run consecutively (true) */
	private boolean repeat;
	/** Whether the timer is running at the moment */
	private boolean running = true;
	/** Whether the timer should be started on build */
	private boolean runningOnStart = true;

	/**
	 * @param duration Timer before the Timer Action is called.
	 * @param repeat Whether the timer schould run consecutively (true) or stop after one
	 *            runningOnStart Whether the Timer should start running on build (true) ore
	 *            have started with {@link #setPause(boolean)} Timer Action (false)
	 * @see #MyTimer(int, boolean)
	 */
	public MyTimer(int duration, boolean repeat, boolean runningOnStart) {
		this(duration, repeat);
		this.runningOnStart = runningOnStart;
		running = runningOnStart;
	}

	/**
	 * @param duration Timer before the Timer Action is called.
	 * @param repeat Whether the timer schould run consecutively (true) or stop after one Timer
	 *            Action (false)
	 * @see #MyTimer(int, boolean, boolean)
	 */
	public MyTimer(int duration, boolean repeat) {
		this.duration = duration;
		this.repeat = repeat;
	}

	@Override
	public void update(int delta) {
		if (running) {
			timeSinceStart += delta;
			if (timeSinceStart >= duration) {
				timerAction();
				if (repeat) {
					timeSinceStart -= duration;
				} else {
					running = false;
				}
			}
		}
	}

	/**
	 * Action which is procceden on a Timer event. Overwrite this function to get Timer
	 * functionality.
	 */
	protected void timerAction() {}

	public void setPause(boolean doPause) {
		running = !doPause;
	}

	public boolean isInPause() {
		return !running;
	}

	public void start() {
		running = true;
	}

	@Override
	public void reset() {
		timeSinceStart = 0;
		running = runningOnStart;
	}
}