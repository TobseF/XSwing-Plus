/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;

public class BackSwing extends BasicGame {

    static AppGameContainer container;
    Image background;
    AngelCodeFont font;
    String time = "sdsd", t = "";

    // BigImage back;

    public BackSwing() {
        super("XSwing");
    }

    static long ttime;

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new BackSwing());
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
        font = new AngelCodeFont("restest/font2.fnt", "res/font2.png");
        ttime = container.getTime();
    }

    long timeS;

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        timeS = ((container.getTime() - ttime)) / 1000;

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        String s = String.format("%02d", (int) timeS % 60);
        String m = String.format("%02d", (int) (timeS / 60) % 60);
        String h = String.format("%02d", (int) ((timeS / 60) / 60) % 60);
        font.drawString(55, 443, h + ":" + m + ":" + s);
    }

}
