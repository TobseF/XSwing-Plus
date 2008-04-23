/*
 * @version 0.0 16.04.2008
 * @author 	Tobse F
 */
package test;

import java.util.Arrays;

import xswing.SObject;

public class CopyOfGrid extends SObject{
	/** Ball side lenght	 */
	int ballA=48;
	/** gab between the balls*/
	int gap=16;
	
	int w,h; //w: 512 h: 432
	
	public CopyOfGrid(int x, int y){
		super(x,y);
		w=(ballA+gap)*filled.length;
		h=ballA*filled[0].length;
		for(int[] i:filled){ //fills the array with -1
			Arrays.fill(i,-1);
		}
	}
	
	int[][] filled=new int[8][9];
	
	
	public void setValue(int x, int y,int value){
		filled[x][y]=value;
	}
	
	public int getValue(int x, int y){
		return filled[x][y];
	}
	
	/** Returns the field in which the ball resides. <br> E.g.: (3/2)*/
	public int[] getField(int x,int y){
		//if(y<this.y)return new int[]{-1,-1};
		int posX=(int)((x-this.x+gap)/(double)(ballA+gap));
		int posY=(int)((this.y+h-y+ballA)/(double)ballA);
		return new int[]{posX,posY};
	}
	
	/** Checks, if the given field is empty 
	 * @see #isEmpty(int[])*/
	public boolean isEmpty(int posX,int posY){
//		System.out.println(posX+" "+posY);
		if(posX<filled.length&&posY<filled[0].length)
			if(filled[posX][posY]!=-1){
				return false;
			}
			else{
				return true;
			}
		else
			return true;
	}
	
	/** Checks, if the given field is empty
	 * @see #isEmpty(int posX,int posY)*/
	public boolean isEmpty(int[] pos){
		return isEmpty(pos[0],pos[1]);
	}

	/** Returns the Grid in the console*/
	public void printGrid(){
		System.out.println(filled[0].length);
		for(int i=filled[0].length;i>0;i--){
			for(int ii=0;ii<filled.length;ii++){
				System.out.print(" "+filled[ii][i-1]);	
			}
			System.out.println("");
		}
	}
	
	public boolean isOverGrid(int x, int y){
		if(y>this.y)
				return true;
		else
			return false;
	}
	
	/** Returns the coordinates, of a given Grid field*/
	public int[] getFieldPosOnScreen(int posX, int posY){
		return new int[]{this.x+gap+posX*(ballA+gap),this.y+h-posY*ballA};
	}
	
	/** Returns the coordinates, of a given Grid field*/
	public int[] getFieldPos(int posX, int posY){
		return new int[]{gap+posX*(ballA+gap),h-posY*ballA};
	}
	
	/** Returns the coordinates, of a given Grid field*/
	public int[] getFieldPosOnScreen(int[] pos){
		return getFieldPosOnScreen(pos[0],pos[1]);
	}
	
	public void checkCollsion(){
		
	}
	
}
