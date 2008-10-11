package tests.fun;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BallStress4
extends BasicGame {
    static AppGameContainer container;
    static Frame frame;
    boolean changeColor = true;
    Image temp1;
    Image temp2;
    Image point;
    Graphics g2;
    Graphics g3;
    Graphics g4;
    boolean mouse;
    static Dimension size;

    public BallStress4() {
        super("sad");
    }

    public static void main(String[] args) {
        try {
            CanvasGameContainer container = new CanvasGameContainer(new BallStress4());
            container.setSize(640, 480);
            frame = new Frame("Test");
            frame.setLayout(new GridLayout(1, 2));
            frame.add(container);
            frame.pack();
            size = frame.getSize();
            frame.addWindowListener(new WindowAdapter(){

                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            frame.setVisible(true);
            frame.requestFocus();
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
        this.point = this.temp2.copy();
        this.g4 = this.point.getGraphics();
        this.g4.setAntiAlias(true);
        this.g3.setColor(Color.black);
        System.out.println("init");
        frame.setLocation(0, 0);
        frame.repaint();
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        if (!frame.getSize().equals(size)) {
            size = frame.getSize();
            this.init(container);
        }
        Point p1 = new Point((int)(Math.random() * (double)container.getWidth()), (int)(Math.random() * (double)container.getHeight()));
        Point p2 = new Point((int)(Math.random() * (double)container.getWidth()), (int)(Math.random() * (double)container.getHeight()));
        if (this.changeColor) {
            this.g2.setColor(new Color((int)(Math.random() * 265.0), (int)(Math.random() * 265.0), (int)(Math.random() * 265.0)));
        }
        this.g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        this.g2.setColor(Color.black);
        int r = 60;
        if (this.mouse) {
            this.g2.fillOval(container.getInput().getMouseX() - r / 2, container.getInput().getMouseY() - r / 2, r, r);
        }
        this.g4.fillOval(container.getWidth() / 2, container.getWidth() / 2, r, r);
        this.g4.flush();
        this.point.rotate(0.15f);
        this.g2.drawImage(this.point, 0.0f, 0.0f);
        this.g2.flush();
        this.g3.flush();
        this.temp1.draw();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(57)) {
            this.changeColor = !this.changeColor;
        }
        this.mouse = container.getInput().isMouseButtonDown(0);
    }
}
