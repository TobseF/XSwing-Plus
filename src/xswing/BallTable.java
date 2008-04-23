/*
 * @version 0.0 16.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.util.Arrays;


public class BallTable extends SObject{
	/** Ball side lenght	 */
	public static final int ballA=48;
	/** gab between the balls*/
	public static final int gap=16;
	/** Hight and Weight of the Ball Table in pixels*/
	private int h; //w: 512 h: 432
	private Ball[][] balls=new Ball[8][13];
	
	public BallTable(int x, int y){
		super(x,y);
		h=ballA*8;
	}
	
	public Ball[][] getBalls() {
		return balls;
	}
	
	/** Sets a ball to the TabllTable*/
	public void setBall(int x, int y,Ball ball){
		if(ball!=null)
			ball.setPos(getFieldPosOnScreen(x,y));
		balls[x][y]=ball;
	}
	
	/**Returns a ball out of the BallTable or null if
	 * the Position is outside the PlayField (0x0)-(7x7)
	 * @param x Position in the Grid
	 * @param y Position in the Grid
	 */
	public Ball getPlayFieldBall(int x, int y){
		if(x>=0&&x<8&&y>=0&&y<8)
			return balls[x][y];
		else
			return null;
	}
	
	/**Returns a ball out of the BallTable
	 * @param x Position in the Grid
	 * @param y Position in the Grid
	 */
	public Ball getBall(int x, int y){
			return balls[x][y];
	}
	
	/** Returns the field in which the ball resides. <br> E.g.: (3/2)*/
	public int[] getField(int x,int y){
		int posX=(int)((x-this.x+gap)/(double)(ballA+gap));
		double posYTemp=((this.y+h-y)/(double)ballA);
		int posY=(int)posYTemp;
		if(posYTemp%1==0)//if its exacly on the grid
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
	
	/** Returns the BallTable in the console*/
	public void printBallTable(){
		BallTable.printBallTable(balls);
	}
	
	/** Returns the given BallTable in the console*/
	public static void printBallTable(Ball[][] balls){
		for(int i=balls[0].length;i>0;i--){
			for(int ii=0;ii<balls.length;ii++){
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
	 * @return Wether the Posiotion is over the BallTable
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
	
	/**Cleares all balls from the BallTable*/
	public void clear(){
		for(Ball[] b:balls){
			Arrays.fill(b,null);
		}
	}
	
	/**Removes the given balls from the BallTable*/
	public void removeBall(Ball ball){
		int[] tabelPos=getField(ball.getX(), ball.getY());
		setBall(tabelPos[0],tabelPos[1],null);
	}
	
	/**Returns the sum of all ball weight on the given column*/
	public int getColumnWeight(int column){
		int i=0;
		for(int row=0;row<8;row++){
			if(balls[column][row]!=null)
				i+=balls[column][row].getWeight();
		}
		return i;
	}
	
}
