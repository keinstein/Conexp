/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import java.beans.PropertyChangeEvent;

public interface EventProcessor {
    void processEventForTarget(PropertyChangeEvent event, Object target);
}
