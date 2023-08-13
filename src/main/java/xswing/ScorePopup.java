/*
 * @version 0.0 02.12.2008
 * @author Tobse F
 */
package xswing;

import lib.mylib.MyTimer;
import lib.mylib.ValueSlider;
import lib.mylib.ValueSlider.ValueSliderType;
import lib.mylib.color.TransparancySlider;
import lib.mylib.object.SObject;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

public class ScorePopup extends SObject {

    private final MyTimer timer;
    private final TransparancySlider transparency;
    private final Font font;
    private final int timeToLive = 1500;
    private final String score;
    private final ValueSlider size;

    // private Image scoreImage;

    public ScorePopup(Font font, int x, int y, String score) {
        super(x, y);
        this.score = score;
        timer = new MyTimer(timeToLive, false) {

            @Override
            protected void timerAction() {
                finish();
            }
        };
        transparency = new TransparancySlider(50, timeToLive,
                TransparancySlider.LINEAR_FADE_OUT);
        size = new ValueSlider(1, 1.3, 20, 50, ValueSliderType.SLIDE_IN);
        // size.setValue(1);
        this.font = font;
        // y-= font.getHeight(score)* size.getValue();
        // FontStringsToSpiteSheetConverter converter = new
        // FontStringsToSpiteSheetConverter(font, new String[]{score});
        // scoreImage = converter.getSpriteSheet().getSprite(0);
    }

    @Override
    public void update(int delta) {
        timer.update(delta);
        size.update(delta);
        transparency.update(delta);
        y -= (int) size.getValue();
    }

    @Override
    public void render(Graphics g) {
        // g.scale((float)size.getValue(), (float)size.getValue());
        g.setFont(font);
        g.setColor(transparency.getTranparency());
        // g.drawString(score, x/(float)size.getValue(), (y/(float)(size.getValue()) -
        // (float)(font.getHeight(score)* size.getValue()))); //- font.getHeight(score)*
        // size.getValue()

        g.drawString(score, x, y);
        // font.drawString(x/(float)size.getValue(), (y/(float)(size.getValue()) -
        // (float)(font.getHeight(score)* size.getValue())), score,
        // transparency.getTranparency());
        // g.drawImage(scoreImage, x, y, transparency.getTranparency());
    }
}
