/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import java.beans.PropertyChangeEvent;

public interface LatticeConsumerEventProcessor {
    void processEventForLatticeConsumer(PropertyChangeEvent event, ConceptSetDrawingConsumer container);
}
