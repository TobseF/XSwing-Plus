package tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.BigImage;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BackSwingBigImage
extends BasicGame {
    static AppGameContainer container;
    Image background;
    Image backgroundBig;
    AngelCodeFont font;
    String time = "sdsd";
    String t = "";
    boolean bigImage = false;
    static long ttime;
    long timeS;

    public BackSwingBigImage() {
        super("XSwing");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BackSwingBigImage());
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
        this.backgroundBig = new BigImage("res/swing_background.jpg", 2, 256);
        this.font = new AngelCodeFont("res/font2.fnt", "res/font2.png");
        ttime = container.getTime();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        this.timeS = (container.getTime() - ttime) / 1000L;
        if (container.getInput().isKeyPressed(57)) {
            this.bigImage = !this.bigImage;
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        if (this.bigImage) {
            g.drawImage(this.backgroundBig, 0.0f, 0.0f);
        } else {
            g.drawImage(this.background, 0.0f, 0.0f);
        }
        String s = String.format("%02d", (int)this.timeS % 60);
        String m = String.format("%02d", (int)(this.timeS / 60L) % 60);
        String h = String.format("%02d", (int)(this.timeS / 60L / 60L) % 60);
        this.font.drawString(55.0f, 443.0f, String.valueOf(h) + ":" + m + ":" + s);
        g.drawString("BigImage: " + this.bigImage, 80.0f, 100.0f);
    }
}
