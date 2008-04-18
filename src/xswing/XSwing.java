/*
 * @version 0.0 14.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.util.*;
import lib.SpriteSheet;
import org.newdawn.slick.*;

public class XSwing extends BasicGame{ 
	static AppGameContainer game=null;
	Image background,ball,balls1,tuxcanon;
	Cannon canon;
	List<Ball> ballsToMove =new ArrayList<Ball>();
	Timer timer;
	BallTable ballTable;
	Mechanics mechanics;
	
	Effects effects;
	Sound klack1,kran1,wup,music;
	
	Image normImage;
	SpriteSheet ballImages;
	
	AngelCodeFont font,ballFont;
	
	int rasterX=248,rasterY=289;
	int canonX=248,canonY=166;
	
	boolean particles=true;

	public XSwing() {
		super("XSwing");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean fullsceen=true;
		try {
			game = new AppGameContainer(new XSwing());
			game.setMinimumLogicUpdateInterval(20);
			game.setDisplayMode(1024,768,fullsceen);
			game.setClearEachFrame(false);
			//container.setIcon("res/ball.png");
			game.start();
			
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		//Images
		background=new Image("res/swing_background_b.jpg");
		tuxcanon=new Image("res/tuxcanon.png");
		balls1=new Image("res/Balls1.png");
		ball=new Image("res/ball.png");
		canon=new Cannon(tuxcanon,canonX,canonY);
		font = new AngelCodeFont("res/font2.fnt","res/font2.png");
		ballFont=new AngelCodeFont("res/BallFont3.fnt","res/BallFont3.png");	
		
		//XML
		effects=new Effects();
		//Sound
		klack1=new Sound("res/klack4.wav");
		kran1=new Sound("res/kran1.wav");
		wup=new Sound("res/wupp.wav");
		//Objects
		ballTable=new BallTable(rasterX,rasterY);
		mechanics=new Mechanics(ballTable);
		timer=new Timer(font,115,717);
		timer.start(game);
		//normImage=new StandartBallMaker().getImage();
		ballImages=new SpriteSheet(balls1,48,48);
	}
	
	@Override
	public void update(GameContainer container, int delta)throws SlickException {
		timer.tick(container);
		Input in=container.getInput();
		if(particles)
			effects.update(delta);
		mechanics.checkOfThree();
			if(in.isKeyPressed(Input.KEY_LEFT)){
				if(canon.getPos()!=0)
					kran1.play();
				canon.moveLeft();
			}
			
			if(in.isKeyPressed(Input.KEY_RIGHT)){
				if(canon.getPos()!=7)
					kran1.play();
				canon.moveRight();
			}
			
			if(in.isKeyPressed(Input.KEY_DOWN)){
				addNewBall();
			}
			
			if(in.isKeyPressed(Input.KEY_N)){
				ballTable.clear();
				ballsToMove.clear();
				effects.reset();
			}
			if(in.isKeyPressed(Input.KEY_P)){
				particles=!particles;
			}
			
			for(int i=0;i<ballsToMove.size();i++){
				Ball b=ballsToMove.get(i);
				if(b.isReadyToKill()){
					effects.addEffect(b,Effects.effectDisaperaing);
					int[] field=ballTable.getField(b);;
					ballTable.setBall(field[0],field[1],null);
					ballsToMove.remove(i);
					wup.play();
				}
				else
					b.update();
			}

		if(in.isKeyDown(Input.KEY_ESCAPE)){
			container.exit();
		}

	}
	
	/** Revomes a specific ball
	 * @param x Position int the BallTable
	 * @param y Position int the BallTable
	 */
	public void removeBall(int x,int y){
	}
	
	public void addNewBall() {
		int b=(int)(Math.random()*4);
		canon.releaseBall();
		Ball ball=new Ball(b,canon.getX(),canon.getY(),this);
		ball.setGrid(ballTable);
		ball.setSwing(this);
		ballsToMove.add(ball);
		canon.setBall(ball);
		effects.addEffect(ball,Effects.efectFlash);
	}
	
	@Override
	public void render(GameContainer container, Graphics g)throws SlickException {
		g.drawImage(background,0,0);
		timer.renderTimer();
		canon.draw(g);
		
		for(int i=0;i<ballsToMove.size();i++){
			ballsToMove.get(i).draw(g);
		}
		if(particles)
			effects.draw(g);
	}

}
