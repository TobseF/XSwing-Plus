/*
 * @version 0.0 19.11.2008
 * @author Tobse F
 */
package lib.mylib.color;

import lib.mylib.MyTimer;
import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;
import org.newdawn.slick.Color;

/**
 * Changes a transparancy value over time.
 *
 * @author Tobse
 * @see #getTranparency()
 * @see #invert
 */
public class TransparancySlider implements Updateable, Resetable {

    private final Color[] tranparency;
    private int currentAlphaStep = 0;
    private final MyTimer timer;
    public static final int LINEAR_FADE_IN = 0, LINEAR_FADE_OUT = 1;
    private boolean invert = false;

    public TransparancySlider(int steps, int duration, int fadingType) {
        tranparency = new Color[steps + 1];
        initColors();
        timer = new MyTimer((int) ((double) duration / steps), true) {

            @Override
            protected void timerAction() {
                nextAlphaValue();
            }
        };
        switch (fadingType) {
            case LINEAR_FADE_OUT:
                invert();
                reset();
                break;
            case LINEAR_FADE_IN:
                break;
        }
    }

    private void initColors() {
        float alphaStep = 1f / (tranparency.length - 1);
        float alpha = 0;
        for (int i = 0; i < tranparency.length; i++) {
            alpha = i * alphaStep;
            tranparency[i] = Transparency.getTransparency(alpha);
        }
    }

    /**
     * @return currentTransparany
     */
    public Color getTranparency() {
        return tranparency[currentAlphaStep];
    }

    /**
     * @return currentTransparany
     */
    public Color getTranparency(Color color) {
        Color newColor = Transparency.setTransparency(color, tranparency[currentAlphaStep].a);
        return newColor;
    }

    @Override
    public void update(int delta) {
        timer.update(delta);
    }

    private void nextAlphaValue() {
        if (!invert && currentAlphaStep < tranparency.length - 1) {
            currentAlphaStep++;
        } else if (invert && currentAlphaStep > 0) {
            currentAlphaStep--;
        }
    }

    @Override
    public void reset() {
        currentAlphaStep = !invert ? 0 : tranparency.length - 1;
        timer.reset();
    }

    /**
     * Inverts the progression.
     */
    public void invert() {
        invert = !invert;
    }
}