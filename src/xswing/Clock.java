package xswing;

import lib.mylib.Resetable;
import lib.mylib.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Clock
extends SObject
implements Resetable {
    private long timeS;
    private int timeTemp;
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

    @Override
    public void draw(Graphics g) {
        this.renderTimer();
    }

    @Override
    public void update() {
        this.timeS = (System.currentTimeMillis() - ttime) / 1000L;
    }

    @Override
    public void update(int delta) {
        this.timeTemp += delta;
        if (this.timeTemp > 1000) {
            ++this.timeS;
            this.timeTemp = 1000 - this.timeTemp;
        }
    }

    public void renderTimer() {
        String s = String.format("%02d", (int)this.timeS % 60);
        String m = String.format("%02d", (int)(this.timeS / 60L) % 60);
        String h = String.format("%02d", (int)(this.timeS / 60L / 60L) % 60);
        this.font.drawString(this.x, this.y, String.valueOf(h) + ":" + m + ":" + s);
    }
}
