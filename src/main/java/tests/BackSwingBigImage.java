/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;

import static lib.mylib.options.Paths.RES_DIR;

public class BackSwingBigImage extends BasicGame {

    static AppGameContainer container;
    Image background, backgroundBig;
    AngelCodeFont font;
    String time = "sdsd", t = "";
    boolean bigImage = false;

    // BigImage back;

    public BackSwingBigImage() {
        super("XSwing");
    }

    static long ttime;

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BackSwingBigImage());
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
        background = new Image(RES_DIR + "swing_background.jpg");
        backgroundBig = new BigImage(RES_DIR + "swing_background.jpg", Image.FILTER_NEAREST,
                256);
        font = new AngelCodeFont(RES_DIR + "font2.fnt", "res/font2.png");
        ttime = container.getTime();
    }

    long timeS;

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        timeS = ((container.getTime() - ttime)) / 1000;
        if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
            bigImage = !bigImage;
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        if (bigImage) {
            g.drawImage(backgroundBig, 0, 0);
        } else {
            g.drawImage(background, 0, 0);
        }
        String s = String.format("%02d", (int) timeS % 60);
        String m = String.format("%02d", (int) (timeS / 60) % 60);
        String h = String.format("%02d", (int) ((timeS / 60) / 60) % 60);
        font.drawString(55, 443, h + ":" + m + ":" + s);
        g.drawString("BigImage: " + bigImage, 80, 100);
    }

}
