/*
 * @version 0.0 16.02.2009
 * @author Tobse F
 */
package lib.mylib;

import lib.mylib.object.Resetable;

public class Value implements Resetable {

    private double value;

    public Value(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void reset() {
        value = 0;
    }

}