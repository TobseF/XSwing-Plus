package test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Keys
extends BasicGame {
    Image background;
    Image ball;
    static AppGameContainer container;
    int x;
    int y;
    int delatTemp;
    float angle = 20.0f;

    public Keys() {
        super("sad");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new Keys());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.background = new Image("res/swing_background.jpg");
        this.ball = new Image("res/ball.png");
    }

    public void update(GameContainer container, int delta) throws SlickException {
        Input in = container.getInput();
        System.out.println(delta);
        int l = 50;
        if (this.delatTemp > 70) {
            if (in.isKeyDown(203)) {
                this.x -= l;
                this.ball.rotate(-this.angle);
            }
            if (in.isKeyDown(205)) {
                this.x += l;
                this.ball.rotate(this.angle);
            }
            this.delatTemp = 0;
        } else {
            this.delatTemp += delta;
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(this.background, 0.0f, 0.0f);
        g.drawImage(this.ball, this.x, this.y);
    }
}
