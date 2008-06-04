/*
 * @version 0.0 25.04.2008
 * @author 	Tobse F
 */
package xswing;

import lib.Resetable;
import lib.SpriteSheet;

/** The ball which counts the levels */
public class Level extends Ball implements Resetable{
	
	/**The Level with wich the game starts (only for reset)*/
	private int startLeveL=3;
	/**The Highest reachable level*/
	private int maxLeveL=45;

	public Level(int nr, int x, int y,SpriteSheet ballsSpriteSheet) {
		super(nr, x, y,ballsSpriteSheet);
		weight=nr+1;
		setNr(nr);
	}
	
	public void reset(){
		setLevel(startLeveL);
	}
	
	public void setLevel(int level){
		setNr(level+1);
		weight=level+1;
	}

	@Override
	public void update() {
		//do nothing
	}
	
	/** Returns the current level */
	public int getLevel(){
		return	getNr();
	}
	
	/** swiches to the next level */
	public void nextLevel(){
		if(!(getNr()+1>=maxLeveL)){
			setNr(getNr()+1);
			weight=getNr()+1;
		}
	}
}
