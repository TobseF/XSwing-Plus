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
	String res="res/";
	Image background,ball,balls1,tuxcanon;
	Cannon canon;
	List<Ball> ballsToMove=new ArrayList<Ball>();
	List<SObject>gui=new ArrayList<SObject>();
	Timer timer;
	BallTable ballTable;
	Mechanics mechanics;
	WeightTable weightTable;
	BallCounter ballCounter;
	HighScoreCounter scoreCounter;
	Multiplicator multiplicator;
	Level levelBall;
	
	Effects effects;
	Sound klack1,kran1,wup,music,shrinc;
	
	Image normImage;
	SpriteSheet ballImages,multipl;
	
	AngelCodeFont font,ballFont;
	
	int rasterX=248,rasterY=289;
	int canonX=248,canonY=166;
	
	boolean particles=true;

	public XSwing() {
		super("XSwing");
	}
	
	public static void main(String[] args) {
		boolean fullsceen=true;
		try {
			game = new AppGameContainer(new XSwing());
			game.setMinimumLogicUpdateInterval(20);
			game.setDisplayMode(1024,768,fullsceen);
			game.setClearEachFrame(false);			
			game.setIcon("res/32.png");
			game.start();	
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		//Images
		background=new Image(res+"swing_background_b.jpg");
		multipl=new SpriteSheet(new Image(res+"multiplicator_sp.jpg"),189,72);
		tuxcanon=new Image(res+"tuxcanon.png");
		balls1=new Image(res+"Balls1.png");
		ball=new Image(res+"ball.png");
		font = new AngelCodeFont(res+"font2.fnt","res/font2.png");
		ballFont=new AngelCodeFont(res+"BallFont3.fnt","res/BallFont3.png");	
		ballImages=new SpriteSheet(balls1,48,48);
		//XML
		effects=new Effects();
		//Sound
		klack1=new Sound(res+"klack4.wav");
		kran1=new Sound(res+"kran1.wav");
		wup=new Sound(res+"wupp.wav");
		shrinc=new Sound(res+"spratz2.WAV");
		Sound music=new Sound("res/music.ogg");
		
		//Objects
		ballTable=new BallTable(rasterX,rasterY);
		mechanics=new Mechanics(ballTable);
		timer=new Timer(font,115,717);
		timer.start(game);
		canon=new Cannon(tuxcanon,canonX,canonY);
		canon.setBallTable(ballTable);
		multiplicator=new Multiplicator(59,92,multipl);
		scoreCounter=new HighScoreCounter(font,970,105,multiplicator);
		mechanics.setScore(scoreCounter);
		weightTable=new WeightTable(font,ballTable);
		weightTable.setPos(285, 722);
		levelBall=new Level(3,25,15,this);
		ballCounter=new BallCounter(font,160,22);
		ballCounter.setLevel(levelBall);
		
		gui.add(canon);
		gui.add(timer);
		gui.add(weightTable);
		gui.add(levelBall);
		gui.add(ballCounter);
		gui.add(scoreCounter);
		gui.add(multiplicator);
		
		addTopBalls();
		ballTable.printBallTable();
		
		music.loop();
	}
	
	@Override
	public void update(GameContainer container, int delta)throws SlickException {
		timer.tick(container);
		Input in=container.getInput();
		weightTable.update();
		if(particles)
			effects.update(delta);
		mechanics.checkOfFive();
		mechanics.checkOfThree();
		updateBalls();
		checkKeys(in,container);
		multiplicator.update(delta);
	}
	
	public void addNewBall() {
		Ball ball=new Ball(levelBall.getLevel(),canon.getX(),canon.getY(),this);
		ball.setGrid(ballTable);
		ball.setSwing(this);
		canon.releaseBall(ball);
		ballsToMove.add(ball);
		effects.addEffect(canon.getBall(),Effects.efectFlash);
		ballCounter.count();
	}
	
	public void addTopBalls(){
		for(int row=12;row>10;row--){
			for(int column=0;column<8;column++){
				int[]pos=ballTable.getFieldPosOnScreen(column,row);
				Ball newBall=new Ball(levelBall.getLevel(),pos[0],pos[1],this);
				newBall.setGrid(ballTable);
				newBall.setSwing(this);
				ballTable.setBall(column,row, newBall);
				ballsToMove.add(newBall);
			}
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)throws SlickException {
		g.drawImage(background,0,0);
		for(int i=0;i<gui.size();i++){
			gui.get(i).draw(g);
		}
		for(int i=0;i<ballsToMove.size();i++){
			ballsToMove.get(i).draw(g);
		}
		if(particles)
			effects.draw(g);
	}
	
	private void checkKeys(Input in,GameContainer container){
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
			addTopBalls();
		}
		if(in.isKeyPressed(Input.KEY_P)){
			particles=!particles;
		}
		
		if(in.isKeyDown(Input.KEY_ESCAPE)){
			container.exit();
		}
	}
	
	private void updateBalls(){
		for(int i=0;i<ballsToMove.size();i++){
			Ball b=ballsToMove.get(i);
			if(b.getReadyToKill()>0){
				effects.addEffect(b,Effects.effectDisappearing);
				int[] field=ballTable.getField(b);;
				ballTable.setBall(field[0],field[1],null);
				ballsToMove.remove(i);
				if(b.getReadyToKill()==1)
					wup.play();
				if(b.getReadyToKill()==2)
					shrinc.play();
			}
			else
				b.update();
		}
	}

}
