package xswing;

import de.lessvoid.font.AngelCodeFont;
import lib.mylib.SObject;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

public class HighScore
extends SObject {
    private SavedState localFile;
    public static int currentScore = 0;

    public HighScore(AngelCodeFont font, String fileName) {
        try {
            this.localFile = new SavedState(fileName);
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void addScore(String name, int score) {
        String nr = String.valueOf(1);
        this.localFile.setString(nr, name);
        this.localFile.setNumber(nr, score);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
