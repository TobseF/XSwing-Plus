package xswing;


import lib.mylib.object.SObject;
import org.newdawn.slick.Sound;

public class GameOver extends SObject {
    private Sound gameOver;

    public void play() {
        if (gameOver != null) {
            gameOver.play();
        }
    }

}
