/*
 * @version 0.0 20.11.2008
 * @author Tobse F
 */
package lib.mylib;

import lib.mylib.object.Resetable;
import lib.mylib.object.Updateable;

/**
 * Changes a transparancy value over time.
 *
 * @author Tobse
 * @see #getTranparency()
 * @see #invert
 */
public class ValueSlider implements Updateable, Resetable {

    private double currentValue = 0;
    private final MyTimer timer;

    public enum ValueSliderType {
        SLIDE_IN, SLIDE_OUT
    }

    private boolean invert = false;
    private int steps;
    private double valueByStep;
    private double minValue = 0, maxValue = 1;
    private boolean pause = false;

    public ValueSlider(double minValue, double maxValue, int steps, int duration,
                       ValueSliderType slydeType) {

        // FIXME: BUg: size = new ValueSlider(1, 1.7, 20, 50, ValueSliderType.SLIDE_IN); slider
        // starts with 0.034!

        this.steps = steps;
        setRange(minValue, maxValue);
        timer = new MyTimer((int) ((double) duration / steps), true) {

            @Override
            protected void timerAction() {
                nextValue();
            }
        };
        switch (slydeType) {
            case SLIDE_OUT:
                invert();

                break;
            case SLIDE_IN:
                break;
        }
        reset();
    }

    public void setRange(double minValue, double maxValue) {
        if (minValue > maxValue) {
            throw new IllegalArgumentException("minValue isn't < maxValue");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        setSteps(steps);
    }

    public void setSteps(int steps) {
        if (steps <= 0) {
            throw new IllegalArgumentException("steps have to > 0 !");
        }
        this.steps = steps;
        valueByStep = (maxValue - minValue) / steps;
    }

    public void setValue(double value) {
        if (value <= minValue && value >= maxValue) {
            currentValue = value;
        } else {
            throw new IllegalArgumentException("minValue <= value <= maxValue");
        }
    }

    /**
     * @return currentValue
     */
    public double getValue() {
        return currentValue;
    }

    @Override
    public void update(int delta) {
        if (!pause) {
            timer.update(delta);
        }
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isInverted() {
        return invert;
    }

    private void nextValue() {
        if (!invert) {
            if (currentValue + valueByStep <= maxValue) {
                currentValue += valueByStep;
            } else {
                currentValue = maxValue;
            }
        } else if (currentValue - valueByStep >= minValue) {
            currentValue -= valueByStep;
        } else {
            currentValue = minValue;
        }
    }

    public boolean isFinshed() {
        return (!invert && currentValue == maxValue) || (invert && currentValue == minValue);
    }

    @Override
    public void reset() {
        currentValue = !invert ? minValue : maxValue;
        timer.reset();
    }

    /**
     * Inverts the progression.
     */
    public void invert() {
        invert = !invert;
    }

    public void setInvert(boolean invert) {
        this.invert = invert;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }
}