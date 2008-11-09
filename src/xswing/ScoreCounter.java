/*
 * @version 0.0 27.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.text.NumberFormat;

import lib.mylib.Resetable;
import lib.mylib.SObject;

import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/**Draws the HighScore and Bonus number*/
public class ScoreCounter extends SObject implements Resetable{
	private int score=0;
	private Font font;
	private int bonus=0;
	private HighScoreMultiplicator multiplicator;
	private int letterLenght;
	
	public ScoreCounter(Font font,int x, int y,HighScoreMultiplicator multiplicator) {
		super(x,y);
		this.font=font;
		this.multiplicator=multiplicator;
		letterLenght=font.getWidth("0");
	}

	/**Adds score to the HogScoreCounter*/
	public void score(int score){
		bonus=score*multiplicator.getMulti();
		this.score=this.score+bonus;
		multiplicator.score();
	}
	
	@Override
	public void draw(Graphics g) {
		String scoreS=NumberFormat.getInstance().format(score);
		font.drawString(x-(scoreS.length()-1)*letterLenght, y,scoreS);
		String bonusS=NumberFormat.getInstance().format(bonus);
		font.drawString(x-(bonusS.length()-1)*letterLenght, y+55,bonusS);
	}

	/** Resets the score */
	public void reset() {
		score=0;
		bonus=0;
	}
	
	/** Returns the highscore */
	public int getScore() {
		return score;
	}
}
