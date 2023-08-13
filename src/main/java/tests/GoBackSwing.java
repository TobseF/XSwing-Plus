/*
 * @version 0.0 14.04.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;

public class GoBackSwing extends BasicGame {

    private static AppGameContainer container;
    private Image background, ball;
    private AngelCodeFont font;
    private final int canonX = 248;
    private final int canonY = 166;
    /**
     * Current crane postion (1 - 8)
     */
    private int cranePosition = 0;
    /**
     * Time since start in ms
     */
    private long timeSinceStart;
    /**
     * Gap between balls
     */
    private final int gap = 16;
    /**
     * Ball length
     */
    private final int ba = 48;

    public GoBackSwing() {
        super("XSwing");
    }

    static long ttime;

    public static void main(String[] args) {
        try {
            container = new AppGameContainer(new GoBackSwing());
            container.setMinimumLogicUpdateInterval(20);
            container.setDisplayMode(1024, 768, true);
            container.setClearEachFrame(false);
            container.setIcon("restest/ball.png");
            container.start();

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        background = new Image("restest/swing_background_b.jpg");
        ball = new Image("restest/ball.png");
        font = new AngelCodeFont("restest/font_arial_16_bold.fnt",
                "restest/font_arial_16_bold.png");
        ttime = container.getTime();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        timeSinceStart = ((container.getTime() - ttime)) / 1000;
        Input in = container.getInput();

        if (in.isKeyPressed(Input.KEY_LEFT)) {
            if (cranePosition >= 1) {
                cranePosition--;
            }
        }

        if (in.isKeyPressed(Input.KEY_RIGHT)) {
            if (cranePosition < 7) {
                cranePosition++;
            }
        }

        if (in.isKeyDown(Input.KEY_ESCAPE)) {
            container.exit();
        }

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0);
        renderTimer();
        g.drawImage(ball, canonX + cranePosition * (ba + gap), canonY);
    }

    public void renderTimer() {
        String s = String.format("%02d", (int) timeSinceStart % 60);
        String m = String.format("%02d", (int) (timeSinceStart / 60) % 60);
        String h = String.format("%02d", (int) ((timeSinceStart / 60) / 60) % 60);
        font.drawString(115, 717, h + ":" + m + ":" + s);
    }
}