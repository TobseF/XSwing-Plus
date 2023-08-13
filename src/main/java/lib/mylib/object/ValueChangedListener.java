/*
 * @version 0.0 19.12.2008
 * @author Tobse F
 */
package lib.mylib.object;

import java.util.EventListener;

public interface ValueChangedListener extends EventListener {

    void valueEvent(ValueEvent event);
}
