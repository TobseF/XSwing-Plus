package tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BackSwing
extends BasicGame {
    static AppGameContainer container;
    Image background;
    AngelCodeFont font;
    String time = "sdsd";
    String t = "";
    static long ttime;
    long timeS;

    public BackSwing() {
        super("XSwing");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BackSwing());
            container.setMinimumLogicUpdateInterval(20);
            container.setDisplayMode(800, 600, false);
            container.setClearEachFrame(false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.background = new Image("res/swing_background.jpg");
        this.font = new AngelCodeFont("res/font2.fnt", "res/font2.png");
        ttime = container.getTime();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        this.timeS = (container.getTime() - ttime) / 1000L;
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(this.background, 0.0f, 0.0f);
        String s = String.format("%02d", (int)this.timeS % 60);
        String m = String.format("%02d", (int)(this.timeS / 60L) % 60);
        String h = String.format("%02d", (int)(this.timeS / 60L / 60L) % 60);
        this.font.drawString(55.0f, 443.0f, String.valueOf(h) + ":" + m + ":" + s);
    }
}
