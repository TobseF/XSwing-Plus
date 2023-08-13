/*
 * @version 0.0 18.02.2009
 * @author Tobse F
 */
package lib.mylib.object;

import java.util.EventObject;

public class SObjectRemovedEvent extends EventObject {

    private final SObject removedObject;

    public SObject getRemovedObject() {
        return removedObject;
    }

    public SObjectRemovedEvent(Object source, SObject removedObject) {
        super(source);
        this.removedObject = removedObject;
    }

}