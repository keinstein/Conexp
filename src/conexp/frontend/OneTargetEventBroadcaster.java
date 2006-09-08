/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import java.beans.PropertyChangeEvent;
import java.util.TooManyListenersException;

public class OneTargetEventBroadcaster implements EventBroadcaster {
    private Object target;

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
