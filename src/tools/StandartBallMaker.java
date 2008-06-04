/*
 * @version 0.0 21.04.2008
 * @author 	Tobse F
 */
package tools;

import lib.SpriteSheet;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/** A test class, that can genarate an image with balls and numbers*/
public class StandartBallMaker {
	static SpriteSheet sp;
	private final int ballsX=9,ballsY=5,balls=45,ballsA=48;
	Image image=null,ballBackgrund=null;
	Font font;
	boolean drawNumbers=true;
	
	public StandartBallMaker() {
		Graphics g=null;
		try {
			image=new Image(ballsX*ballsA,ballsY*ballsA);
			font = new AngelCodeFont("res/font2.fnt","res/font2.png");
			ballBackgrund=new Image("res/ball.png");
			g = image.getGraphics();
		} catch (SlickException e) {e.printStackTrace();}
		int x=0,y=0;
		for(int i=0;i<balls;i++){
			g.setAntiAlias(true);
			if(image==null){
				g.setColor(Color.red);
				g.fillOval(x, y, ballsA, ballsA);
			}
			else
				g.drawImage(ballBackgrund,x,y);
			g.setColor(Color.black);
			g.drawOval(x, y, ballsA, ballsA);
			if(drawNumbers)
				font.drawString(x+12, y+12, i+1+"");
			x+=ballsA;
			if((i+1)%ballsX==0&&i>1){
				y+=ballsA;
				x=0;
			}
		}
		sp=new SpriteSheet(image,ballsA,ballsA);
	}
	
	public Image getImage() {
		return image;
	}
	
	public SpriteSheet getSprite() {
		return sp;
	}
}
