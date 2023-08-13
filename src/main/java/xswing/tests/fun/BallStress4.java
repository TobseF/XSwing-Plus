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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BallStress4 extends BasicGame {

    static AppGameContainer container;
    static Frame frame;
    boolean changeColor = true;
    Image temp1, temp2, point;
    Graphics g2, g3, g4;

    public BallStress4() {
        super("sad");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            /*
             * container = new AppGameContainer(new BallStress4());
             * container.setDisplayMode(640,480,false); container.setClearEachFrame(false);
             * container.start();
             */

            CanvasGameContainer container = new CanvasGameContainer(new BallStress4());
            container.setSize(640, 480);
            frame = new Frame("Test");
            frame.setLayout(new GridLayout(1, 2));
            frame.add(container);
            frame.pack();
            size = frame.getSize();
            frame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }

            });
            frame.setVisible(true);
            frame.requestFocus();

            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        temp1 = new Image(container.getWidth(), container.getHeight());
        temp2 = temp1.copy();
        g2 = temp1.getGraphics();
        g2.setAntiAlias(true);
        g3 = temp2.getGraphics();
        g3.setAntiAlias(true);

        point = temp2.copy();
        g4 = point.getGraphics();
        g4.setAntiAlias(true);
        g3.setColor(Color.black);
        System.out.println("init");
        frame.setLocation(0, 0);
        frame.repaint();
    }

    boolean mouse;
    static Dimension size;

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {

        if (!frame.getSize().equals(size)) {
            size = frame.getSize();
            init(container);
        }
        Point p1 = new Point((int) (Math.random() * container.getWidth()), (int) (Math
                .random() * container.getHeight()));
        Point p2 = new Point((int) (Math.random() * container.getWidth()), (int) (Math
                .random() * container.getHeight()));
        if (changeColor) {
            g2.setColor(new Color((int) (Math.random() * 265), (int) (Math.random() * 265),
                    (int) (Math.random() * 265)));
        }
        g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        g2.setColor(Color.black);
        int r = 60;
        if (mouse) {
            g2.fillOval(container.getInput().getMouseX() - r / 2, container.getInput()
                    .getMouseY()
                    - r / 2, r, r);
        }

        g4.fillOval(container.getWidth() / 2, container.getWidth() / 2, r, r);
        g4.flush();
        point.rotate(0.15f);
        g2.drawImage(point, 0, 0);

        g2.flush();
        g3.flush();

        temp1.draw();

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
            changeColor = !changeColor;
        }
        mouse = container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
    }

}
