package conexp.frontend;

import java.beans.PropertyChangeEvent;

public interface LatticeConsumerEventProcessor {
    void processEventForLatticeConsumer(PropertyChangeEvent event, ConceptSetDrawingConsumer container);
}
