/*
 * @version 0.0 27.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.text.NumberFormat;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class HighScoreCounter extends SObject{
	private int score=0;
	private Font font;
	
	public HighScoreCounter(Font font,int x, int y) {
		super(x,y);
		this.font=font;
	}
	
	/** Resets the score */
	public void clear() {
		score=0;
	}
	
	/**Adds score to the HogScoreCounter*/
	public void score(int score){
		this.score=this.score+score;
	}
	
	@Override
	public void draw(Graphics g) {
		font.drawString(x-((score+"").length()-1)*11, y,NumberFormat.getInstance().format(score));
	}
}
