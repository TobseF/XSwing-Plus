/*
 * @version 0.0 23.12.2008
 * @author Tobse F
 */
package lib.mylib.math;

public class RangeSlider extends Range {

    private boolean inverted = false;
    private double step = 0.1;
    private boolean infinite = false; // TODO: implement

    public RangeSlider(double minValue, double maxValue, double step) {
        super(minValue, maxValue);
        setStep(step);
    }

    public RangeSlider(double minValue, double maxValue) {
        super(minValue, maxValue);
    }

    public boolean isInverted() {
        return inverted;
    }

    public RangeSlider(double minValue, double maxValue, double step, double startValue) {
        this(minValue, maxValue, step);
        setValue(startValue);
    }

    public RangeSlider(double step) {
        setStep(step);
    }

    public RangeSlider() {
    }

    public double stepLeft() {
        /*
         * invert(); stepRight(); invert();
         */
        if (!inverted) {
            setValue(getValue() - step);
        } else {
            setValue(getValue() + step);
        }
        if (infinite) {
            if (isAtMinimum() || isAtMaximum()) {
                reset();
            }
        }
        return getValue();
    }

    public double stepRight() {
        if (!inverted) {
            setValue(getValue() + step);
        } else {
            setValue(getValue() - step);
        }
        /*
         * if(infinite){ if(isAtMinimum() || isAtMaximum()) reset(); }
         */
        return getValue();
    }

    @Override
    public void reset() {
        if (!inverted) {
            setValue(minValue);
        } else {
            setValue(maxValue);
        }
    }

    public boolean isAtMinimum() {
        return (!inverted && getValue() == minValue) || (inverted && getValue() == maxValue);
    }

    public boolean isAtMaximum() {
        return (!inverted && getValue() == maxValue) || (inverted && getValue() == minValue);
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public boolean isInfinite() {
        return infinite;
    }

    @Override
    public void setValue(double value) {

        if (value < minValue) {
            value = minValue;
        }
        if (value > maxValue) {
            value = maxValue;
        }

        if (infinite) {
            if (value <= minValue) {
                value = maxValue;
            } else if (value >= maxValue) {
                value = minValue;
            }
        }
        this.value = value;
    }

    public void invert() {
        inverted = !inverted;
    }

    public void setStep(double step) {
        if (step <= 0) {
            throw new IllegalArgumentException("step < 0 is not allowed");
        }
        this.step = step;
    }

    public double getStep() {
        return step;
    }

}