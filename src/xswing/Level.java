package xswing;

import lib.SpriteSheet;
import xswing.Ball;
import xswing.Resetable;

public class Level
extends Ball
implements Resetable {
    public Level(int nr, int x, int y, SpriteSheet ballsSpriteSheet) {
        super(nr, x, y, ballsSpriteSheet);
        this.weight = nr + 1;
        this.setNr(nr);
    }

    @Override
    public void reset() {
        this.setLevel(3);
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
        this.setNr(this.getNr() + 1);
        this.weight = this.getNr() + 1;
    }
}
