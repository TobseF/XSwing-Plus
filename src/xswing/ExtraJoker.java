package xswing;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import xswing.Ball;

public class ExtraJoker
extends Ball {
    public ExtraJoker(int level, int x, int y) {
        super(level, x, y);
        this.nr = 99;
        this.weight = 0;
        try {
            this.setPic(new Image("res/ball.png"));
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean compare(Ball ball) {
        return true;
    }

    @Override
    protected void drawNumber(Graphics g) {
    }
}
