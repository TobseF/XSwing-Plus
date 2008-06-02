package xswing;

import lib.SObject;
import lib.SpriteSheet;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import xswing.Ball;
import xswing.BallTable;

public class Cannon
extends SObject {
    private int gap = 16;
    private int ba = 48;
    private int pos = 0;
    private Ball ball = null;
    private BallTable ballTable;
    private Sound move;
    private Sound alarm;
    private SpriteSheet cannons;
    private Animation anim1;
    private Animation anim2;
    private final int duration = 180;

    public Cannon(SpriteSheet cannons, int x, int y, Sound[] sounds) {
        super(cannons.getSprite(0, 0), x, y);
        this.move = sounds[0];
        this.alarm = sounds[1];
        this.cannons = cannons;
        this.anim1 = new Animation(cannons.getSprites(1), 180);
        this.anim2 = new Animation(cannons.getSprites(2), 180);
        this.anim1.start();
        this.anim1.setPingPong(true);
        this.anim2.start();
        this.anim2.setPingPong(true);
    }

    public void setBallTable(BallTable ballTable) {
        this.ballTable = ballTable;
    }

    public void moveLeft() {
        if (this.pos >= 1) {
            --this.pos;
            this.move.play();
        }
        this.setBallPos(this.ball);
    }

    public void moveRight() {
        if (this.pos < 7) {
            ++this.pos;
            this.move.play();
        }
        this.setBallPos(this.ball);
    }

    @Override
    public int getX() {
        return this.x + this.gap + this.pos * (this.ba + this.gap);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(this.pic, this.getX() - 12, this.y - 3);
    }

    private void checkHigh() {
        int height = this.ballTable.getColumnHeight(this.pos);
        if (height <= 6) {
            this.setPic(this.cannons.getSprite(0, 0));
        }
        if (height == 7) {
            this.setPic(this.anim1.getCurrentFrame());
        }
        if (height == 8) {
            this.setPic(this.anim2.getCurrentFrame());
            if (!this.alarm.playing()) {
                this.alarm.play();
            }
        }
    }

    public void releaseBall(Ball nextBall) {
        if (this.ball != null) {
            this.ball.toggleMoving();
            this.setBallPos(this.ball);
        }
        this.setBall(this.ballTable.getBall(this.pos, 11));
        this.ballTable.setBall(this.pos, 11, this.ballTable.getBall(this.pos, 12));
        this.ballTable.setBall(this.pos, 12, nextBall);
    }

    public int getPos() {
        return this.pos;
    }

    public Ball getBall() {
        return this.ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
        this.setBallPos(ball);
    }

    private void setBallPos(Ball ball) {
        if (ball != null) {
            ball.setPos(this.getX(), this.y + 8);
        }
    }

    public void update(int delta) {
        this.anim1.update(delta);
        this.anim2.update(delta);
        this.checkHigh();
    }
}
