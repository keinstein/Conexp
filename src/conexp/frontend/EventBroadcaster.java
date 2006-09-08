/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import java.beans.PropertyChangeEvent;
import java.util.TooManyListenersException;

public interface EventBroadcaster {
    void addTarget(Object target) throws TooManyListenersException;

    boolean hasTargets();

    void removeTarget(Object target);

    void applyEventProcessorToListeners(PropertyChangeEvent evt, EventProcessor processor);
}
