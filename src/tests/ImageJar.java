/*
 * @version 0.0 14.04.2008
 * @author 	Tobse F
 */
package tests;

import org.newdawn.slick.*;

public class ImageJar extends BasicGame{
	static AppGameContainer container;
	Image background;
	
	public ImageJar() {
		super("JarTest");
	}
	static long ttime;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			container = new AppGameContainer(new ImageJar());
			container.setMinimumLogicUpdateInterval(20);
			container.setDisplayMode(640,500,false);
			container.setClearEachFrame(false);
			container.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		//background=new Image("res/swing_background.jpg");
		background=new Image(Thread.currentThread().getContextClassLoader().getResource("res/swing_background.jpg").toString());
	}
	
	@Override
	public void update(GameContainer container, int delta)throws SlickException {
		

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.drawImage(background,0,0);
	}

}
