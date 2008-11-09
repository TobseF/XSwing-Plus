/*
 * @version 0.0 06.06.2008
 * @author 	Tobse F
 */
package tests;

import java.util.List;



import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class MenuTest extends BasicGame {
	   private Menu menu;

	   public MenuTest () {
	      super("MenuTest");
	   }

	   public void init (GameContainer container) throws SlickException {
	      menu = new Menu(new AngelCodeFont("res/font.fnt", "res/font.tga"), container.getInput(), null);
	      menu.setBounds(100, 40, 300, 400);

	      List<String> entries = menu.getEntries();
	      for (int i = 1; i <= 10; i++)
	         entries.add("Entry" + i);
	      menu.setWrapAround(true);
	      menu.setGradientHeight(100);
	   }

	   public void render (GameContainer container, Graphics g) throws SlickException {
	      menu.render(g);
	      g.setColor(Color.white);
	      g.drawRect(100, 40, 300, 400);
	   }

	   public void update (GameContainer container, int delta) throws SlickException {
	      menu.update(delta);
	   }

	   public static void main (String[] args) throws Exception {
	      /*CanvasGameContainer container = new CanvasGameContainer(new MenuTest());
	      container.setSize(640, 480);
	      JFrame frame = new JFrame("Menu");
	      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      frame.add(container);
	      frame.pack();
	      frame.setVisible(true);*/
	      //container.
		   AppGameContainer con=new AppGameContainer(new MenuTest());
		   con.setDisplayMode(640, 480,false);
		   con.start();
	   }
	}
