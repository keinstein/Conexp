/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 17, 2001
 * Time: 12:55:17 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import java.beans.PropertyChangeEvent;
import java.util.TooManyListenersException;

public interface EventBroadcaster {
    void addTarget(Object target) throws TooManyListenersException;

    boolean hasTargets();

    void removeTarget(Object target);

    void applyEventProcessorToListeners(PropertyChangeEvent evt, EventProcessor processor);
}
