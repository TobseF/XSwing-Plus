/*
 * @version 0.0 25.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.SObject;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/** Draws the weight sum per column on the screen */
public class WeightTable extends SObject{
	private final Font font;
	BallTable ballTable;
	private int[] weights=new int[8];
	private int letterLenght;
	
	public WeightTable(Font font,BallTable ballTable) {
		this.font=font;
		this.ballTable=ballTable;
		letterLenght=font.getWidth("0");
	}
	
	@Override
	public void draw(Graphics g) {
		int gap=16;
		int ballA=48;
		for(int i=0;i<8;i++){
			font.drawString(x+(gap+ballA)*i-((weights[i]+"").length()-1)*(letterLenght+2)/2, y,""+weights[i]);
		}
	}
	
	@Override
	public void update() {
		for(int i=0;i<8;i++){
			weights[i]=ballTable.getColumnWeight(i);
		}
	}
	
	public int[] getWeights() {
		return weights;
	}

}
