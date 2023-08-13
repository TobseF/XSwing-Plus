/*
 * @version 0.0 30.10.2008
 * @author Tobse F
 */
package xswing.tests.fun;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PixelTest1 extends BasicGame {

    List<Point2D> punkte = new ArrayList<Point2D>();
    double a = 1, b = 1, massStab = 100;
    static int height = 500, width = 500;

    public PixelTest1(String title) {
        super(title);
    }

    public void calculatePoints() {
        double max = 2 * Math.PI, step = 0.01;
        punkte.clear();
        for (double i = 0; i <= max; i = i + step) {
            int pX = (int) getX(i);
            int pY = (int) getY(i);
            System.out.println(pX + " " + pY);
            punkte.add(new Point(pX, pY));
        }
        System.out.println("Points: " + punkte.size());
    }

    BufferedImage img;

    private void drawMaple(Graphics g) {
        int centerX = height / 2, centerY = width / 2;

        for (int i = punkte.size() - 1; i > 2; i--) {
            int x = (int) punkte.get(i).getX() + centerX;
            int y = (int) punkte.get(i).getY() + centerY;
            g.fillOval(x, y, 1, 1);
        }

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
            AppGameContainer game = new AppGameContainer(new PixelTest1("PixelTest1"));
            game.setDisplayMode(width, height, false);
            game.setClearEachFrame(false);
            game.start();
        } catch (SlickException e) {
        }

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        calculatePoints();

    }

    BufferedImage image;

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        b = b + 1;
        // b=100;
        calculatePoints();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.clear();
        drawMaple(g);

    }

}
