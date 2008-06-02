package xswing;

import lib.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import xswing.Resetable;

public class Clock
extends SObject
implements Resetable {
    private long timeS;
    private static long ttime;
    private Font font;

    public Clock(Font font, int x, int y) {
        super(x, y);
        this.font = font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void start() {
        ttime = System.currentTimeMillis();
    }

    @Override
    public void reset() {
        this.start();
    }

    public void tick() {
        this.timeS = (System.currentTimeMillis() - ttime) / 1000L;
    }

    @Override
    public void draw(Graphics g) {
        this.renderTimer();
    }

    public void renderTimer() {
        String s = String.format("%02d", (int)this.timeS % 60);
        String m = String.format("%02d", (int)(this.timeS / 60L) % 60);
        String h = String.format("%02d", (int)(this.timeS / 60L / 60L) % 60);
        this.font.drawString(this.x, this.y, String.valueOf(h) + ":" + m + ":" + s);
    }
}
