/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class Ball extends SObject{
	Font font;
	
	/** The number of the ball (0-49)*/
	private int nr=0;
	/** The weight of the ball*/
	protected int weight=0; 
	/** The effect number of the ball (1=hidden)*/
	private int effect=0;
	/** Pixels to move in one update step*/
	private int speed=16;
	/*/** The ball side lenght
	private final int a;*/
	/** If the balls has to to be moved in update*/
	private boolean moving=false;
	private int movingType=0;
	
	private int readyToKill=-1;
	
	protected BallTable ballTable=null;

	protected XSwing swing;
	
	public void setSwing(XSwing swing) {
		this.swing = swing;
	}
	
	public Ball(int level, int x, int y,XSwing swing){
		super(x,y);
		nr=(int)(Math.random()*level+1);
		weight=1+(int)(Math.random()*(level+1));
		setPic(swing.ballImages.getSprite(nr));
		this.swing=swing;
		font=swing.ballFont;
	}
	
	public int getReadyToKill() {
		return readyToKill;
	}

	public void kill(int effectNr){
		nr=999; //no sense
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
	
	private void drawNumber(Graphics g){
		if(weight<=9)
			font.drawString(x+18, y+18, weight+"");
		else
			font.drawString(x+13, y+18, weight+"");
	}
	
	/**	Returns the weight of the ball */
	public int getWeight() {
		return weight;
	}
	
	/**	Returns wether the Ball is moving */
	public boolean isMoving() {
		return moving;
	}
	
	/** Returns the side lenght. -1 when a!=b (no square)*/
	public int getA(){
		if(pic.getWidth()!=pic.getHeight())
			return -1;
		return pic.getWidth();
	} 
	
	/** Changes the moving state and (if necessary) removes the ball from the BallTable*/
	public void toggleMoving() {
		moving=!moving;
		if(moving&&ballTable.isOverGrid(x, y)){ //when starting moving remomes von the BallTable
			ballTable.removeBall(this);
		}
		if(movingType==0)
			movingType++;
		
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setNr(int nr) {
		this.nr = nr;
		pic=swing.ballImages.getSprite(nr);
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public void update() {
		if(moving){
			y+=speed;
			if(checkCollison())
					collide();
		}
		else
			if(movingType>0)
				if(!checkCollison())
					//if(ballTable.isOverGrid(x,y))
							toggleMoving();
	}
	
	int waitBeforeColCheck=300;
	
	/** Checks wether ther's a ball or soil below
	 * @return ture wenn collides*/
	private boolean checkCollison(){
		
		if(waitBeforeColCheck>0)
			waitBeforeColCheck--;
		
		boolean collision=false;
		if(ballTable.isOverGrid(x, y)){
			int[] pos=ballTable.getField(x,y);
			if(pos[1]==0||!ballTable.isEmpty(pos[0],pos[1]-1)){
				collision=true;
			}
		}
		return collision;
	}
	
	/**Performs a collsion with a ball below*/
	private void collide(){
		int[] pos=ballTable.getField(x,y);
		swing.klack1.play(); //Plays the Sound
		ballTable.setBall(pos[0],pos[1],this);
		setPos(ballTable.getFieldPosOnScreen(pos));
		toggleMoving();
		swing.effects.addEffect(this,Effects.effectBouncing);
		pos=ballTable.getField(x,y);
//		ballTable.printBallTable();
//		System.out.println("");
	}

}
