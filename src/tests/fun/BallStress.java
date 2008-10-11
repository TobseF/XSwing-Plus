package tests.fun;

import java.awt.Point;
import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BallStress
extends BasicGame {
    Image background;
    Image ball;
    static AppGameContainer container;
    Image temp;
    Graphics g2;
    ArrayList<Point> list = new ArrayList();
    float r = 100.0f;
    float f = 0.0f;
    int i = 1;
    int t = 0;

    public BallStress() {
        super("sad");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BallStress());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(true);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.background = new Image("res/swing_background.jpg");
        this.temp = this.background.copy();
        this.g2 = new Graphics(container.getWidth(), container.getHeight());
        this.g2 = this.temp.getGraphics();
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        g.setColor(Color.white);
        g.drawString(String.valueOf(this.list.size()), 10.0f, 20.0f);
        float f = this.f;
        this.f = f + 1.0f;
        g.rotate(container.getWidth() / 2, container.getHeight() / 2, f);
        this.temp.getGraphics().fillOval(100.0f, 100.0f, 100.0f, 100.0f);
        if (this.list.size() < 500) {
            this.list.add(new Point((int)(Math.random() * (double)container.getWidth()), (int)(Math.random() * (double)container.getHeight())));
        }
        int i = 0;
        while (i < this.list.size()) {
            Point p = this.list.get(i);
            this.temp.getGraphics().setColor(new Color((int)(Math.random() * 265.0), (int)(Math.random() * 265.0), (int)(Math.random() * 265.0)));
            if (this.r > 0.0f) {
                this.r = (float)((double)this.r - 1.0E-4);
                this.r = (float)((double)this.r - 1.0E-4);
                this.temp.getGraphics().fillOval((int)p.getX(), (int)p.getY(), this.r, this.r);
            }
            ++i;
        }
        float f2 = this.f;
        this.f = f2 + 1.0f;
        this.temp.getGraphics().rotate(container.getWidth() / 2, container.getHeight() / 2, f2);
        g.setColor(Color.white);
        g.fillOval(100.0f, 100.0f, 100.0f, 100.0f);
        this.temp.draw();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (this.t++ > 500) {
            this.t = 0;
        }
    }
}
