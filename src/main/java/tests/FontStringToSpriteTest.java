/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package tests;

import lib.mylib.util.FontStringsToSpiteSheetConverter;
import org.newdawn.slick.*;

public class FontStringToSpriteTest extends BasicGame {

    private static AppGameContainer container;
    private Image background;
    private FontStringsToSpiteSheetConverter converter;
    private final String[] highScorea = {"01  Tobse 8966611", "02  Timy 338881"};
    private Font fontText = null;
    private SpriteSheet sp = null;
    private boolean drawAngelCodeFont = true, drawSpiteSheetFont = true;

    public FontStringToSpriteTest() {
        super("MySpriteSheetFont vs AngelCodeFont Test");
    }

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new FontStringToSpriteTest());
            container.setMinimumLogicUpdateInterval(20);
            container.setDisplayMode(800, 600, false);
            container.setClearEachFrame(false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        background = new Image("restest/swing_background.jpg");
        try {
            fontText = new AngelCodeFont("res/font_arial_16_bold.fnt",
                    "res/font_arial_16_bold.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        converter = new FontStringsToSpiteSheetConverter(fontText, highScorea);
        sp = converter.getSpriteSheet();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (container.getInput().isKeyPressed(Input.KEY_1)) {
            drawAngelCodeFont = !drawAngelCodeFont;
        }
        if (container.getInput().isKeyPressed(Input.KEY_2)) {
            drawSpiteSheetFont = !drawSpiteSheetFont;
        }
        if (container.getInput().isKeyPressed(Input.KEY_F2)) {
            try {
                container.setFullscreen(!container.isFullscreen());
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);

        if (drawAngelCodeFont) {
            fontText.drawString(5, 25, highScorea[0]);
            fontText.drawString(5, 40, highScorea[1]);
        }

        if (drawSpiteSheetFont) {
            sp.getSprite(0, 0).draw(150, 25);
            sp.getSprite(0, 1).draw(150, 40);
        }
    }

}
