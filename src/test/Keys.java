/*
 * @version 0.0 14.04.2008
 * @author 	Tobse F
 */
package test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Keys extends BasicGame{
	Image background,ball;
	static AppGameContainer container;
	public Keys() {
		super("sad");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			container = new AppGameContainer(new Keys());
			container.setDisplayMode(640,480,false);
			container.setClearEachFrame(false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		background=new Image("res/swing_background.jpg");
		ball=new Image("res/ball.png");
	}
	int x,y;
	int delatTemp;
	@Override
	public void update(GameContainer container, int delta)throws SlickException {
		Input in=container.getInput();
		System.out.println(delta);
		int l=50;
		if(delatTemp>70){
			if(in.isKeyDown(Input.KEY_LEFT)){
				x-=l;
				ball.rotate(-angle);
			}
			
			if(in.isKeyDown(Input.KEY_RIGHT)){
				x+=l;
				ball.rotate(angle);
			}
			delatTemp=0;
		}else delatTemp+=delta;
		
	}

	float angle=20f; 
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.drawImage(background,0,0);
		//ball.drawFlash(x, y);
		g.drawImage(ball,x,y);
	}

}
