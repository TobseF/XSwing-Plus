/*
 * @version 0.0 27.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.text.NumberFormat;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/**
 * @author Tobse
 *
 *Draws the HighScore and Bonus Number
 */
public class HighScoreCounter extends SObject{
	private int score=0;
	private Font font;
	private int bonus=0;
	private Multiplicator multiplicator;
	
	public HighScoreCounter(Font font,int x, int y,Multiplicator multiplicator) {
		super(x,y);
		this.font=font;
		this.multiplicator=multiplicator;
	}

	/** Resets the score */
	public void clear() {
		score=0;
	}
	
	/**Adds score to the HogScoreCounter*/
	public void score(int score){
		bonus=score*multiplicator.getMulti();
		this.score=this.score+bonus;
		multiplicator.score();
	}
	
	@Override
	public void draw(Graphics g) {
		font.drawString(x-((score+"").length()-1)*11, y,NumberFormat.getInstance().format(score));
		if(bonus>0)
			font.drawString(x-((bonus+"").length()-1)*11, y+55,NumberFormat.getInstance().format(bonus));
	}
}
