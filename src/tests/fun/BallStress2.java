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

public class BallStress2
extends BasicGame {
    Image background;
    Image ball;
    static AppGameContainer container;
    boolean changeColor = true;
    Image temp;
    Graphics g2;
    ArrayList<Point> list = new ArrayList();
    float r = 10.0f;
    float f = 0.0f;
    int i = 1;
    int t = 0;

    public BallStress2() {
        super("sad");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BallStress2());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(true);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.background = new Image(container.getWidth() / 2, container.getHeight());
        this.temp = this.background.copy();
        this.temp = new Image(container.getWidth(), container.getHeight());
        this.g2 = this.temp.getGraphics();
        this.g2.setAntiAlias(true);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setAntiAlias(true);
        g.setColor(Color.white);
        g.drawString(String.valueOf(this.list.size()), 10.0f, 20.0f);
        if (this.list.size() < 500) {
            this.list.add(new Point((int)(Math.random() * (double)container.getWidth()), (int)(Math.random() * (double)container.getHeight())));
        }
        int i = 0;
        while (i < this.list.size()) {
            Point p = this.list.get(i);
            if (this.changeColor) {
                this.g2.setColor(new Color((int)(Math.random() * 265.0), (int)(Math.random() * 265.0), (int)(Math.random() * 265.0)));
            }
            this.g2.fillOval((int)p.getX(), (int)p.getY(), this.r, this.r);
            ++i;
        }
        this.g2.flush();
        this.f = (float)((double)this.f + 0.1);
        this.temp.rotate(this.f);
        this.temp.draw();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(57)) {
            this.changeColor = !this.changeColor;
        }
    }
}
