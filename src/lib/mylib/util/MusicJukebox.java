/*
 * @version 0.0 22.12.2008
 * @author Tobse F
 */
package lib.mylib.util;

import java.util.*;
import lib.mylib.object.Resetable;
import org.newdawn.slick.*;

public class MusicJukebox implements MusicListener, Resetable {

	private List<Music> playlist = new ArrayList<Music>();
	private int musicIndex = 0;

	public MusicJukebox(Music... playlist) {
		for (Music title : playlist) {
			title.addListener(this);
			this.playlist.add(title);
		}
	}

	public MusicJukebox() {}

	public void addMusic(Music music) {
		addMusic(music);
	}

	private Music getCurrentTitle() {
		return playlist.get(musicIndex);
	}

	@Override
	public void musicEnded(Music arg0) {
		nexTitle();
	}

	public void pause() {
		getCurrentTitle().pause();
	}

	public void play() {
		getCurrentTitle().play();
	}

	public void setVolume(float volume) {
		for (Music title : playlist) {
			title.setVolume(volume);
		}
	}

	public void nexTitle() {
		musicIndex = musicIndex + 1 < playlist.size() ? musicIndex + 1 : 0;
		play();
	}

	@Override
	public void musicSwapped(Music arg0, Music arg1) {}

	@Override
	public void reset() {
		musicIndex = 0;
		// TODO: implement music reset
		// getCurrentTitle().stop();
		// getCurrentTitle().play();
	}

}
