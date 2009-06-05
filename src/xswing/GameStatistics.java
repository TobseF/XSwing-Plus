/*
 * @version 0.0 03.05.2009
 * @author 	Tobse F
 */
package xswing;

import java.io.IOException;
import lib.mylib.object.Resetable;
import org.newdawn.slick.*;
import xswing.events.*;
import xswing.events.XSwingEvent.GameEventType;
import xswing.start.XSwing;


public class GameStatistics implements XSwingListener, Resetable{
	private long timeStampGameStarted;
	private long timePlayingCurrentGame;
	private SavedState savedState; 
	
	private double playedGames;
	/** Number of played games */
	private double stoppedGames;
	/** Time of all played games together in ms */
	private double timePlayedAllGames;
	
	public GameStatistics() {
		try {
			savedState = new SavedState(XSwing.class.getName());
			savedState.load();
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(savedState != null){
			playedGames = savedState.getNumber(SavedStateOptions.PLAYED_GAMES);
			stoppedGames = savedState.getNumber(SavedStateOptions.STOPPED_GAMES);
			timePlayedAllGames = 
				savedState.getNumber(SavedStateOptions.TIME_PLAYED_ALL_GAMES);
		}
	}
	
	@Override
	public void gameEvent(XSwingEvent e) {
		if(e.getGameEventType() == GameEventType.GAME_SARTED){
			timeStampGameStarted = System.currentTimeMillis();
		}else if(e.getGameEventType() == GameEventType.GAME_PAUSED){
			timePlayingCurrentGame += (System.currentTimeMillis() - timeStampGameStarted);
		}else if(e.getGameEventType() == GameEventType.GAME_RESUMED){
			timeStampGameStarted = System.currentTimeMillis();
		}else if(e.getGameEventType() == GameEventType.GAME_OVER || 
				e.getGameEventType() == GameEventType.GAME_STOPPED){
			timePlayingCurrentGame += (System.currentTimeMillis() - timeStampGameStarted);
			timePlayedAllGames += (timePlayingCurrentGame);
			playedGames++;
			if(e.getGameEventType() == GameEventType.GAME_STOPPED){
				stoppedGames ++;
			}
			timeStampGameStarted = 0;
			saveState();
			timePlayingCurrentGame = 0;
		}
	}
	
	@Override
	public String toString() {
		double min = (timePlayedAllGames / 60 / 1000);
		int sec = (int)((min % 1) * 60);
		return "playedGames: "+ (int)playedGames + 
				" TimeOfAllGames: " + (int)min + ":"+sec;
	}
	
	
	public double getTimePlayedAllGames() {
		return timePlayedAllGames;
	}
	
	public double getPlayedGames() {
		return playedGames;
	}

	@Override
	public void reset() {
		timePlayedAllGames = 0;
		playedGames = 0;
		stoppedGames = 0;
		saveState();
	}
	
	private void saveState(){
		savedState.setNumber(SavedStateOptions.PLAYED_GAMES, playedGames);
		savedState.setNumber(SavedStateOptions.TIME_PLAYED_ALL_GAMES, timePlayedAllGames);
		savedState.setNumber(SavedStateOptions.STOPPED_GAMES, stoppedGames);
		try {
			savedState.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}