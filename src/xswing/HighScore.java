/*
 * @version 0.0 01.09.2008
 * @author 	Tobse F
 */
package xswing;

import java.io.IOException;

import lib.mylib.HighScoreFormatter;
import lib.mylib.SObject;
import lib.mylib.ScoreStoreable;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

public class HighScore extends SObject implements ScoreStoreable{
	private SavedState localFile;
	private HighScoreFormatter scoreFormatter;
	private String[][] highScoreTable;
	private int delay=0;
	private int index=0;
	private AngelCodeFont font;
	
	public HighScore(int x, int y,AngelCodeFont font,String fileName) {
		super(x, y);
		this.font=font;
	  	try {
			localFile=new SavedState(fileName);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		scoreFormatter=new HighScoreFormatter();
		readScore();
	}
	
	public HighScore() {
	}
	
	private void saveScore(){
		String[][] cryptedHighScoreTable=scoreFormatter.cryptScore(highScoreTable);
		System.out.println("WATT? ");
		scoreFormatter.printTable(highScoreTable);
		System.out.println("WATT2? ");
		scoreFormatter.printTable(cryptedHighScoreTable);
		localFile.setString("score", scoreFormatter.shrincScoreInOneLine(cryptedHighScoreTable));
		try {
			localFile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readScore(){
		String scoreInOneLine=localFile.getString("score");
		if(scoreInOneLine!=null&&!scoreInOneLine.isEmpty())
			highScoreTable=scoreFormatter.decryptScore(scoreFormatter.deShrincHighScoreFromOneLine(localFile.getString("score")));
		else
			highScoreTable=new String[0][0];
		System.out.println("read score?: "+scoreFormatter.printTable(highScoreTable));
	}

	@Override
	public void draw(Graphics g) {
		if(isVisible){
			int gap=font.getLineHeight()-2;
			if(highScoreTable!=null&&highScoreTable.length>=1){
				drawScoreTable(index,0);
				if(highScoreTable.length>=2){
				drawScoreTable(index+1,gap);
				}
			}
		}
	}
	
	private void drawScoreTable(int index, int yTranslation){
		int maxScoreLength=font.getWidth("000000");
		font.drawString(x-150,y+yTranslation,(index+1)+"");
		font.drawString(x-font.getWidth(highScoreTable[index][0]),y+yTranslation,highScoreTable[index][0]);
		font.drawString(x-5-font.getWidth(highScoreTable[index][1])-maxScoreLength,y+yTranslation,highScoreTable[index][1]);
	}
	
	public static void main(String[] args) {
		new HighScore();
	}
	
	private void switchIndex(){
		if(index+1<highScoreTable.length-1){
			index++;
		}else{
			index=0;
		}
	}
	
	@Override
	public void update(int delta) {
		delay+=delta;
		if(delay>4000){
			delay=0;
			switchIndex();
		}
	}

	@Override
	public void addScore(int score, String name) {
		System.out.println("before add"+scoreFormatter.printTable(highScoreTable));
		highScoreTable=scoreFormatter.addScore(highScoreTable, name, score);
		System.out.println("after add");
		scoreFormatter.printTable(highScoreTable);
		saveScore();
	}
}
