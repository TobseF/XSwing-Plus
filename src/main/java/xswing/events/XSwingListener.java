/*
 * @version 0.0 23.02.2009
 * @author Tobse F
 */
package xswing.events;

import java.util.EventListener;

public interface XSwingListener extends EventListener {

    void gameEvent(XSwingEvent e);
}