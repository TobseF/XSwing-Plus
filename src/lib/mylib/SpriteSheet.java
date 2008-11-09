/*
 * @version 0.0 23.04.2008
 * @author 	Tobse F
 */
package lib.mylib;

import org.newdawn.slick.Image;

/** Extendened version of the Slick Spritesheet class */
public class SpriteSheet extends org.newdawn.slick.SpriteSheet{

	public SpriteSheet(Image image, int tw, int th) {
		super(image, tw, th);
	}
	
	/** Get get the sprite with the specifid number 
	 * @param nr The number of the Sprite
	 * @return The single {@link Image} of the Sprite Sheet
	 */
	public Image getSprite(int nr){
		int y=0;
		if(nr>0)//no div/0
			y=nr/getHorizontalCount();
		int x=nr-(y*getHorizontalCount());
		return getSprite(x,y);
	}
	
	/** Get get all Sprites of a specifid row 
	 * @param row The row (Y-Pos.) of the Sprites 
	 * @return Array with Images
	 */
	public Image[] getSprites(int row){
		Image[] images=new Image[getHorizontalCount()];
		for(int i=0;i<getHorizontalCount();i++){
			images[i]=getSprite(i,row);			
		}
		return images;
	}
	
	/** @return The number of Sprites in the SpriteSheet
	 */
	public int getSpriteCount(){
		return getHorizontalCount()*getVerticalCount();
	}

}
