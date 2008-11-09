/*
 * @version 0.0 13.06.2008
 * @author 	Tobse F
 */
package tests;

import org.newdawn.slick.*;

public class MeineKlasse  extends BasicGame{
	Image testBild;

	public MeineKlasse(String title) {
		super(title);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new MeineKlasse("MeinSpiel-Fenstername"));
			container.setMinimumLogicUpdateInterval(20);
			container.setDisplayMode(800,600,false);
			container.setClearEachFrame(false);
			container.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		testBild=new Image("pfad/bild.jpg");
		
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		testBild.draw();
	}

}
