/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core;

import java.beans.PropertyChangeEvent;


public interface ContextListener {
    void contextStructureChanged();

    void relationChanged();

    void objectNameChanged(PropertyChangeEvent evt);

    void attributeNameChanged(PropertyChangeEvent evt);

    void attributeChanged(ContextChangeEvent changeEvent);

    void contextTransposed();
}
