package xswing;

import lib.mylib.Resetable;
import lib.mylib.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import xswing.Level;

public class BallCounter
extends SObject
implements Resetable {
    private Font font;
    private int balls = 0;
    private Level level;
    private int letterLenght;

    public BallCounter(Font font, int x, int y) {
        super(x, y);
        this.font = font;
        this.letterLenght = font.getWidth("0");
    }

    @Override
    public void reset() {
        this.balls = 0;
    }

    public void count() {
        ++this.balls;
        if (this.level != null && this.balls % 50 == 0) {
            this.level.nextLevel();
        }
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void draw(Graphics g) {
        this.font.drawString(this.x - (String.valueOf(this.balls).length() - 1) * this.letterLenght / 2, this.y, "" + this.balls);
    }
}
