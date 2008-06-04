/*
 * @version 0.0 29.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.io.IOException;

import org.newdawn.slick.*;
import org.newdawn.slick.loading.*;

/**The loading screen, which is shwon after all resources have been loaded*/
public class LoadingScreen{
	private DeferredResource nextResource;
	Image loading=null;
	private boolean finish=false;
	public boolean isFinish() {
		return finish;
	}
	
	public LoadingScreen() {
		try {
			loading=new Image("res/32.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void update() throws SlickException {
		if (nextResource != null) {
			try {
				nextResource.load();
			} catch (IOException e) {
				throw new SlickException("Failed to load: "+nextResource.getDescription(), e);
			}
			nextResource = null;
		}
		
		if (LoadingList.get().getRemainingResources() > 0) {
			nextResource = LoadingList.get().getNext();
		} else {
			if (!finish) 
				finish = true;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
			g.fillRect(0,0,500,500); //clear screen, beause "game.setClearEachFrame(false);"
		
		g.setColor(Color.white);
		if (nextResource != null) {
			g.drawString("Loading: "+nextResource.getDescription(), 100, 100);
		}
		
		int total = LoadingList.get().getTotalResources();
		int loaded = LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources();
		
		g.fillRect(100,150,loaded*40,20);
		g.drawRect(100,150,total*40,20);
		g.drawImage(loading,90+loaded*40,140);
	}

}
