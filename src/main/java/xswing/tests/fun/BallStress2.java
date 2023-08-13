/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package xswing.tests.fun;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.*;

import java.awt.*;
import java.util.ArrayList;

public class BallStress2 extends BasicGame {

    Image background, ball;
    static AppGameContainer container;
    boolean changeColor = true;
    Image temp;
    Graphics g2;

    public BallStress2() {
        super("sad");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BallStress2());
            container.setDisplayMode(640, 480, false);
            container.setClearEachFrame(true);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        background = new Image(container.getWidth() / 2, container.getHeight());
        temp = background.copy();
        temp = new Image(container.getWidth(), container.getHeight());
        g2 = temp.getGraphics();
        g2.setAntiAlias(true);
    }

    ArrayList<Point> list = new ArrayList<Point>();

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        // g.fillOval((float)Math.random()*container.getWidth(),
        // (float)Math.random()*container.getHeight(), (float)Math.random()*10,
        // (float)Math.random()*10);
        g.setAntiAlias(true);
        g.setColor(Color.white);
        g.drawString(list.size() + "", 10, 20);
        if (list.size() < 500) {
            list.add(new Point((int) (Math.random() * container.getWidth()), (int) (Math
                    .random() * container.getHeight())));
        }
        for (int i = 0; i < list.size(); i++) {
            Point p = list.get(i);
            if (changeColor) {
                g2.setColor(new Color((int) (Math.random() * 265),
                        (int) (Math.random() * 265), (int) (Math.random() * 265)));
            }
            g2.fillOval((int) p.getX(), (int) p.getY(), r, r);
        }
        g2.flush();
        temp.rotate(f += 0.1);
        temp.draw();

    }

    float r = 10;
    float f = 0;
    int i = 1;
    int t = 0;

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
            changeColor = !changeColor;
        }

    }

}
