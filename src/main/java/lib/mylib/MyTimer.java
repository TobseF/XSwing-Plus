/*
 * @version 0.0 22.11.2008
 * @author Tobse F
 */
package lib.mylib;

import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;

/**
 * Timer which provides a {@link #timerAction()} function. It's called after the the timer was
 * started with {@link #start()} and the {@link #duration} period has passe by. The timer
 * <b>has to<b> updated with {@link #update(int)}.
 *
 * @author Tobse
 * @see #timerAction()
 */
public class MyTimer implements Updateable, Resetable {

    /**
     * Time in ms before the Timer Action is called
     */
    private int duration;
    /**
     * Time since the timer was started, or last {@link #timerAction()} was called.
     */
    private int timeSinceStart;
    /**
     * Whether the timer should run consecutively (true)
     */
    private final boolean repeating;
    /**
     * Whether the timer is running at the moment
     */
    private boolean running = true;
    /**
     * Whether the timer should be started on build
     */
    private boolean runningOnStart = true;

    /**
     * @param duration time before the Timer Action is called.
     * @param repeat   Whether the timer should run consecutively (true) or stop after one
     *                 runningOnStart Whether the Timer should start running on build (true) ore
     *                 have started with {@link #setPaused(boolean)} Timer Action (false)
     * @see #MyTimer(int, boolean)
     * @see #timerAction()
     */
    public MyTimer(int duration, boolean repeat, boolean runningOnStart) {
        this(duration, repeat);
        this.runningOnStart = runningOnStart;
        running = runningOnStart;
    }

    /**
     * @param duration time before the Timer Action is called.
     * @param repeat   Whether the timer should run consecutively (true) or stop after one Timer
     *                 Action (false)
     * @see #MyTimer(int, boolean, boolean)
     * @see #timerAction()
     */
    public MyTimer(int duration, boolean repeat) {
        this.duration = duration;
        this.repeating = repeat;
    }

    @Override
    public void update(int delta) {
        if (running) {
            timeSinceStart += delta;
            if (timeSinceStart >= duration) {
                timerAction();
                if (repeating) {
                    timeSinceStart -= duration;
                } else {
                    running = false;
                }
            }
        }
    }

    /**
     * Action which is proceeded on a Timer event. Overwrite this function to get Timer
     * functionality.
     */
    protected void timerAction() {
    }

    public void setPaused(boolean doPause) {
        running = !doPause;
    }

    /**
     * @return if the timer paused (not running)
     */
    public boolean isPaused() {
        return !running;
    }

    /**
     * @return if the timer is not running and the {@link #timerAction()} was called
     */
    public boolean isFinsihed() {
        return (!running && timeSinceStart >= duration);
    }

    /**
     * Starts the timer.
     */
    public void start() {
        running = true;
    }

    /**
     * Pauses the timer.
     */
    public void pause() {
        running = false;
    }

    /**
     * Stops the timer, an resets it.
     */
    public void stop() {
        reset();
        pause();
    }

    public void setDuration(int duration) {
        this.duration = duration;
        reset();
    }


    public boolean isRunning() {
        return running;
    }


    public boolean isRunningOnStart() {
        return runningOnStart;
    }


    public boolean isRepeating() {
        return repeating;
    }

    @Override
    public void reset() {
        timeSinceStart = 0;
        running = runningOnStart;
    }
}