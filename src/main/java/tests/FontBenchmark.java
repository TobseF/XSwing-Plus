/*
 * @version 0.0 26.05.2008
 * @author Tobse F
 */
package tests;

import lib.mylib.SpriteSheet;
import org.newdawn.slick.*;

/**
 * Speed Test: AngelCodeFont vs SpriteSheetFont The difference are only 5fps -1094fps max
 */
public class FontBenchmark extends BasicGame {

    private static AppGameContainer container;
    private Image h;
    private int fontNr = 3;
    private SpriteSheet balls;
    private AngelCodeFont fontAngelCodeFont;
    private SpriteSheetFont fontSpriteSheetFont;
    private SpriteSheet myNumbers;
    private Image nubersPic;

    public FontBenchmark() {
        super("AngelCodeFont vs SpriteSheetFont Test");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new FontBenchmark());
            container.setMinimumLogicUpdateInterval(20);
            container.setDisplayMode(1024, 768, true);
            container.setClearEachFrame(false);
            container.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        h = new Image("res/swing_background_b.jpg");
        balls = new SpriteSheet(new Image("res/Balls1.png"), 48, 48);
        fontAngelCodeFont = new AngelCodeFont("res/font2.fnt", "res/font2.png");
        nubersPic = new Image("res/testt.png");
        myNumbers = new SpriteSheet(nubersPic, 10, 14);
        fontSpriteSheetFont = new SpriteSheetFont(myNumbers, '0');
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input in = container.getInput();
        if (in.isKeyDown(Input.KEY_0)) {
            fontNr = 0;
        }
        if (in.isKeyDown(Input.KEY_1)) {
            fontNr = 1;
        }
        if (in.isKeyDown(Input.KEY_2)) {
            fontNr = 2;
        }
        if (in.isKeyDown(Input.KEY_3)) {
            fontNr = 3;
        }
        if (in.isKeyDown(Input.KEY_ESCAPE)) {
            container.exit();
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        g.drawImage(h, 0, 0);
        if (fontNr > 0) {
            // drawBalls(g,0,0);
            if (fontNr == 1 || fontNr == 3) {
                drawAngelCodeFont(g, 12, 300);
            }
            if (fontNr == 2 || fontNr == 3) {
                drawSpriteSheetFont(g, 450, 300);
            }
        }
    }

    public void drawAngelCodeFont(Graphics g, int x, int y) {
        int iy = 0;
        int ix = 0;
        for (int i = 0; i < balls.getSpriteCount(); i++) {
            ix++;
            if (i % balls.getHorizontalCount() == 0) {
                iy += 50;
                ix = 0;
            }
            fontAngelCodeFont.drawString(ix * 48 + x, iy + y, i + "");
        }
    }

    public void drawSpriteSheetFont(Graphics g, int x, int y) {
        int iy = 0;
        int ix = 0;
        for (int i = 0; i < balls.getSpriteCount(); i++) {
            ix++;
            if (i % balls.getHorizontalCount() == 0) {
                iy += 50;
                ix = 0;
            }
            fontSpriteSheetFont.drawString(ix * 48 + x, iy + y, i + "");
        }
    }

    public void drawBalls(Graphics g, int x, int y) {
        int iy = 0;
        int ix = 0;
        for (int i = 0; i < balls.getSpriteCount(); i++) {
            ix++;
            if (i % balls.getHorizontalCount() == 0) {
                iy += 50;
                ix = 0;
            }
            g.drawImage(balls.getSprite(i), ix * 48 + x, iy + y);
        }
    }

}
