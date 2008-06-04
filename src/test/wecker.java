/*
 * @version 0.0 17.04.2008
 * @author 	Tobse F
 */
package test;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class wecker {
	private Music music;
	
	public wecker() {
		try {
			music=new Music("D:/Eigene Musik/Nightwish - Dark Passion Play/01 - The Poet and the Pendulum.mp3",false);
		} catch (SlickException e) {}
		music.play();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new wecker();

	}

}
