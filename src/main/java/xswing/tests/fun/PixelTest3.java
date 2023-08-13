/*
 * @version 0.0 30.10.2008
 * @author Tobse F
 */
package xswing.tests.fun;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class PixelTest3 extends BasicGame {

    private final List<Point2D> punkte = new ArrayList<Point2D>();
    private final double a = 0.9;
    private final double massStab = 100;
    private int b = 1, j = 0;

    private static final int height = 500;
    private static final int width = 500;

    private ImageBuffer buffer;
    private final List<Image> imgage = new ArrayList<Image>();

    public PixelTest3(String title) {
        super(title);
    }

    public void calculatePoints() {
        double max = 2 * Math.PI, step = 0.0001;
        punkte.clear();
        for (double i = 0; i <= max; i = i + step) {
            int pX = (int) getX(i);
            int pY = (int) getY(i);
            punkte.add(new Point(pX, pY));
        }
        System.out.println("Points: " + punkte.size());
    }

    private void createBuffer() {
        int centerX = height / 2, centerY = width / 2;
        buffer = new ImageBuffer(width, height);
        for (int i = punkte.size() - 1; i > 2; i--) {
            int x = (int) punkte.get(i).getX() + centerX;
            int y = (int) punkte.get(i).getY() + centerY;
            buffer.setRGBA(x, y, 255, 100, 255, 255);
        }
        System.out.println(imgage.size());
        imgage.add(buffer.getImage());
    }

    private double getX(double x) {
        return (Math.sin(x) + a * Math.sin(b * x)) * massStab;
    }

    private double getY(double x) {
        return (Math.cos(x) + a * Math.cos(b * x)) * massStab;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            AppGameContainer game = new AppGameContainer(new PixelTest3("PixelTest3"));
            game.setMaximumLogicUpdateInterval(25);
            game.setDisplayMode(width, height, false);
            game.start();
        } catch (SlickException e) {
        }

    }

    float rotation = 0;

    @Override
    public void init(GameContainer container) throws SlickException {

        calculatePoints();
        createBuffer();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (j < imgage.size() - 1) {
            j++;
        } else {
            j = 0;
        }

        if (b < 200) {
            calculatePoints();
            createBuffer();
            b++;
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        imgage.get(j).draw();
        if (b < 200) {
            g.drawString("Calculating Animation: frame " + b + " of 200", 10, 25);
        } else {
            g.drawString("playing", 10, 25);
        }
    }

}