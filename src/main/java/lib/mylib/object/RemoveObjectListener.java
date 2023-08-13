/*
 * @version 0.0 18.02.2009
 * @author Tobse F
 */
package lib.mylib.object;

import java.util.EventListener;

public interface RemoveObjectListener extends EventListener {

    /**
     * Called when an {@link SObject} was removed.
     *
     * @param removedObject
     */
    void removedObecect(SObjectRemovedEvent objectRemovedEvent);

}
