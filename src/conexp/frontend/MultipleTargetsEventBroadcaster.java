/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 5, 2001
 * Time: 9:11:29 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class MultipleTargetsEventBroadcaster implements EventBroadcaster {
    private ArrayList targets;

    public synchronized List getTargets() {
        if (null == targets) {
            targets = new ArrayList();
        }
        return targets;
    }

    public void addTarget(Object target) {
        getTargets().add(target);
    }

    public boolean hasTargets() {
        if (null == targets) {
            return false;
        }
        return getTargets().size() != 0;
    }

    public void removeTarget(Object target) {
        if (null == targets) {
            return;
        }
        getTargets().remove(target);
    }


    public void applyEventProcessorToListeners(PropertyChangeEvent evt, EventProcessor processor) {
        List toProcess = null;

        synchronized (this) {
            if (null != targets) {
                toProcess = (List) targets.clone();
            }
        }
        if (null != toProcess) {
            for (int i = 0; i < toProcess.size(); i++) {
                processor.processEventForTarget(evt, toProcess.get(i));
            }
        }
    }
}
