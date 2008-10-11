package xswing;

import lib.mylib.Resetable;
import lib.mylib.SObject;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public class HighScoreMultiplicator
extends SObject
implements Resetable {
    private SpriteSheet multiplicator;
    private int multi = 0;
    private int timer;
    private int timerTemp = this.timer = 2500;

    public HighScoreMultiplicator(int x, int y, SpriteSheet multiplicator) {
        super(x, y);
        this.multiplicator = multiplicator;
    }

    public void score() {
        this.multi = 4;
        this.timerTemp = this.timer;
    }

    @Override
    public void update(int delta) {
        this.timerTemp += delta;
        if (this.timerTemp >= this.timer) {
            if (this.multi > 0) {
                --this.multi;
            }
            this.timerTemp = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.multi > 0) {
            g.drawImage(this.multiplicator.getSprite(0, 3 - this.multi), this.x, this.y);
        }
    }

    public int getMulti() {
        return this.multi + 1;
    }

    @Override
    public void reset() {
        this.multi = 0;
        this.timerTemp = 0;
    }
}
