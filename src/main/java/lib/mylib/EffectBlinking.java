/*
 * @version 0.0 14.10.2008
 * @author Tobse F
 */
package lib.mylib;

import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;

/**
 * A blinking effect.
 *
 * @author Tobse
 * @see #getBlink()
 */
public class EffectBlinking implements Resetable, Updateable {

    /**
     * Times the Blinker should blink
     */
    private int blincCount = 0;
    /**
     * Times the Blinker did blinked
     */
    private int blincedCount = 0;
    /**
     * The blink status
     */
    private boolean blinc;
    /**
     * Blink BallState on start (saved for reset)
     */
    private final boolean visibleOnStart;
    private final MyTimer timer;

    /**
     * @param blincCount     Times the Blinker should blink
     * @param blincDuration  Duration of a blink in ms (visible Time)
     * @param visibleOnStart Whether blink BallState should be on start <code>true<code>
     * @see {@link #getBlink()}
     */
    public EffectBlinking(int blincCount, int blincDuration, boolean visibleOnStart) {
        this.blincCount = blincCount * 2;
        this.visibleOnStart = visibleOnStart;
        blinc = !visibleOnStart;
        timer = new MyTimer(blincDuration, true) {

            @Override
            protected void timerAction() {
                switchBlink();
            }
        };
        reset();
    }

    private void switchBlink() {
        if (blincedCount <= blincCount) {
            blinc = !blinc;
            blincedCount++;
        } else {
            timer.setPaused(true);
        }
    }

    /**
     * Returns the current blinking BallState
     */
    public boolean getBlink() {
        return blinc;
    }

    @Override
    public void reset() {
        timer.reset();
        blincedCount = 0;
        blinc = !visibleOnStart;
    }

    @Override
    public void update(int delta) {
        timer.update(delta);
    }
}