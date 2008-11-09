/*
 * @version 0.0 01.09.2008
 * @author 	Tobse F
 */
package xswing;

import lib.mylib.SObject;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

import de.lessvoid.font.AngelCodeFont;

public class HighScore extends SObject{
	private SavedState localFile;
	
	public HighScore(AngelCodeFont font,String fileName) {
	  	try {
			localFile=new SavedState(fileName);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		//localFile.
	}
	
	public void addScore(String name, int score){
		String nr=String.valueOf(1);
		localFile.setString(nr, name);
		localFile.setNumber(nr, score);
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
	
	public static int currentScore=0;
}
