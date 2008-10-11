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

public class Stress3
extends BasicGame {
    Image temp1;
    Image temp2;
    static AppGameContainer container;
    boolean changeColor = true;
    Graphics g2;
    Graphics g3;
    ArrayList<Point> list = new ArrayList();
    float r = 10.0f;
    float f = 0.0f;
    int i = 1;
    int t = 0;

    public Stress3() {
        super("sad");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new Stress3());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(true);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.temp1 = new Image(container.getWidth(), container.getHeight());
        this.temp2 = this.temp1.copy();
        this.g2 = this.temp1.getGraphics();
        this.g2.setAntiAlias(true);
        this.g3 = this.temp2.getGraphics();
        this.g3.setAntiAlias(true);
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
        this.temp1.rotate(this.f);
        this.g3.drawImage(this.temp1, 0.0f, 0.0f);
        this.g3.flush();
        this.temp2.draw();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(57)) {
            this.changeColor = !this.changeColor;
        }
    }
}
