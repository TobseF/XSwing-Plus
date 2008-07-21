package xswing;

import lib.Resetable;
import lib.SpriteSheet;
import xswing.Ball;

public class Level
extends Ball
implements Resetable {
    private int startLeveL = 3;
    private int maxLeveL = 45;

    public Level(int nr, int x, int y, SpriteSheet ballsSpriteSheet) {
        super(nr, x, y, ballsSpriteSheet);
        this.weight = nr + 1;
        this.setNr(nr);
    }

    @Override
    public void reset() {
        this.setLevel(this.startLeveL);
    }

    public void setLevel(int level) {
        this.setNr(level + 1);
        this.weight = level + 1;
    }

    @Override
    public void update() {
    }

    public int getLevel() {
        return this.getNr();
    }

    public void nextLevel() {
        if (this.getNr() + 1 < this.maxLeveL) {
            this.setNr(this.getNr() + 1);
            this.weight = this.getNr() + 1;
        }
    }
}
