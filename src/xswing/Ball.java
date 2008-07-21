package xswing;

import lib.SObject;
import lib.SpriteSheet;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import xswing.BallTable;
import xswing.Effects;

public class Ball
extends SObject {
    Font font;
    protected int nr = 0;
    protected int weight = 0;
    private int effect = 0;
    private int speed = 20;
    private boolean moving = false;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    private int movingType = 1;
    private int readyToKill = -1;
    protected BallTable ballTable = null;
    private Sound collsionSound = null;
    private Effects effects = null;
    private SpriteSheet ballsSpriteSheet = null;
    public static final int ALIVE = 0;
    public static final int WAITING_FOR_KILL = 1;
    public static final int KILL_IMMEDIATELY = 2;
    public static final int KILLING_STARTED = 3;
    public static final int WAITING_FOR_SHRINK = 4;
    public static final int A = 48;

    public Ball() {
    }

    public Ball(int level, int x, int y, SpriteSheet balls) {
        super(x, y);
        this.nr = (int)(Math.random() * (double)level + 1.0);
        this.weight = 1 + (int)(Math.random() * (double)(level + 1));
        this.ballsSpriteSheet = balls;
        if (balls != null) {
            this.setPic(this.ballsSpriteSheet.getSprite(this.nr));
        }
    }

    public Ball(int level, int x, int y) {
        this(level, x, y, null);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setCollsionSound(Sound collsionSound) {
        this.collsionSound = collsionSound;
    }

    public void setEffects(Effects effects) {
        this.effects = effects;
    }

    public void setBallsSpriteSheet(SpriteSheet ballsSpriteSheet) {
        this.ballsSpriteSheet = ballsSpriteSheet;
        this.setNr(this.nr);
    }

    public boolean isReadyToKill() {
        return this.readyToKill > 0;
    }

    public int getReadyToKill() {
        return this.readyToKill;
    }

    public void kill(int effectNr) {
        this.readyToKill = effectNr;
    }

    public void setGrid(BallTable ballTable) {
        this.ballTable = ballTable;
    }

    public int getEffect() {
        return this.effect;
    }

    public int getNr() {
        return this.nr;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        this.drawNumber(g);
    }

    protected void drawNumber(Graphics g) {
        if (this.font != null) {
            if (this.weight <= 9) {
                this.font.drawString(this.x + 18, this.y + 21, String.valueOf(this.weight));
            } else {
                this.font.drawString(this.x + 13, this.y + 21, String.valueOf(this.weight));
            }
        }
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean isMoving() {
        return this.moving;
    }

    public void toggleMoving() {
        boolean bl = this.moving = !this.moving;
        if (this.moving && this.ballTable.isOverGrid(this.x, this.y)) {
            this.ballTable.removeBall(this);
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean compare(Ball ball) {
        return this.getNr() == ball.getNr() || ball.getNr() == 99;
    }

    public void setNr(int nr) {
        this.nr = nr;
        if (this.ballsSpriteSheet != null) {
            this.pic = this.ballsSpriteSheet.getSprite(nr);
        }
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public void update() {
        block8: {
            block7: {
                if (!this.moving) break block7;
                switch (this.movingType) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        this.y += this.speed;
                        if (this.checkCollison()) {
                            this.collide();
                            break;
                        }
                        break block8;
                    }
                    case 2: {
                        break;
                    }
                }
                break block8;
            }
            if (!this.checkCollison() && this.ballTable.isOverGrid(this.x, this.y)) {
                this.toggleMoving();
            }
        }
    }

    private boolean checkCollison() {
        int[] pos;
        boolean collision = false;
        if (this.ballTable.isOverGrid(this.x, this.y) && ((pos = this.ballTable.getField(this.x, this.y))[1] == 0 || !this.ballTable.isEmpty(pos[0], pos[1] - 1))) {
            collision = true;
        }
        return collision;
    }

    private void collide() {
        int[] pos = this.ballTable.getField(this.x, this.y);
        if (this.collsionSound != null) {
            this.collsionSound.play();
        }
        this.ballTable.setBall(pos[0], pos[1], this);
        this.setPos(this.ballTable.getFieldPosOnScreen(pos));
        this.toggleMoving();
        if (this.effects != null) {
            this.effects.addEffect(this, 1);
        }
        pos = this.ballTable.getField(this.x, this.y);
    }
}
