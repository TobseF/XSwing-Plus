package xswing;

import lib.mylib.EffectBlinking;
import lib.mylib.Resetable;
import lib.mylib.SpriteSheet;
import org.newdawn.slick.Graphics;
import xswing.Ball;

public class Level
extends Ball
implements Resetable {
    private int startLeveL = 3;
    private int maxLeveL = 45;
    EffectBlinking blinking;

    public Level(int nr, int x, int y, SpriteSheet ballsSpriteSheet) {
        super(nr, x, y, ballsSpriteSheet);
        this.weight = nr + 1;
        this.setNr(nr);
        this.blinking = new EffectBlinking(9, 250L, false);
    }

    @Override
    public void reset() {
        this.setLevel(this.startLeveL);
        this.blinking.reset();
    }

    public void setLevel(int level) {
        this.setNr(level + 1);
        this.weight = level + 1;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        if (this.blinking.getBlink()) {
            super.draw(g);
        }
    }

    public int getLevel() {
        return this.getNr();
    }

    public void nextLevel() {
        if (this.getNr() + 1 < this.maxLeveL) {
            this.setNr(this.getNr() + 1);
            this.weight = this.getNr() + 1;
            this.blinking.reset();
        }
    }
}
