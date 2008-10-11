package tests;

import lib.mylib.SpriteSheet;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheetFont;

public class FontBenchmark
extends BasicGame {
    static AppGameContainer container;
    Image h;
    private int fontNr = 3;
    SpriteSheet balls;
    public AngelCodeFont fontAngelCodeFont;
    public SpriteSheetFont fontSpriteSheetFont;
    SpriteSheet myNumbers;
    Image nubersPic;

    public FontBenchmark(String title) {
        super(title);
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new FontBenchmark("FontBEchmark"));
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
        this.h = new Image("res/swing_background_b.jpg");
        this.balls = new SpriteSheet(new Image("res/Balls1.png"), 48, 48);
        this.fontAngelCodeFont = new AngelCodeFont("res/font2.fnt", "res/font2.png");
        this.nubersPic = new Image("res/testt.png");
        this.myNumbers = new SpriteSheet(this.nubersPic, 10, 14);
        this.fontSpriteSheetFont = new SpriteSheetFont(this.myNumbers, '0');
    }

    public void update(GameContainer container, int delta) throws SlickException {
        Input in = container.getInput();
        if (in.isKeyDown(11)) {
            this.fontNr = 0;
        }
        if (in.isKeyDown(2)) {
            this.fontNr = 1;
        }
        if (in.isKeyDown(3)) {
            this.fontNr = 2;
        }
        if (in.isKeyDown(4)) {
            this.fontNr = 3;
        }
        if (in.isKeyDown(1)) {
            container.exit();
        }
    }

    public void render(GameContainer container, Graphics g) {
        g.drawImage(this.h, 0.0f, 0.0f);
        if (this.fontNr > 0) {
            if (this.fontNr == 1 || this.fontNr == 3) {
                this.drawAngelCodeFont(g, 12, 300);
            }
            if (this.fontNr == 2 || this.fontNr == 3) {
                this.drawSpriteSheetFont(g, 450, 300);
            }
        }
    }

    public void drawAngelCodeFont(Graphics g, int x, int y) {
        int iy = 0;
        int ix = 0;
        int i = 0;
        while (i < this.balls.getSpriteCount()) {
            ++ix;
            if (i % this.balls.getHorizontalCount() == 0) {
                iy += 50;
                ix = 0;
            }
            this.fontAngelCodeFont.drawString(ix * 48 + x, iy + y, String.valueOf(i));
            ++i;
        }
    }

    public void drawSpriteSheetFont(Graphics g, int x, int y) {
        int iy = 0;
        int ix = 0;
        int i = 0;
        while (i < this.balls.getSpriteCount()) {
            ++ix;
            if (i % this.balls.getHorizontalCount() == 0) {
                iy += 50;
                ix = 0;
            }
            this.fontSpriteSheetFont.drawString(ix * 48 + x, iy + y, String.valueOf(i));
            ++i;
        }
    }

    public void drawBalls(Graphics g, int x, int y) {
        int iy = 0;
        int ix = 0;
        int i = 0;
        while (i < this.balls.getSpriteCount()) {
            ++ix;
            if (i % this.balls.getHorizontalCount() == 0) {
                iy += 50;
                ix = 0;
            }
            g.drawImage(this.balls.getSprite(i), ix * 48 + x, iy + y);
            ++i;
        }
    }
}
