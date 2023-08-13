/*
 * @version 0.0 19.12.2008
 * @author Tobse F
 */
package lib.mylib.object;

import java.util.EventObject;

public class ValueEvent extends EventObject {

    private final double value;

    public ValueEvent(Object source, double value) {
        super(source);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
