/*
 * @version 0.0 01.06.2008
 * @author Tobse F
 */
package lib.mylib.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds resetable objects.
 *
 * @author Tobse
 */
public class Reset implements Resetable {

    List<Resetable> list = new ArrayList<Resetable>();

    public void add(Resetable... resetableObjects) {
        Collections.addAll(list, resetableObjects);
    }

    /**
     * Resets all Elements
     */
    public void reset() {
        for (Resetable resetable : list) {
            resetable.reset();
        }
    }
}