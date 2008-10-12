package lib.mylib;

import lib.mylib.Resetable;

public class EffectBlinking
implements Resetable {
    private int blincCount = 0;
    private int blincedCount = 0;
    private long blincDuration = 100L;
    private boolean running = true;
    private boolean blinc = false;
    private long lastBlinc;

    public EffectBlinking(int blincCount, long blincDuration, boolean running) {
        this.blincCount = blincCount * 2;
        this.blincDuration = blincDuration;
        this.running = running;
        this.reset();
    }

    public EffectBlinking(int blincCount, long blincDuration) {
        this(blincCount, blincDuration, true);
    }

    public boolean getBlink() {
        long now = System.currentTimeMillis();
        long timeSinceLastFlash = now - this.lastBlinc;
        if (this.running || this.blincedCount > this.blincCount) {
            return true;
        }
        if (timeSinceLastFlash >= this.blincDuration) {
            this.lastBlinc = System.currentTimeMillis();
            this.blinc = !this.blinc;
            ++this.blincedCount;
        }
        return this.blinc;
    }

    @Override
    public void reset() {
        this.lastBlinc = System.currentTimeMillis();
        this.blincedCount = 0;
    }
}
