/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import java.beans.PropertyChangeEvent;

public abstract class LatticeConsumerEventProcessorAdapter implements EventProcessor, LatticeConsumerEventProcessor {
    public void processEventForTarget(PropertyChangeEvent event, Object target) {
        processEventForLatticeConsumer(event, (ConceptSetDrawingConsumer) target);
    }
}
