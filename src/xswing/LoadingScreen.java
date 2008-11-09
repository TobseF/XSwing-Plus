/*
 * @version 0.0 29.04.2008
 * @author 	Tobse F
 */
package xswing;

import java.io.IOException;

import lib.mylib.BasicGameState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**The loading screen, which is shwon after all resources have been loaded*/
public class LoadingScreen extends BasicGameState{
	
	public LoadingScreen(int id) {
		super(id);
	}

	public static final String RES="res/";
	private DeferredResource nextResource;
	private Image loading=null;
	private boolean finish=false;	
	
	public boolean isFinish() {
		return finish;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)throws SlickException {
		loading=new Image(RES+"32.png");
		LoadingList.setDeferredLoading(true);	
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)throws SlickException {
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

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)throws SlickException {
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
			finish = true;
			LoadingList.setDeferredLoading(false); //TODO ask Nifty -cause of Hifty errors without
			goToGame(game);
		}
	}

	private void goToGame(StateBasedGame game){
		game.enterState(1, new FadeOutTransition(Color.black),null);
	}
}
