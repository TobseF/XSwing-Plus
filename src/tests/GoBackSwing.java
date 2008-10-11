package tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class GoBackSwing
extends BasicGame {
    static AppGameContainer container;
    Image background;
    Image ball;
    AngelCodeFont font;
    String time = "sdsd";
    String t = "";
    int rasterX = 284;
    int rasterY = 232;
    int canonX = 248;
    int canonY = 166;
    int caPos = 0;
    static long ttime;
    long timeS;
    int gap = 16;
    int ba = 48;
    int ups = 0;

    public GoBackSwing() {
        super("XSwing");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new GoBackSwing());
            container.setMinimumLogicUpdateInterval(20);
            container.setDisplayMode(1024, 768, true);
            container.setClearEachFrame(false);
            container.start();
        }
        catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void init(GameContainer container) throws SlickException {
        this.background = new Image("res/swing_background_b.jpg");
        this.ball = new Image("res/ball.png");
        this.font = new AngelCodeFont("res/font2.fnt", "res/font2.png");
        ttime = container.getTime();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        this.timeS = (container.getTime() - ttime) / 1000L;
        Input in = container.getInput();
        if (this.ups % 1 == 0) {
            if (in.isKeyPressed(203) && this.caPos >= 1) {
                --this.caPos;
            }
            if (in.isKeyPressed(205) && this.caPos < 7) {
                ++this.caPos;
            }
        }
        if (in.isKeyDown(1)) {
            container.exit();
        }
        ++this.ups;
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(this.background, 0.0f, 0.0f);
        this.renderTimer();
        g.drawImage(this.ball, this.canonX + this.caPos * (this.ba + this.gap), this.canonY);
    }

    public void renderTimer() {
        String s = String.format("%02d", (int)this.timeS % 60);
        String m = String.format("%02d", (int)(this.timeS / 60L) % 60);
        String h = String.format("%02d", (int)(this.timeS / 60L / 60L) % 60);
        this.font.drawString(115.0f, 717.0f, String.valueOf(h) + ":" + m + ":" + s);
    }
}
