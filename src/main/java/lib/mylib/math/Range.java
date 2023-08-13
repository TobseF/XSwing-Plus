/*
 * @version 0.0 21.12.2008
 * @author Tobse F
 */
package lib.mylib.math;

import lib.mylib.object.Resetable;

public class Range implements Resetable {

    protected double minValue = 0, maxValue = 1;
    protected double value = minValue;

    public Range(double minValue, double maxValue) {
        setRange(minValue, maxValue);
    }

    public Range() {
    }

    public void setRange(double minValue, double maxValue) {
        if (maxValue <= minValue) {
            throw new IllegalArgumentException("maxValue can't be smaller than minValue");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public void setValue(double value) {
        // if(value <=0)
        // throw new IllegalArgumentException("value ");
        // FIXME: 0 is never reched on inverted
        if (value < minValue) {
            value = minValue;
        } else if (value > maxValue) {
            value = maxValue;
        }
        this.value = value;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getValue() {
        return value;
    }

    public double getRange() {
        return maxValue - minValue;
    }

    public boolean isInRange(double value) {
        return value >= minValue && value <= maxValue;
    }

    @Override
    public void reset() {
        value = minValue;
    }
}