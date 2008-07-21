package test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BallStress
extends BasicGame {
    Image background;
    Image ball;
    static AppGameContainer container;

    public BallStress() {
        super("sad");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BallStress());
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

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(this.background, 0.0f, 0.0f);
        int i = 0;
        while (i < 1000) {
            g.drawImage(this.ball, (float)(Math.random() * (double)container.getWidth()), (float)(Math.random() * (double)container.getHeight()));
            ++i;
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {
    }
}
