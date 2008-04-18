/*
 * @version 0.0 23.04.2008
 * @author 	Tobse F
 */
package lib;

import org.newdawn.slick.Image;


public class SpriteSheet extends org.newdawn.slick.SpriteSheet{

	public SpriteSheet(Image image, int tw, int th) {
		super(image, tw, th);
	
	
	}
	
	/** Get get the sprite with the specifid number 
	 * @param nr The number of the Sprite
	 * @return The single Image of the Sprite Sheet
	 */
	public Image getSprite(int nr){
		int y=0;
		if(nr>0)//no div/0
			y=nr/getHorizontalCount();
		int x=nr-(y*getHorizontalCount());
		return getSprite(x,y);
	}

}
