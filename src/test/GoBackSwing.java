/*
 * @version 0.0 14.04.2008
 * @author 	Tobse F
 */
package test;

import org.newdawn.slick.*;

public class GoBackSwing extends BasicGame{
	static AppGameContainer container;
	Image background,ball;
	
	AngelCodeFont font;
	String time="sdsd",t="";
	int rasterX=284,rasterY=232;
	int canonX=248,canonY=166;
	//BigImage back;
	int caPos=0;
	
	public GoBackSwing() {
		super("XSwing");
	}
	static long ttime;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			container = new AppGameContainer(new GoBackSwing());
			container.setMinimumLogicUpdateInterval(20);
			container.setDisplayMode(1024,768,true);
			container.setClearEachFrame(false);
			//container.setIcon("res/ball.png");
			container.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		background=new Image("res/swing_background_b.jpg");
		ball=new Image("res/ball.png");
		font = new AngelCodeFont("res/font2.fnt","res/font2.png");
		ttime=container.getTime();
	}
	
	long timeS;
	int gap=16; //Gap between balls
	int ba=48; //Ball lenght
	
	int ups=0;
	@Override
	public void update(GameContainer container, int delta)throws SlickException {
		timeS=((container.getTime()-ttime))/1000;
		Input in=container.getInput();
		if(ups%1==0){
			if(in.isKeyPressed(Input.KEY_LEFT)){
				if(caPos>=1)caPos--;
			}
			
			if(in.isKeyPressed(Input.KEY_RIGHT)){
				if(caPos<7)caPos++;
			}
		}
		if(in.isKeyDown(Input.KEY_ESCAPE)){
			container.exit();
		}
		ups++;
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		g.drawImage(background,0,0);
		renderTimer();
		g.drawImage(ball,canonX+caPos*(ba+gap),canonY);
	}
	
	public void renderTimer(){
		String s=String.format("%02d",(int)timeS%60);
		String m=String.format("%02d",(int)(timeS/60)%60);
		String h=String.format("%02d",(int)((timeS/60)/60)%60);
		font.drawString(115,717,h+":"+m+":"+s);
	}

}
