/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 17, 2001
 * Time: 1:50:04 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import java.beans.PropertyChangeEvent;
import java.util.TooManyListenersException;

public class OneTargetEventBroadcaster implements EventBroadcaster {
    Object target;

    public void addTarget(Object newTarget) throws TooManyListenersException {
        if (null != target) {
            throw new TooManyListenersException("One target event broadcasring strategy support only one target");
        }
        target = newTarget;
    }

    public boolean hasTargets() {
        return target != null;
    }

    public void removeTarget(Object target) {
        this.target = null;
    }

    public void applyEventProcessorToListeners(PropertyChangeEvent evt, EventProcessor processor) {
        if (null != target) {
            processor.processEventForTarget(evt, target);
        }
    }
}
