package tests;

import java.awt.*;
import java.awt.event.*;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

/**
 * Quick test to confirm canvas size is reported correctly
 * 
 * @author kevin
 */
public class CanvasSizeTest extends BasicGame {

	/**
	 * Create test
	 */
	public CanvasSizeTest() {
		super("Test");
	}

	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void init(GameContainer container) throws SlickException {
		System.out.println(container.getWidth() + ", " + container.getHeight());
	}

	/**
	 * @see org.newdawn.slick.Game#render(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.drawString("sd", 23, 23);
		g.fillOval(100, 100, 200, 200);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {}

	/**
	 * Entry point to the test
	 * 
	 * @param args The command line arguments passed in (none honoured)
	 */
	public static void main(String[] args) {
		try {
			CanvasGameContainer container = new CanvasGameContainer(new CanvasSizeTest());
			container.setSize(640, 480);
			Frame frame = new Frame("Test");
			frame.setLayout(new GridLayout(1, 2));
			frame.add(container);
			frame.pack();
			frame.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			frame.setVisible(true);

			container.start();
		} catch (Exception e) {
			Log.error(e);
		}
	}
}
