package tests;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class wecker {
    private Music music;

    public wecker() {
        try {
            this.music = new Music("D:/Eigene Musik/Nightwish - Dark Passion Play/01 - The Poet and the Pendulum.mp3", false);
        }
        catch (SlickException slickException) {
            // empty catch block
        }
        this.music.play();
    }

    public static void main(String[] args) {
        new wecker();
    }
}
