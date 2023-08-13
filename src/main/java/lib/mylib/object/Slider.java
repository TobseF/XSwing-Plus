/*
 * @version 0.0 21.12.2008
 * @author Tobse F
 */
package lib.mylib.object;

import lib.mylib.math.Range;

// TODO: implement all and remove old!
public class Slider extends Range {

    @SuppressWarnings("unused")
    private double valueStep = 0.01;
    private int steps = 20;

    public Slider(int steps) {
        setSteps(steps);
    }

    public void setSteps(int steps) {
        this.steps = steps;
        if (steps <= 0) {
            throw new IllegalArgumentException("steps <= 0 is not allowed");
        }
        valueStep = getRange() / steps;
    }

    public int getSteps() {
        return steps;
    }
}
