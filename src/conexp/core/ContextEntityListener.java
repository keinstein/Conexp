/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import java.beans.PropertyChangeEvent;

public interface ContextEntityListener {
    void nameChanged(PropertyChangeEvent evt);
}
