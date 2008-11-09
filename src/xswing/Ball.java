/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.mylib.SObject;
import lib.mylib.SpriteSheet;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;

/** The ball which moves on the screen. It can be stored in the BallTable*/
public class Ball extends SObject{
	protected Font font;
	
	/** The number of the ball (0-49)*/
	protected int nr=0;
	/** The weight of the ball*/
	protected int weight=0; 
	/** The effect number of the ball (1=hidden)*/
	private int effect=0;
	/** Pixels to move in one update step*/
	private int speed=20;
	/** If the balls has to to be moved in update*/
	private boolean moving=false;
	/** The directions in which the Ball can move*/
	public static final int UP = 0 ;
	/** The directions in which the Ball can move*/
	public static final int DOWN = 1 ;
	/** The directions in which the Ball can move*/
	public static final int LEFT = 2 ;
	/** The directions in which the Ball can move*/
	public static final int RIGHT = 3 ;
	/** The current moving type*/
	private int movingType=DOWN;
	
	private int readyToKill=-1;
	
	protected BallTable ballTable=null;
	private Sound collsionSound=null;
	private EffectCatalog effectCatalog=null;
	private SpriteSheet ballsSpriteSheet=null;
	
	public static final int ALIVE=0;
	public static final int WAITING_FOR_KILL=1;
	public static final int KILL_IMMEDIATELY=2;
	public static final int KILLING_STARTED=3;
	public static final int WAITING_FOR_SHRINK=4;
	public static final int A=48;
	
	public Ball(){
	}
	
	public Ball(int level, int x, int y,SpriteSheet balls){
		super(x,y);
		nr=(int)(Math.random()*level+1);
		weight=1+(int)(Math.random()*(level+1));
		this.ballsSpriteSheet=balls;
		if(balls!=null)
			setPic(ballsSpriteSheet.getSprite(nr));
	}
	
	public Ball(int nr, int wieght, int x, int y,SpriteSheet balls){
		super(x,y);
		this.nr=nr+1;
		this.weight=wieght;
		this.ballsSpriteSheet=balls;
		if(balls!=null)
			setPic(ballsSpriteSheet.getSprite(nr));
	}
	
	public Ball(int level, int x, int y){
		this(level,x,y,null);
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setCollsionSound(Sound collsionSound) {
		this.collsionSound = collsionSound;
	}
	
	public void setEffects(EffectCatalog effectCatalog) {
		this.effectCatalog = effectCatalog;
	}
	
	
	public void setBallsSpriteSheet(SpriteSheet ballsSpriteSheet) {
		this.ballsSpriteSheet = ballsSpriteSheet;
		setNr(nr);
	}
	
	public boolean isReadyToKill(){
		if(readyToKill>ALIVE)// TODO: Why not: 	return readyToKill==WAITING_FOR_KILL;
			return true;
		else
			return false;
	}
	
	public int getReadyToKill() {
		return readyToKill;
	}

	public void kill(int effectNr){
		readyToKill=effectNr;
	}
	
	public void setGrid(BallTable ballTable) {
		this.ballTable = ballTable;
	}

	
	/**	Returns the effect number of the ball */
	public int getEffect() {
		return effect;
	}
	
	/**	Returns the number of the ball */
	public int getNr() {
		return nr;
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		drawNumber(g);
	}
	
	/** Draws the weight number on the Ball */
	protected void drawNumber(Graphics g){	
		if(font!=null)
			font.drawString(x+A/2-font.getWidth(weight+"")/2-1, y+21, weight+"");
	}
	
	/**	Returns the weight of the ball */
	public int getWeight() {
		return weight;
	}
	
	/**	Returns wether the Ball is moving */
	public boolean isMoving() {
		return moving;
	}
	
	/** Changes the moving state and (if necessary) removes the ball from the BallTable*/
	public void toggleMoving() {
		moving=!moving;
		if(moving&&ballTable.isOverGrid(x, y)){ //when moving the ball is remomved from the BallTable
			ballTable.removeBall(this);
		}		
	}
	
	/** Sets the falling speed*/
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/** Checks wether the given ball has the same nr, 99 is the Joker*/
	public boolean compare(Ball ball){
		return getNr()==ball.getNr()||ball.getNr()==99;
	}
	
	/**Sets a nr to the ball -it also changes the icon*/
	public void setNr(int nr) {
		this.nr = nr;
		if(ballsSpriteSheet!=null)
			pic=ballsSpriteSheet.getSprite(nr);
	}
	
	/** Returns the current sped of the ball*/
	public int getSpeed() {
		return speed;
	}
	
	/** Returns the weight of the ball*/
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public void update(int delta) {
		if(moving){
			switch(movingType){
				case UP:
					break;
				case DOWN:
					y+=speed;
					if(checkCollison())
						collide();
					break;
				case LEFT:
					break;
				case RIGHT:
					break;
			}
		}else{
			if(!checkCollison())
				if(ballTable.isOverGrid(x,y))
						toggleMoving();
		}					
	}
	
	/** Checks wether ther's a ball or soild below
	 * @return ture if collides*/
	private boolean checkCollison(){
		boolean collision=false;
		if(ballTable.isOverGrid(x, y)){
			int[] positionInBallTable=ballTable.getField(x,y);
			if(positionInBallTable[1]==0||//collision with soild
				!ballTable.isEmpty(positionInBallTable[0],positionInBallTable[1]-1)){ //collsion with ball 
					collision=true;
			}
		}
		return collision;
	}
	
	/**Performs a collsion with a ball below*/
	private void collide(){
		if(collsionSound!=null)
			collsionSound.play(); //Plays the sound
		toggleMoving();
		ballTable.addBall(this);
		if(effectCatalog!=null)
			effectCatalog.addEffect(this,EffectCatalog.effectBouncing);
	}

}
