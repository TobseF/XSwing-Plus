/*
 * @version 0.0 19.12.2008
 * @author Tobse F
 */
package tests;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lib.mylib.math.MyMath;

public class MathTestter extends JFrame implements MouseMotionListener, KeyListener {

	private int x1 = 150, y1 = 150;
	private double degree = 12;

	public MathTestter() {
		pP = new double[2];
		setSize(300, 300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBackground(Color.white);
		addMouseMotionListener(this);
		addKeyListener(this);
		setVisible(true);
		System.out.println(MyMath.getDegree(0, 0, 1, 1));
		System.out.println(MyMath.getDegree(0, 0, 0, 0));
		System.out.println(MyMath.getDegree(0, 0, 1, 0));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.fillOval(x1, y1, 5, 5);
		g.drawString("d:" + degree + "", 20, 80);
		g.drawString("Press Left or Right", 20, 110);
		System.out.println(degree);

		g.setColor(Color.red);
		int[] posP = MyMath.translateToCenter(x1, y1, (int) (pP[0]), (int) (pP[1]));
		g.fillOval(posP[0], posP[1], 5, 5);
	}

	public static void main(String[] args) {
		new MathTestter();
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		degree = MyMath.getDegree(x1, y1, e.getX(), e.getY());
		repaint();
	}

	double pointDegree = 0;
	double rP = 90;
	double[] pP;

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			pointDegree += 5;
		} else {
			pointDegree -= 5;
		}
		pP = MyMath.rotate(0, rP, pointDegree);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
