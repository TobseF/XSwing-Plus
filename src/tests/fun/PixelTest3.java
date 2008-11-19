/*
 * @version 0.0 30.10.2008
 * @author Tobse F
 */
package tests.fun;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;

public class PixelTest3 extends BasicGame {

	List<Point2D> punkte = new ArrayList<Point2D>();
	double a = 0.9, b = 1, massStab = 100;
	static int height = 500, width = 500;
	int centerX, centerY;

	public PixelTest3(String title) {
		super(title);
	}

	public void calculatePoints() {
		double max = 2 * Math.PI, step = 0.0001;
		punkte.clear();
		for (double i = 0; i <= max; i = i + step) {
			int pX = (int) getX(i);
			int pY = (int) getY(i);
			// System.out.println(pX+" "+pY);
			punkte.add(new Point(pX, pY));
		}
		System.out.println("Points: " + punkte.size());

	}

	private ImageBuffer buffer;
	private List<Image> imgage = new ArrayList<Image>();

	double colorstep = 265. / 200.;

	private void createBuffer() {
		int centerX = height / 2, centerY = width / 2;
		buffer = new ImageBuffer(width, height);
		for (int i = punkte.size() - 1; i > 2; i--) {
			int x = (int) punkte.get(i).getX() + centerX;
			int y = (int) punkte.get(i).getY() + centerY;
			buffer.setRGBA(x, y, 255, 100, 255, 255);
			// System.out.println(x+" "+y);
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
			game.setDisplayMode(width, height, false);
			game.start();
		} catch (SlickException e) {}

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

	int j = 0;

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		System.out.println("j" + j);
		imgage.get(j).draw();
	}

}
