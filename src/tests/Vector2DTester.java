/*
 * @version 0.0 19.12.2008
 * @author 	Tobse F
 */
package tests;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import lib.mylib.math.Vector2D;

public class Vector2DTester extends JFrame{
	public Vector2DTester() {
		Vector2D vector2D = new Vector2D(2,1);
	//vector2D.rotate(90);
		System.out.println(vector2D.x + " " + vector2D.y);
	
		setSize(300, 300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		final int x =(int)vector2D.getX(), y = (int)vector2D.getY();
		add(new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawLine(150, 150,x, y);
			}
		});
		setVisible(true);
	}
	public static void main(String[] args) {
		new Vector2DTester();
	}

}
