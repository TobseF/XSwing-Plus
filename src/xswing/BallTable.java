/*
 * @version 0.0 16.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.util.Arrays;


public class BallTable extends SObject{
	/** Ball side lenght	 */
	private int ballA=48;
	/** gab between the balls*/
	private int gap=16;
	/** Hight and Weight of the Ball Table in pixels*/
	private int h; //w: 512 h: 432
	private Ball[][] balls=new Ball[8][10];
	
	public BallTable(int x, int y){
		super(x,y);
		//w=(ballA+gap)*8;
		h=ballA*8;
	}
	
	public Ball[][] getBalls() {
		return balls;
	}
	
	public void setBall(int x, int y,Ball ball){
		balls[x][y]=ball;
	}
	
	public Ball getBall(int x, int y){
		return balls[x][y];
	}
	
	/** Returns the field in which the ball resides. <br> E.g.: (3/2)*/
	public int[] getField(int x,int y){
		int posX=(int)((x-this.x+gap)/(double)(ballA+gap));
		double posYTemp=((this.y+h-y)/(double)ballA);
		int posY=(int)posYTemp;
		if(posYTemp%1==0)//if its exavly on the grid
			posY-=1;
		return new int[]{posX,posY};
	}
	
	/** Returns the field in which the ball resides. <br> E.g.: (3/2)*/
	public int[] getField(SObject ball){
		return getField(ball.getX(),ball.getY());
	}

	/** Checks, if the given field in the BallTable is empty 
	 * @param posX of the BallTable
	 * @param posY of the BallTable
	 * @return
	 * @see #isEmpty(int[])*/
	public boolean isEmpty(int posX,int posY){
		if(balls[posX][posY]!=null)
			return false;
		else			
			return true;
	}
	
	/** Checks, if the given field is empty
	 * @see #isEmpty(int posX,int posY)*/
	public boolean isEmpty(int[] pos){
		return isEmpty(pos[0],pos[1]);
	}
	
	/** Returns the Grid in the console*/
	public void printBallTable(){
		for(int i=8;i>0;i--){
			for(int ii=0;ii<8;ii++){
				if(balls[ii][i-1]!=null)
					System.out.print(" "+String.format("%02d",balls[ii][i-1].getNr()));
				else
					System.out.print(" --");
			}
			System.out.println("");
		}
	}
	
	/**
	 * @param x Position on Screen
	 * @param y Position on Screen
	 * @return Wether the Posiotion is over the Ball table
	 */
	public boolean isOverGrid(int x, int y){
		if(y>this.y)
				return true;
		else
			return false;
	}
	
	/** Returns the coordinates of a given BallTable cell on the display*/
	public int[] getFieldPosOnScreen(int posX, int posY){
		return new int[]{x+gap+posX*(ballA+gap),y+h-((posY+1)*ballA)};
	}
	
	/** Returns the coordinates of a given BallTable cell on the display*/
	public int[] getFieldPosOnScreen(int[] pos){
		return getFieldPosOnScreen(pos[0],pos[1]);
	}
	
	/** Returns the coordinates of fields in the BallTable*/
	public int[] getFieldPos(int posX, int posY){
		return new int[]{gap+posX*(ballA+gap),h-posY*ballA};
	}
	
	/**Kleares all balls from the BallTable*/
	public void clear(){
		for(Ball[] b:balls){
			Arrays.fill(b,null);
		}
	}
	
}
