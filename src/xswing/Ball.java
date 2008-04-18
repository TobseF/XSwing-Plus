/*
 * @version 0.0 15.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class Ball extends SObject{
	Font font;
	
	/** The number of the ball (0-49)*/
	private int nr=0;
	/** The weight of the ball*/
	private int weight=0; 
	/** The effect number of the ball (1=hidden)*/
	private int effect=0;
	/** Pixels to move in one update step*/
	private int speed=8;
	/*/** The ball side lenght
	private final int a;*/
	/** If the balls has to to be moved in update*/
	private boolean moving=false;
	
	private boolean readyToKill=false;
	
	public boolean isReadyToKill() {
		return readyToKill;
	}

	BallTable ballTable=null;

	XSwing swing;
	
	public void setSwing(XSwing swing) {
		this.swing = swing;
	}
	

	

	
	public Ball(int nr, int x, int y,XSwing swing){
		super(swing.ballImages.getSprite(nr), x, y);
		this.nr=nr;
		weight=nr;
//		super(swing.ball, x, y);
//		swing.ballImages.gets
		this.swing=swing;
		font=swing.ballFont;

	}
	
	public void kill(){
		nr=99;
		weight=99;
		readyToKill=true;
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
		if(weight<9)
			font.drawString(x+18, y+18, weight+1+"");
		else
			font.drawString(x+13, y+18, weight+1+"");
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
			int[] tabelPos=swing.ballTable.getField(x, y);
			swing.ballTable.setBall(tabelPos[0],tabelPos[1], null);
		}
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	@Override
	public void update() {
		if(moving){
			y+=speed;
			if(checkCollison())
				collide();
		}
		else
			if(!checkCollison())
				if(ballTable.isOverGrid(x,y))
						toggleMoving();
	}
	
	/** Checks wether ther's a ball or soil below
	 * @return ture wenn collides*/
	private boolean checkCollison(){
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
		ballTable.printBallTable();
		System.out.println("");
	}


}
