/*
 * @version 0.0 14.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.util.ArrayList;
import java.util.List;

import lib.SObject;
import lib.Sound;
import lib.SpriteSheet;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.loading.LoadingList;

/** The main game class, which combines all game elements */
public class XSwing extends BasicGame{ 
	private static AppGameContainer game=null;
	
	String res="res/";
	Image background,ball;
	SpriteSheet balls1;
	Cannon canon;
	List<Ball> ballsToMove=new ArrayList<Ball>();
	List<SObject>gui=new ArrayList<SObject>();
	LoadingScreen loading;
	Clock timer;
	BallTable ballTable;
	Mechanics mechanics;
	WeightTable weightTable;
	BallCounter ballCounter;
	HighScoreCounter scoreCounter;
	HighScoreMultiplicator multiplicator;
	BallKiller ballKiller;
	Level levelBall;
	Reset reset;
	
	Effects effects;
	public Sound klack1,kran1,wup,shrinc,warning;
	private Music music;
	SpriteSheet ballImages,multipl,cannons;
	public SpriteSheetFont font,ballFont;
	
	
	int rasterX=248,rasterY=289;
	int canonX=248,canonY=166;
	
	boolean particles=true;

	public XSwing() {
		super("XSwing");
	}
	
	public static void main(String[] args) {
		boolean fullsceen=true;
		//Log.setVerbose(false); //no debug infos
		try {
			game = new AppGameContainer(new XSwing());
			game.setMinimumLogicUpdateInterval(20);
			game.setMaximumLogicUpdateInterval(20);
			game.setDisplayMode(1024,768,fullsceen);
			game.setClearEachFrame(false);			
			game.setIcons(new String[]{"res/16.png","res/32.png"});
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		loading=new LoadingScreen();
		LoadingList.setDeferredLoading(true);
		//Images
		background=new Image(res+"swing_background_b.jpg");
		multipl=new SpriteSheet(new Image(res+"multiplicator_sp.jpg"),189,72);
		cannons=new SpriteSheet(new Image(res+"cannons.png"),72,110);
		balls1=new SpriteSheet(new Image(res+"Balls1.png"),Ball.A,Ball.A);
		ball=new Image(res+"ball.png");
		font=new SpriteSheetFont(new SpriteSheet(new Image("res/NumerFont_s19.png"),15,19),'.');
		ballFont=new SpriteSheetFont(new SpriteSheet(new Image("res/spriteFontBalls2.png"),11,16),'.');
		ballImages=new SpriteSheet(balls1,Ball.A,Ball.A);
		//XML
		effects=new Effects();
		//Sound
		klack1=new Sound(res+"klack4.wav");
		kran1=new Sound(res+"kran1.wav");
		wup=new Sound(res+"dreier.wav");
		wup.setMaxPlyingTime(1000);
		shrinc=new Sound(res+"spratz2.wav");
		warning=new Sound(res+"ALARM1.wav");
		music=new Music("res/MOD.X-OCEANS GO EDITION.mod");
		
		//Objects
		reset =new Reset();
		ballTable=new BallTable(rasterX,rasterY);
		mechanics=new Mechanics(ballTable);
		timer=new Clock(font,85,718);
		canon=new Cannon(cannons,canonX,canonY,new Sound[]{kran1,warning});
		canon.setBallTable(ballTable);
		multiplicator=new HighScoreMultiplicator(59,93,multipl);
		scoreCounter=new HighScoreCounter(font,970,106,multiplicator);
		weightTable=new WeightTable(font,ballTable);
		weightTable.setPos(285, 723);
		levelBall=new Level(3,25,15,balls1);
		levelBall.setFont(ballFont);
		ballCounter=new BallCounter(ballFont,160,22);
		ballCounter.setLevel(levelBall);
		ballKiller=new BallKiller(mechanics,scoreCounter);
		effects.setSound(wup,Effects.effectDisappearing);
		
		reset.add(timer);
		reset.add(ballCounter);
		reset.add(levelBall);
		reset.add(scoreCounter);
		reset.add(ballTable);
		reset.add(effects);
		reset.add(multiplicator);
		
		gui.add(canon);
		gui.add(timer);
		gui.add(weightTable);
		gui.add(levelBall);
		gui.add(ballCounter);
		gui.add(scoreCounter);
		gui.add(multiplicator);
		
		newGame();
	}
	
	@Override
	public void update(GameContainer container, int delta)throws SlickException {
		if(!loading.isFinish())
			loading.update();
		else{
			Input in=container.getInput();
			checkKeys(in);
			if(delta>0){
				if(!music.playing())
				music.loop();
				timer.tick();
				canon.update(delta);
				
				weightTable.update();
				if(particles)
					effects.update(delta);
				mechanics.checkOfFive();
				mechanics.checkOfThree();
				ballKiller.update(delta);
				updateBalls();
				multiplicator.update(delta);
			}
		}
	}
	
	public void addNewBall() {
		Ball ball=getNewBall(canon.getX(),canon.getY());
		canon.releaseBall(ball);
		effects.addEffect(canon.getBall(),Effects.efectFlash);
		ballCounter.count();
	}
	
	public Ball getNewBall(int x, int y){
		Ball ball=new Ball(levelBall.getLevel(),x,y,balls1);
		ball.setGrid(ballTable);
		ball.setFont(ballFont);
		ball.setCollsionSound(klack1);
		ball.setEffects(effects);
		ballsToMove.add(ball);
		return ball;
	}
	
	public void addNewJoker() {
		ExtraJoker ball=new ExtraJoker(33,canon.getX(),canon.getY());
		ball.setGrid(ballTable);
		ball.setCollsionSound(klack1);
		ballsToMove.remove(canon.getBall());
		ballsToMove.add(ball);
		canon.setBall(ball);
		effects.addEffect(canon.getBall(),Effects.efectFlash);
		ballCounter.count();
	}
	
	public void addTopBalls(){
		for(int row=12;row>10;row--){
			for(int column=0;column<8;column++){
				int[]pos=ballTable.getFieldPosOnScreen(column,row);
				Ball newBall=getNewBall(pos[0],pos[1]);		
				ballTable.setBall(column,row, newBall);
			}
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)throws SlickException {
		if(!loading.isFinish())
			loading.draw(g);
		else{
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
	}
	
	private void checkKeys(Input in){
		
		if(in.isKeyDown(Input.KEY_ESCAPE)){
			game.exit();
		}
		if(in.isKeyPressed(Input.KEY_P)){
			game.setPaused(!game.isPaused());
		}
		if(in.isKeyPressed(Input.KEY_N)){
			newGame();
		}
		if(!game.isPaused()){
			if(in.isKeyPressed(Input.KEY_LEFT)){
				canon.moveLeft();
			}
			if(in.isKeyPressed(Input.KEY_RIGHT)){
				canon.moveRight();
			}
			if(in.isKeyPressed(Input.KEY_DOWN)){
				addNewBall();
			}
			if(in.isKeyPressed(Input.KEY_J)){
				addNewJoker();
			}
			if(in.isKeyPressed(Input.KEY_E)){
				particles=!particles;
			}
		}
	}
	
	public void newGame(){
		reset.reset();
		ballsToMove.clear();
		addTopBalls();
		game.setPaused(false);
	}

	private void updateBalls(){
		for(int i=0;i<ballsToMove.size();i++){
			Ball b=ballsToMove.get(i);
			if(b.isReadyToKill()){
				
				if(b.getReadyToKill()==Ball.WAITING_FOR_KILL){;
					ballKiller.addBall(b);
					effects.addEffect(b,Effects.effectDisappearing);
					b.kill(Ball.KILLING_STARTED);
				}
				if(b.getReadyToKill()==Ball.WAITING_FOR_SHRINK){
					shrinc.play();
					b.kill(Ball.KILL_IMMEDIATELY);
				}
				if(b.getReadyToKill()==Ball.KILL_IMMEDIATELY){
					effects.addEffect(b,Effects.effectDisappearing);
					int[] field=ballTable.getField(b);;
					ballTable.setBall(field[0],field[1],null);
					ballsToMove.remove(i);
				}
			}
			else
				b.update();
		}
	}

}
