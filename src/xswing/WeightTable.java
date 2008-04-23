/*
 * @version 0.0 25.04.2008
 * @author 	Tobse F
 */
package xswing;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class WeightTable extends SObject{
	private final Font font;
	BallTable ballTable;
	public WeightTable(Font font,BallTable ballTable) {
		this.font=font;
		this.ballTable=ballTable;
	}
	
	@Override
	public void draw(Graphics g) {
		int gap=16;
		int ballA=48;
		for(int i=0;i<8;i++){
			int weight=ballTable.getColumnWeight(i);
			font.drawString(x+(gap+ballA)*i-((weight+"").length()-1)*5, y,""+weight);
		}
	}

}
